package com.woowahan.smell.bazzangee.repository;

import com.woowahan.smell.bazzangee.domain.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface GoodRepository extends CrudRepository<Good, Long> {

}
