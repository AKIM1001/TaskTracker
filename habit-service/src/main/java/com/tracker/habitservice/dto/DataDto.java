package com.tracker.habitservice.dto;

import java.util.Map;

public record DataDto(Long habitId,
                      Map<String, String> userIdsData) {
}

