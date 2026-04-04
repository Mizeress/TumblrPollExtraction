package com.tumblrPoll.metrics.DTOs;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostResponseDTO {
    private Meta meta;
    private Response response;

    public static String getPostURLsWithTag(PostResponseDTO dto, String[] tags) throws RuntimeException {
        Post[] posts = dto.getResponse().getPosts();
        List<String> requiredTags = Arrays.asList(tags);
        
        return Arrays.stream(posts)
            .filter(post -> post.getTags() != null && post.getTags().containsAll(List.of(tags)))
            .map(Post::getPost_url).findFirst().orElseThrow(() -> new RuntimeException("No post found with the specified tags."));
        
    }
}