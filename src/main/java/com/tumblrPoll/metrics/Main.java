package com.tumblrPoll.metrics;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.apis.TumblrApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.tumblrPoll.metrics.Helpers.EnvLoader;
import com.tumblrPoll.metrics.Helpers.TumblrRequester;

public class Main {
    public static void main(String[] args) {
    //     try {
    //         // 1. Read the mock JSON file
    //         String jsonContent = new String(Files.readAllBytes(Paths.get("data/mock_data.json")));

    //         // 2. Parse it into a Poll object where T is a String (for now)
    //         Poll<String> testPoll = Poll.parseJSON(jsonContent, String.class);

    //         if (testPoll != null) {
    //             System.out.println("✅ JSON Parsed Successfully!");
    //             System.out.println("Item: " + testPoll.getItem());
    //             System.out.println("Category: " + testPoll.getCategory());
                
    //             // 3. Run the math logic
    //             double average = Poll.getWeightedAverage(testPoll);
    //             System.out.println("Calculated Weighted Average: " + average);
    //         }

    //     } catch (Exception e) {
    //         System.err.println("Critical Error: " + e.getMessage());
    //         e.printStackTrace();
    //     }
    //

        // Load environment variables from .env file
        Map<String, String> env = EnvLoader.load(".env");
        
        String CONSUMER_KEY = env.get("TUMBLR_CONSUMER_KEY");
        String CONSUMER_SECRET = env.get("TUMBLR_CONSUMER_SECRET");
        String TOKEN = env.get("TUMBLR_TOKEN");
        String TOKEN_SECRET = env.get("TUMBLR_TOKEN_SECRET");

        OAuth10aService service = new ServiceBuilder(CONSUMER_KEY)
            .apiSecret(CONSUMER_SECRET)
            .build(TumblrApi.instance());

        OAuth1AccessToken accessToken = new OAuth1AccessToken(TOKEN, TOKEN_SECRET);

        TumblrRequester requester = new TumblrRequester(service, accessToken);
        

        String blog = "mizeress.tumblr.com";
        String tag = "test poll";
        try {
            // String posts = requester.fetchPostsByTag(blog, tag);
            String poll = requester.fetchPollResults(blog, id);
            // Create a mapper that indents the output
            ObjectMapper mapper = new ObjectMapper();
            Object jsonObject = mapper.readValue(poll, Object.class); // Convert string to object
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

            System.out.println("=== PRETTY API RESPONSE ===");
            System.out.println(prettyJson);
            
        } catch (IOException e) {
            System.err.println("Error fetching posts: " + e.getMessage());
            e.printStackTrace();
        } finally {
            requester.cleanup();
            System.out.println("Cleanup complete. Exiting.");
            System.exit(0); // Forces the JVM to terminate lingering threads
        }
        
    }
}