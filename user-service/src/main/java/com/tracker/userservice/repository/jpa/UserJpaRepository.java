package com.tracker.userservice.repository.jpa;

import com.tracker.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User,Long> {

    Optional<Object> findByEmail(String email);
}
