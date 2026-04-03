package com.tumblrPoll.metrics;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Read the mock JSON file
            String jsonContent = new String(Files.readAllBytes(Paths.get("data/mock_data.json")));

            // 2. Parse it into a Poll object where T is a String (for now)
            Poll<String> testPoll = Poll.parseJSON(jsonContent, String.class);

            if (testPoll != null) {
                System.out.println("✅ JSON Parsed Successfully!");
                System.out.println("Item: " + testPoll.getItem());
                System.out.println("Category: " + testPoll.getCategory());
                
                // 3. Run the math logic
                double average = Poll.getWeightedAverage(testPoll);
                System.out.println("Calculated Weighted Average: " + average);
            }

        } catch (Exception e) {
            System.err.println("Critical Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}