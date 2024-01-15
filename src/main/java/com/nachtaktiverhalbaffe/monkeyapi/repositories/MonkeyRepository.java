package com.nachtaktiverhalbaffe.monkeyapi.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nachtaktiverhalbaffe.monkeyapi.domain.Monkey;

@Repository
public interface MonkeyRepository extends CrudRepository<Monkey, Long>, PagingAndSortingRepository<Monkey, Long> {

    Optional<Monkey> findByName(String name);

    boolean existsByName(String name);

    void deleteByName(String name);

}
