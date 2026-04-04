package com.tumblrPoll.metrics.DTOs;

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
public class Post {
    private long id;
    // private String type; // May be useful eventually, but for now we just want to filter tags and get the URL
    private String post_url;
    // private long timestamp; //May be useful eventually
    private List<String> tags;
}