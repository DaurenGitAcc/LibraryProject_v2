package com.absat.springcourse.repositories;

import com.absat.springcourse.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
    Page<Person> findAll(Pageable pageable);
}
