package com.absat.springcourse.controllers;

import com.absat.springcourse.models.Book;
import com.absat.springcourse.models.Person;
import com.absat.springcourse.services.BooksServices;
import com.absat.springcourse.services.PeopleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksServices booksServices;
    private final PeopleServices peopleServices;

    @Autowired
    public BooksController(BooksServices booksServices, PeopleServices peopleServices) {
        this.booksServices = booksServices;
        this.peopleServices = peopleServices;
    }

    @RequestMapping(value={"","/page/{page}"}, method=RequestMethod.GET)
    public String index(Model model, @PathVariable(value = "page",required = false)String page1,
                        @RequestParam(value = "sort_by_year", defaultValue = "true") boolean sortByYear) {
        List<Book> books = new ArrayList<>();
        int size=booksServices.findAll().size();
        int page=(page1==null?1:Integer.parseInt(page1))-1;
        if (page >= 0) {
            if (sortByYear) {
                books = booksServices.paginationFindAllWithSort(page, 3);
            } else {
                books = booksServices.paginationFindAll(page, 3);
            }
        } else {
            if (sortByYear) {
                books = booksServices.findAllWithSort();
            } else {
                books = booksServices.findAll();
            }

        }
        model.addAttribute("books", books);
        int[] pages=new int[(int)Math.ceil(size/3.0)];
        for (int i=0;i<(int)Math.ceil(size/3.0);i++){
            pages[i]=i;
        }
        model.addAttribute("pages",pages);
        return "books/index";
    }
    @GetMapping("/search")
    public String searchPage(@RequestParam(value = "searchParam",defaultValue = "")String searchParam,
                             @ModelAttribute("book")Book book,
                             Model model){
        boolean isBook=false;
        if(!searchParam.isEmpty()){
            isBook=true;
            book=booksServices.findByNameStartingWith(searchParam);
        }
        model.addAttribute("isBook",isBook);
        model.addAttribute("book",book);
        return "books/search";
    }

    @GetMapping("/new")
    public String createPage(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksServices.findOne(id));
        model.addAttribute("people", peopleServices.findAll());
        model.addAttribute("person", new Person());
        return "books/show";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book) {
        booksServices.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksServices.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        booksServices.update(book);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/set")
    public String setReader(@PathVariable("id") int id, @ModelAttribute("person") Person person, @ModelAttribute("book") Book book) {
        book = booksServices.findOne(id);
        person = peopleServices.findOne(person.getId());
        booksServices.updateReader(book, person);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String releaseReader(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        book = booksServices.findOne(book.getId());
        book.setCurrentReader(null);
        booksServices.update(book);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksServices.delete(id);
        return "redirect:/books";
    }
}
