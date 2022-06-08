package by.ginel.weblib.controller;

import by.ginel.weblib.dto.BookCreateDto;
import by.ginel.weblib.dto.BookGetDto;
import by.ginel.weblib.dto.BookUpdateDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.entity.CartBook;
import by.ginel.weblib.entity.PersonRole;
import by.ginel.weblib.service.api.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    @Value("${upload.path}")
    private String uploadPath;

    private final BookService bookService;

    @GetMapping("/{id}")
    public ModelAndView getBook(HttpServletRequest request, @PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            BookGetDto book = bookService.getById(id);
            modelAndView.addObject("book", book)
                    .addObject("person", request.getSession().getAttribute("person"))
                    .setViewName("book");
            return modelAndView;
        } catch (NullPointerException ex) {
            request.getSession().setAttribute("error", "there is no book with such id");
            modelAndView.setViewName("redirect:/error");
            return modelAndView;
        }
    }

    @GetMapping("/{id}/add")
    public ModelAndView addBookToCart(@PathVariable Long id, @RequestParam("quantity") Long quantity, HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        bookService.addBookToCart(id, quantity, request);
        modelAndView.setViewName("redirect:/catalog");
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deleteBook(@PathVariable Long id, HttpServletRequest request) {

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");
        ModelAndView modelAndView = new ModelAndView();
        if (person != null && person.getRole().equals(PersonRole.ADMIN.toString())) {

            try {
                bookService.delete(id);
                modelAndView.setViewName("redirect:/catalog");
                return modelAndView;
            } catch (NullPointerException e) {
                session.setAttribute("error", "There is no book with such id");
                modelAndView.setViewName("redirect:/mistake");
                return modelAndView;
            }
        } else {
            session.setAttribute("error", "You cant access this feature");
            modelAndView.setViewName("redirect:/mistake");
            return modelAndView;
        }
    }

    @PostMapping("/add")
    public ModelAndView addBook(@ModelAttribute BookCreateDto book, @RequestParam("cover") MultipartFile cover, HttpServletRequest request) {

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");
        ModelAndView modelAndView = new ModelAndView();
        if (person != null && person.getRole().equals(PersonRole.ADMIN.toString())) {

            try {
                bookService.saveBookWithCover(book, cover);
            } catch (IOException exception) {
                session.setAttribute("error", "Something went wrong during file transfer");
                modelAndView.setViewName("redirect:/mistake");
                return modelAndView;
            }
            modelAndView.setViewName("redirect:/catalog");
            return modelAndView;
        } else {

            session.setAttribute("error", "You cant access this feature");
            modelAndView.setViewName("redirect:/mistake");
            return modelAndView;
        }
    }


    @GetMapping("/new")
    public ModelAndView getNewBook(HttpServletRequest request) {

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");
        ModelAndView modelAndView = new ModelAndView();
        if (person != null && person.getRole().equals(PersonRole.ADMIN.toString())) {

            modelAndView.addObject("person", request.getSession().getAttribute("person"))
                    .setViewName("new_book");
            return modelAndView;
        } else {

            session.setAttribute("error", "You cant access this feature");
            modelAndView.setViewName("redirect:/mistake");
            return modelAndView;
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView getEditBook(@PathVariable Long id, HttpServletRequest request) {

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");
        ModelAndView modelAndView = new ModelAndView();
        if (person != null && person.getRole().equals(PersonRole.ADMIN.toString())) {
            try {

                BookGetDto book = bookService.getById(id);
                modelAndView.addObject("book", book)
                        .addObject("person", request.getSession().getAttribute("person"))
                        .setViewName("edit_book");
                return modelAndView;
            } catch (NullPointerException e) {
                session.setAttribute("error", "There is no book with such id");
                modelAndView.setViewName("redirect:/mistake");
                return modelAndView;
            }
        } else {

            session.setAttribute("error", "You cant access this feature");
            modelAndView.setViewName("redirect:/mistake");
            return modelAndView;
        }
    }

    @PostMapping("/{id}/update")
    public ModelAndView updateBook(@ModelAttribute BookUpdateDto book, @RequestParam("cover") MultipartFile cover, HttpServletRequest request) {

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");
        ModelAndView modelAndView = new ModelAndView();
        if (person != null && person.getRole().equals(PersonRole.ADMIN.toString())) {

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
        else {

            session.setAttribute("error", "You cant access this feature");
            modelAndView.setViewName("redirect:/mistake");
            return modelAndView;
        }
    }
}
