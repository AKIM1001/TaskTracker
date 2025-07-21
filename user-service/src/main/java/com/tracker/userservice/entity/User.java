package com.tracker.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(generator = "user_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    @Setter(AccessLevel.NONE)
    private Long id;
    private Role role;
    private String name;
    private String surname;
    private String email;
    private Date dateOfBirth;
    private String hashPassword;
    private String telegramAccount;
}
