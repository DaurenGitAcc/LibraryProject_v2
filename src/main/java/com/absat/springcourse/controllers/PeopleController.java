package com.absat.springcourse.controllers;

import com.absat.springcourse.models.Book;
import com.absat.springcourse.models.Person;
import com.absat.springcourse.repositories.PeopleRepository;
import com.absat.springcourse.services.PeopleServices;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleServices peopleServices;

    @Autowired
    public PeopleController(PeopleServices peopleServices) {
        this.peopleServices = peopleServices;
    }

    @GetMapping()
    public String index(Model model){

        model.addAttribute("people",peopleServices.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Person person = peopleServices.findOne(id);
        List<Book> books = peopleServices.getBooksById(id);
        model.addAttribute("person", person);
        model.addAttribute("books", books);
        return "people/show";
    }

    @GetMapping("/new")
    public String createPage(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person) {
        peopleServices.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        peopleServices.update(person);
        return "redirect:/people/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleServices.delete(id);
        return "redirect:/people";
    }

}
