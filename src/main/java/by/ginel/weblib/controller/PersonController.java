package by.ginel.weblib.controller;

import by.ginel.weblib.dto.BookGetDto;
import by.ginel.weblib.entity.CartBook;
import by.ginel.weblib.service.api.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final BookService bookService;

    @GetMapping("/cart")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getCart(HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        List<CartBook> cart = (List<CartBook>) session.getAttribute("cart");
        if (Objects.isNull(cart) || cart.size() == 0) {
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
    public ModelAndView removeBookFromCart(@PathVariable Long id, HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        List<CartBook> cart = (List<CartBook>) session.getAttribute("cart");
        if (Objects.isNull(cart)) {
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
