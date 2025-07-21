package com.tracker.userservice.controller;

import com.tracker.userservice.dto.UserDto;
import com.tracker.userservice.request.UserRequest;
import com.tracker.userservice.service.UserService;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService, PageableHandlerMethodArgumentResolverCustomizer pageable) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<UserDto> save(@RequestBody UserRequest userRequest) {
        UserDto savedUser = userService.save(userRequest);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/find/all")
    public ResponseEntity<Page<UserDto>> findAll(Pageable pageable) {
        Page<UserDto> usersPage = userService.findAll((org.springframework.data.domain.Pageable) pageable);
        return ResponseEntity.ok(usersPage);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable long id) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<UserDto> findByEmail(@PathVariable String email) {
        UserDto user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}



