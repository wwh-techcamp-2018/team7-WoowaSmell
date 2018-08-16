package com.woowahan.smell.bazzangee.repository;

import com.woowahan.smell.bazzangee.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserId(String userId);
}
