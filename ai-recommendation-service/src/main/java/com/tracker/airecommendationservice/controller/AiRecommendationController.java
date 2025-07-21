package com.tracker.airecommendationservice.controller;

import com.tracker.airecommendationservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiRecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("{id}")
    public String getResponse(@PathVariable long id) throws IOException {
        return recommendationService.getFullRecommendation(id);
    }
}
