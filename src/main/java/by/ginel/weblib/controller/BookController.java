package by.ginel.weblib.controller;

import by.ginel.weblib.dto.BookCreateDto;
import by.ginel.weblib.dto.BookGetDto;
import by.ginel.weblib.dto.BookUpdateDto;
import by.ginel.weblib.service.api.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public ModelAndView getBook(HttpSession session, @PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            BookGetDto book = bookService.getById(id);
            modelAndView.addObject("book", book).setViewName("book");
            return modelAndView;
        } catch (NullPointerException ex) {
            session.setAttribute("error", "there is no book with such id");
            modelAndView.setViewName("redirect:/error");
            return modelAndView;
        }
    }

    @GetMapping("/{id}/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addBookToCart(@PathVariable Long id, @RequestParam("quantity") Long quantity, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        bookService.addBookToCart(id, quantity, session);
        modelAndView.setViewName("redirect:/catalog");
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView deleteBook(@PathVariable Long id, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            bookService.delete(id);
            modelAndView.setViewName("redirect:/catalog");
            return modelAndView;
        } catch (NullPointerException e) {
            session.setAttribute("error", "There is no book with such id");
            modelAndView.setViewName("redirect:/mistake");
            return modelAndView;
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView addBook(@ModelAttribute BookCreateDto book, @RequestParam("cover") MultipartFile cover, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            bookService.saveBookWithCover(book, cover);
        } catch (IOException exception) {
            session.setAttribute("error", "Something went wrong during file transfer");
            modelAndView.setViewName("redirect:/mistake");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/catalog");
        return modelAndView;
    }


    @GetMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView getNewBook() {
        return new ModelAndView("new_book");
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView getEditBook(@PathVariable Long id, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            BookGetDto book = bookService.getById(id);
            modelAndView.addObject("book", book).setViewName("edit_book");
            return modelAndView;
        } catch (NullPointerException e) {
            session.setAttribute("error", "There is no book with such id");
            modelAndView.setViewName("redirect:/mistake");
            return modelAndView;
        }
    }

    @PostMapping("/{id}/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView updateBook(@ModelAttribute BookUpdateDto book, @RequestParam("cover") MultipartFile cover, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            bookService.updateBookWithCover(book, cover);
        } catch (IOException exception) {
            session.setAttribute("error", "Something went wrong during file transfer");
            modelAndView.setViewName("redirect:/mistake");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/catalog");
        return modelAndView;
    }
}
