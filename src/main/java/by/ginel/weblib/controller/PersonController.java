package by.ginel.weblib.controller;

import by.ginel.weblib.dto.BookGetDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.entity.CartBook;
import by.ginel.weblib.entity.Person;
import by.ginel.weblib.service.api.BookService;
import by.ginel.weblib.service.api.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final BookService bookService;

    @GetMapping("/cart")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getCart(HttpSession session, @SessionAttribute(required = false) List<CartBook> cart) {

        ModelAndView modelAndView = new ModelAndView();
        if (CollectionUtils.isEmpty(cart)) {
            session.setAttribute("error", "Cart is empty please go to catalog");
            modelAndView.setViewName("redirect:/mistake");
            return modelAndView;
        }
        List<BookGetDto> books = bookService.getBooksFromCart(cart);
        modelAndView
                .addObject("cart", cart)
                .addObject("books", books)
                .setViewName("cart");
        return modelAndView;

    }


    @GetMapping("/cart/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView removeBookFromCart(@PathVariable Long id, HttpSession session, @SessionAttribute List<CartBook> cart) {

        ModelAndView modelAndView = new ModelAndView();
        if (CollectionUtils.isEmpty(cart)) {
            session.setAttribute("error", "Cart is empty please go to catalog");
            modelAndView.setViewName("redirect:/mistake");
            return modelAndView;
        }
        if (bookService.removeBookFromCart(id, cart)) {
            modelAndView.setViewName("redirect:/cart");
            return modelAndView;
        }
        session.setAttribute("error", "there is no book in cart with such id");
        modelAndView.setViewName("redirect:/mistake");
        return modelAndView;
    }


}
