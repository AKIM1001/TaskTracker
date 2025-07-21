package com.tracker.habitservice.controller;

import com.tracker.habitservice.dto.HabitDto;
import com.tracker.habitservice.request.HabitSaveRequest;
import com.tracker.habitservice.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("api/v1/habit")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    @PostMapping("/save")
    public ResponseEntity<HabitDto> save(@RequestBody HabitSaveRequest habitRequest) {
        HabitDto savedHabit = habitService.save(habitRequest);
        return ResponseEntity.ok(savedHabit);
    }

    @GetMapping("/{habitId}")
    public ResponseEntity<HabitDto> getById(
            @PathVariable long habitId,
            @RequestParam long userId
    ) {
        HabitDto habit = habitService.findById(habitId, userId);
        return ResponseEntity.ok(habit);
    }


    @GetMapping
    public ResponseEntity<Page<HabitDto>> getAll(Pageable pageable) {
        Page<HabitDto> page = habitService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<HabitDto>> getAllByUserId(
            @PathVariable long userId,
            Pageable pageable
    ) {
        Page<HabitDto> page = habitService.findAllByUserId(pageable, userId);
        return ResponseEntity.ok(page);
    }


    @DeleteMapping("/{habitId}")
    public ResponseEntity<Void> deleteHabit(@PathVariable long habitId) {
        habitService.deleteById(habitId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteHabitForUser(
            @RequestParam long habitId,
            @RequestParam long userId
    ) {
        habitService.deleteByUserId(habitId, userId);
        return ResponseEntity.noContent().build();
    }


    }
