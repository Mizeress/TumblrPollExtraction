package com.tumblrPoll.metrics.Helpers;

import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequester {
    private HttpClient client;
    private final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/146.0.0.0 Safari/537.36";

    public HttpRequester() {
        this.client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .cookieHandler(new CookieManager()).build();
    }

    public String fetchPollResults(String blogName, long postId, String pollId, String formKey) throws IOException, InterruptedException {
        String url = String.format("https://www.tumblr.com/api/v2/polls/%s/%s/%s/results", blogName, postId, pollId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Tumblr-Form-Key", formKey)
                .header("User-Agent", userAgent)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                // Internal API often requires the Referer to prevent cross-site hits
                .header("Referer", String.format("https://www.tumblr.com/%s", blogName))
                .GET()
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to fetch poll results. Status: " + response.statusCode() + " Body: " + response.body());
        }

        return response.body();
    }

    /**
     * Scrapes the HTML of the post to find the csrfToken/form_key.
     */
    public String getFormKey(String postUrl) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(postUrl))
                .header("User-Agent", userAgent)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to load page for Form Key: " + response.statusCode());
        }

        // Regex to find "csrfToken":"..." or "form_key":"..."
        Pattern pattern = Pattern.compile("\"(?:csrfToken|form_key)\":\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(response.body());

        if (matcher.find()) {
            return matcher.group(1);
        }

        throw new IOException("Could not find csrfToken in page source.");
    }
}