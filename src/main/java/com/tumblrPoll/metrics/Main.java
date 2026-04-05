package com.tumblrPoll.metrics;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.apis.TumblrApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.tumblrPoll.metrics.DTOs.PostResponseDTO;
import com.tumblrPoll.metrics.Helpers.EnvLoader;
import com.tumblrPoll.metrics.Helpers.TumblrRequester;

public class Main {
    public static void main(String[] args) {

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
        String blogName = "mizeress";
        String[] tags = { "radiants" };
        try {
            PostResponseDTO dto = requester.fetchPosts(blog, tags[0]); // All posts with 1st tag

            long postId = PostResponseDTO.GetPostId(dto, tags); // Post data matching all tags

            String pollContentId = PostResponseDTO.getPollContentId(postId, dto);
            System.out.println("Post ID: " + postId);
            System.out.println("Poll Content ID: " + pollContentId);

            String pollResults = requester.fetchPollResults(blogName, postId, pollContentId);
            
            ObjectMapper mapper = new ObjectMapper();
            // Parse the raw string into a tree, then write it pretty
            Object json = mapper.readValue(pollResults, Object.class); 
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

            System.out.println(prettyJson);

            
            
        } catch (IOException e) {
            System.err.println("Error fetching posts: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Request interrupted: " + e.getMessage());
            e.printStackTrace();
            Thread.currentThread().interrupt(); // Restore interrupt status
        } catch (RuntimeException e) {
            System.err.println("Error processing posts: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            requester.cleanup();
            System.out.println("Cleanup complete. Exiting.");
            System.exit(0); // Forces the JVM to terminate lingering threads
        }
        
    }
}