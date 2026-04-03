package com.tumblrPoll.metrics;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Poll<T> {
    private long id;
    private T item; // Character or Race we are gathering data on
    private String category; // Category of the poll (e.g., "stronl", or "Mordor Coeffecient")
    private List<PollOption> options; // List of options for the poll
    private int totalVotes; // Total number of votes cast in the poll
    
    public static double getWeightedAverage(Poll<?> poll) {
        if (poll.totalVotes == 0) {
            System.out.println("No votes cast in the poll.");
            return 0f;
        }
        
        // Calculate the weighted sum of the votes
        double weightedSum = poll.getOptions().stream()
            .mapToDouble(option -> option.getValue() * option.getNumVotes())
            .sum();

        return weightedSum / poll.getTotalVotes();
    }

    public static <T> Poll<T> parseJSON(String json, Class<T> itemType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JavaType type = objectMapper.getTypeFactory().constructParametricType(Poll.class, itemType);
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<PollOption> getOptions() {
        return options;
    }

    public void setOptions(List<PollOption> options) {
        this.options = options;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public int setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
        return totalVotes;
    }


}

//Internal class to represent each option in the poll
class PollOption {
    private String label; // Label for the option (e.g., "Strong", "Smol", etc.)
    private int numVotes; // Number of votes for this option

    public int getValue() {
        try {
            return Integer.parseInt(label);
        } catch (Exception e) {
            return 0;
        }
    }

    // Getters and setters
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(int numVotes) {
        this.numVotes = numVotes;
    }
}