package by.ginel.weblib.controller;

import by.ginel.weblib.dto.BookGetDto;
import by.ginel.weblib.entity.CartBook;
import by.ginel.weblib.service.api.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PersonController {

    @Autowired
    BookService bookService;

    @GetMapping("/cart")
    public ModelAndView getCart(HttpServletRequest request){

        HttpSession session = request.getSession();
        if(session.getAttribute("person") != null){
            List<CartBook> cart = (List<CartBook>) session.getAttribute("cart");
            if (cart == null || cart.size()==0){
                session.setAttribute("error","Cart is empty please go to catalog");
                return new ModelAndView("redirect:/mistake");
            }
            List<BookGetDto> books = cart.stream()
                    .map(item -> bookService.getById(item.getBookId()))
                    .collect(Collectors.toList());
            return new ModelAndView("cart")
                    .addObject("cart",cart).addObject("books",books)
                    .addObject("person",session.getAttribute("person"));
        }
        else{
            session.setAttribute("error", "Please login before accessing this feature");
            return new ModelAndView("redirect:/mistake");
        }
    }

    @GetMapping("/cart/{id}")
    public ModelAndView removeBookFromCart(@PathVariable Long id, HttpServletRequest request){

        HttpSession session = request.getSession();
        if(session.getAttribute("person") != null){
            List<CartBook> cart = (List<CartBook>) session.getAttribute("cart");
            if (cart == null){
                session.setAttribute("error","Cart is empty please go to catalog");
                return new ModelAndView("redirect:/mistake");
            }
            for (CartBook item:cart) {
                if (item.getBookId()==id){
                    cart.remove(item);
                    return new ModelAndView("redirect:/cart");
                }
            }
            session.setAttribute("error", "there is no book in cart with such id");
            return new ModelAndView("redirect:/mistake");
        }
        else{
            session.setAttribute("error", "Please login before accessing this feature");
            return new ModelAndView("redirect:/mistake");
        }
    }
}
