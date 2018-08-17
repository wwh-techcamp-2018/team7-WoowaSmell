package com.woowahan.smell.bazzangee.repository;

import com.woowahan.smell.bazzangee.domain.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserId(String userId);
}
