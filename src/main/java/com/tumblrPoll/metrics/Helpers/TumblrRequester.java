package com.tumblrPoll.metrics.Helpers;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.tumblrPoll.metrics.DTOs.PostResponseDTO;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// TODO update this to filter beyond just one tag (so I don't have to make multiple requests for each tag/category of poll I want to analyze)
// and to extract only the poll data. 

/**
 * Helper class to pull posts from my blog based on tags. 
 */
public class TumblrRequester {
    private final OAuth10aService service;
    private final OAuth1AccessToken accessToken;
    private final OkHttpClient client;

    public TumblrRequester(OAuth10aService service, OAuth1AccessToken accessToken) {
        this.service = service;
        this.accessToken = accessToken;
        this.client = new OkHttpClient();
    }

    public String fetchPostUrl(String blog, String[] tags) throws IOException {
        String url = String.format("https://api.tumblr.com/v2/blog/%s/posts", blog);

        OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, url);
        oAuthRequest.addQuerystringParameter("npf", "true");
        oAuthRequest.addQuerystringParameter("tag", tags[0]);  

        service.signRequest(accessToken, oAuthRequest);

        String completeUrl = oAuthRequest.getCompleteUrl();
        Request request = new Request.Builder().url(completeUrl).headers(okhttp3.Headers.of(oAuthRequest.getHeaders())).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Filter out for more tags
            String jsonResponse = response.body().string();
            ObjectMapper mapper = new ObjectMapper();
            PostResponseDTO dto = mapper.readValue(jsonResponse, PostResponseDTO.class);

            return PostResponseDTO.getPostURLsWithTag(dto, tags);
        }
    }


    public void cleanup() {
        // 1. Stop accepting new requests and cancel pending ones
        client.dispatcher().executorService().shutdown();
        
        // 2. Clear the connection pool (where the 'lingering' threads live)
        client.connectionPool().evictAll();
        
        // 3. Optional: Force close if shutdown takes too long
        try {
            if (!client.dispatcher().executorService().awaitTermination(1, java.util.concurrent.TimeUnit.SECONDS)) {
                client.dispatcher().executorService().shutdownNow();
            }
        } catch (InterruptedException e) {
            client.dispatcher().executorService().shutdownNow();
        }
    }
}
