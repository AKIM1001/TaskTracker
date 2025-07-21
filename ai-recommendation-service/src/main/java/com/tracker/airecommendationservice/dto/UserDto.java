package com.tracker.airecommendationservice.dto;

public record UserDto(Long id,
                      String name,
                      String surname,
                      Integer age) {

    @Override
    public String toString() {
        return "name: " + name + ", surname: " + surname + ", age: " + age + "\n";
    }
}
