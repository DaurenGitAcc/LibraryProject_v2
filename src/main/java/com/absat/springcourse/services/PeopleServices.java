package com.absat.springcourse.services;

import com.absat.springcourse.models.Book;
import com.absat.springcourse.models.Person;
import com.absat.springcourse.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleServices {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleServices(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }
    public Person findOne(int id){
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElse(null);
    }

    public List<Book> getBooksById(int id){
        Optional<Person> person = peopleRepository.findById(id);
        if(person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            return person.get().getBooks();
        }
        else {
            return Collections.emptyList();
        }
    }


    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }
    @Transactional
    public void update(Person person){
        peopleRepository.save(person);
    }
    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }
}
