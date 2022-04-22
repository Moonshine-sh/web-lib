package by.ginel.weblib.controller;

import by.ginel.weblib.dto.BookCreateDto;
import by.ginel.weblib.dto.BookGetDto;
import by.ginel.weblib.dto.BookUpdateDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.entity.CartBook;
import by.ginel.weblib.entity.PersonRole;
import by.ginel.weblib.service.api.BookService;
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
public class BookController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    BookService bookService;

    @GetMapping("/{id}")
    public ModelAndView getBook(HttpServletRequest request, @PathVariable Long id) {

        try {
            BookGetDto book = bookService.getById(id);
            return new ModelAndView("book").addObject("book", book)
                    .addObject("person",request.getSession().getAttribute("person"));
        }catch (NullPointerException ex){
            request.getSession().setAttribute("error", "there is no book with such id");
            return new ModelAndView("redirect:/error");
        }
    }

    @GetMapping("/{id}/add")
    public ModelAndView addBookToCart(@PathVariable Long id, @RequestParam("quantity") Long quantity, HttpServletRequest request){

        List<CartBook> cart = (List<CartBook>) request.getSession().getAttribute("cart");
        if(cart == null)
            cart = new ArrayList<>();
        cart.add(new CartBook(id,quantity));
        request.getSession().setAttribute("cart",cart);
        return new ModelAndView("redirect:/catalog");
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deleteBook(@PathVariable Long id, HttpServletRequest request) {

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");
        if (person != null && person.getRole() == PersonRole.ADMIN.toString()) {

            try {
                BookGetDto book = bookService.getById(id);
                bookService.delete(id);

                return new ModelAndView("redirect:/catalog");
            } catch (NullPointerException e) {
                session.setAttribute("error", "There is no book with such id");
                return new ModelAndView("redirect:/mistake");
            }
        } else {
            session.setAttribute("error", "You cant access this feature");
            return new ModelAndView("redirect:/mistake");
        }
    }

    @PostMapping("/add")
    public ModelAndView addBook(@ModelAttribute BookCreateDto book, @RequestParam("cover") MultipartFile cover, HttpServletRequest request) {

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");

        if (person != null && person.getRole() == PersonRole.ADMIN.toString()) {

            String resultFilename = bookService.filePathCreate(cover);
            try {
                cover.transferTo(new File(uploadPath+"/"+resultFilename));
            } catch (IOException exception) {
                session.setAttribute("error", "Something went wrong during file transfer");
                return new ModelAndView("redirect:/mistake");
            }

            book.setPicPath(resultFilename);
            bookService.save(book);
            return new ModelAndView("redirect:/catalog");
        } else {

            session.setAttribute("error", "You cant access this feature");
            return new ModelAndView("redirect:/mistake");
        }
    }

    @GetMapping("/new")
    public ModelAndView getNewBook(HttpServletRequest request) {

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");

        if (person != null && person.getRole() == PersonRole.ADMIN.toString()) {

            return new ModelAndView("new_book")
                    .addObject("person",request.getSession().getAttribute("person"));
        } else {

            session.setAttribute("error", "You cant access this feature");
            return new ModelAndView("redirect:/mistake");
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView getEditBook(@PathVariable Long id, HttpServletRequest request) {

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");

        if (person != null && person.getRole() == PersonRole.ADMIN.toString()) {
            try {

                BookGetDto book = bookService.getById(id);
                return new ModelAndView("edit_book")
                        .addObject("book",book)
                        .addObject("person",request.getSession().getAttribute("person"));
            } catch (NullPointerException e) {
                session.setAttribute("error", "There is no book with such id");
                return new ModelAndView("redirect:/mistake");
            }
        } else {

            session.setAttribute("error", "You cant access this feature");
            return new ModelAndView("redirect:/mistake");
        }
    }

    @PostMapping("/{id}/update")
    public ModelAndView updateBook(@ModelAttribute BookUpdateDto book, @RequestParam("cover") MultipartFile cover, HttpServletRequest request) {

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");

        if (person != null && person.getRole() == PersonRole.ADMIN.toString()) {

            String resultFilename = bookService.filePathCreate(cover);
            try {
                cover.transferTo(new File(uploadPath+"/"+resultFilename));
            } catch (IOException exception) {
                session.setAttribute("error", "Something went wrong during file transfer");
                return new ModelAndView("redirect:/mistake");
            }

            book.setPicPath(resultFilename);
            bookService.update(book);
            return new ModelAndView("redirect:/catalog");
        } else {

            session.setAttribute("error", "You cant access this feature");
            return new ModelAndView("redirect:/mistake");
        }
    }
}
