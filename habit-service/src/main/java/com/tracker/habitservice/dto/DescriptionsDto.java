package com.tracker.habitservice.dto;

import java.util.Map;

public record DescriptionsDto(Long habitId,
                              Map<String, String> userIdsDescriptions) {
}
