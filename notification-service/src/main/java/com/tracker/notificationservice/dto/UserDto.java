package com.tracker.notificationservice.dto;

import java.util.List;

public record UserDto(Long id,
                      String name,
                      String surname,
                      String email,
                      String phoneNumber,
                      List<String> deviceTokens,
                      Integer age) {

    @Override
    public String toString() {
        return "name: " + name + ", surname: " + surname + ", age: " + age + "\n";
    }
}
