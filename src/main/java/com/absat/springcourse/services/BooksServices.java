package com.absat.springcourse.services;

import com.absat.springcourse.models.Book;
import com.absat.springcourse.models.Person;
import com.absat.springcourse.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksServices {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksServices(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }
    public Book findOne(int id){
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }
    public List<Book> paginationFindAll(int page,int elementsPerPage){
        return booksRepository.findAll(PageRequest.of(page,elementsPerPage)).getContent();
    }
    public List<Book> paginationFindAllWithSort(int page,int elementsPerPage){
        return booksRepository.findAll(PageRequest.of(page,elementsPerPage, Sort.by("year"))).getContent();
    }
    public List<Book> findAllWithSort(){
        return booksRepository.findAll(Sort.by("year"));
    }
    public Book findByNameStartingWith(String startingWith){
        List<Book> books = booksRepository.findByNameStartingWith(startingWith);
        if(books.isEmpty()){
            return null;
        }
        else{
            return books.get(0);
        }
    }
    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }
    @Transactional
    public void update(Book book){
        booksRepository.save(book);
    }
    @Transactional
    public void updateReader(Book book, Person person){
        book.setSubscriptionDate(new Date());
        book.setCurrentReader(person);
        booksRepository.save(book);
    }
    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }
}
