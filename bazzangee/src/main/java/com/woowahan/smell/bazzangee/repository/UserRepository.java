package com.woowahan.smell.bazzangee.repository;

import com.woowahan.smell.bazzangee.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
