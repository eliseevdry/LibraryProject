package org.eliseev.libraryproject.controllers;

import org.eliseev.libraryproject.dao.BookDao;
import org.eliseev.libraryproject.dao.PersonDao;
import org.eliseev.libraryproject.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDao bookDao;

    private final PersonDao personDao;

    public BookController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDao.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model) {
        Book book = bookDao.show(id);
        Integer personId = book.getPersonId();
        model.addAttribute("book", book);
        model.addAttribute("people", personDao.index());
        if (personId != null) {
            model.addAttribute("person", personDao.show(personId));
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model) {
        model.addAttribute("book", bookDao.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDao.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/clear")
    public String clearPerson(@ModelAttribute("book") Book book,
                              @PathVariable("id") int id) {
        bookDao.setPerson(id, null);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/set")
    public String setPerson(@ModelAttribute("book") Book book,
                            @PathVariable("id") int id) {
        bookDao.setPerson(id, book.getPersonId());
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }
}
