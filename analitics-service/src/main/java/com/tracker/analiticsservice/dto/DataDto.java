package com.tracker.analiticsservice.dto;

import java.util.Map;

public record DataDto(Long habitId,
                      Map<String, String> userIdsData) {
}
