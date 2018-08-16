package com.woowahan.smell.bazzangee.repository;

import com.woowahan.smell.bazzangee.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
}
