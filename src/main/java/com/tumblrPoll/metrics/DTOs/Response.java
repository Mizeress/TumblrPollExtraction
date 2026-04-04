package com.tumblrPoll.metrics.DTOs;

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
public class Response {
    // Blog blog; // Not needed for our purposes, but can be added later if desired
    private Post[] posts;
    private int total_posts;
}