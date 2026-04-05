package com.tumblrPoll.metrics.DTOs;

import java.util.List;

public record PollContentBlock(
    String type, String client_id, String question, List<Answer> answers
) {}

record Answer(
    String client_id, String answer_text
) {}