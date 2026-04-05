package com.tumblrPoll.metrics.DTOs;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    private long id;
    // private String type; // May be useful eventually, but for now we just want to filter tags and get the URL
    @JsonProperty("post_url")
    private String postUrl;
    // private long timestamp; //May be useful eventually
    private List<String> tags;
   
    private List<Map<String, Object>> content; // We only care about the poll content blocks, so we can store the content as a list of maps and extract the poll content blocks later
}