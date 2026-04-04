package com.tumblrPoll.metrics.Helpers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EnvLoader {
    public static Map<String, String> load(String filePath) {
        Map<String, String> env = new HashMap<>();
        try {
            Files.lines(Paths.get(filePath))
                .filter(line -> line.contains("=") && !line.startsWith("#"))
                .forEach(line -> {
                    String[] parts = line.split("=", 2);
                    env.put(parts[0].trim(), parts[1].trim());
                });
        } catch (Exception e) {
            System.err.println("Could not read .env file: " + e.getMessage());
        }
        return env;
    }
}