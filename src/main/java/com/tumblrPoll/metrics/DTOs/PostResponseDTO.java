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
        long postId = GetPostId(dto, tags);
        Post post = getPostById(postId, dto);
        return post.getPostUrl();
        
    }

    public static long GetPostId(PostResponseDTO dto, String[] tags) throws RuntimeException {
        Post[] posts = dto.getResponse().getPosts();
        List<String> requiredTags = Arrays.stream(tags).map(String::toLowerCase).toList();

        return Arrays.stream(posts)
            .filter(post -> 
                {
                    if (post.getTags() == null || post.getContent() == null) return false;

                    // 1. Check if the tags match
                    List<String> postTags = post.getTags().stream().map(String::toLowerCase).toList();
                    boolean tagsMatch = requiredTags.stream().allMatch(postTags::contains);

                    // 2. Check if it actually contains a poll block
                    boolean hasPoll = post.getContent().stream()
                                        .anyMatch(block -> "poll".equals(block.get("type")));

                    return tagsMatch && hasPoll;
            }).map(Post::getId).findFirst().orElseThrow(() -> new RuntimeException("No post found with the specified tags."));
        
    }

    public static Post getPostById(long id, PostResponseDTO dto) throws RuntimeException {
        Post[] posts = dto.getResponse().getPosts();

        return Arrays.stream(posts)
            .filter(post -> post.getId() == id)
            .findFirst().orElseThrow(() -> new RuntimeException("No post found with the specified ID."));
    }

    public static String getPollContentId(long postId, PostResponseDTO dto) throws RuntimeException {
        Post post = getPostById(postId, dto);
                
        if (post.getContent() == null) {
            throw new RuntimeException("No poll content blocks found for the specified post.");
        }
        
        return post.getContent().stream()
            .filter(block -> "poll".equals(block.get("type")))
            .map(block -> (String) block.get("client_id"))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No poll block found in post " + postId));
    }
}