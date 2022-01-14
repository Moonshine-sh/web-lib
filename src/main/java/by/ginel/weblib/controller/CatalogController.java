package by.ginel.weblib.controller;

import by.ginel.weblib.dto.BookGetDto;
import by.ginel.weblib.service.api.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CatalogController {

    @Autowired
    BookService bookService;

    @GetMapping("/catalog")
    public ModelAndView getCatalog(HttpServletRequest request){

        HttpSession session = request.getSession();
        ModelAndView modelAndView = new ModelAndView("catalog");
        List<BookGetDto> books = bookService.getAll();
        modelAndView.addObject("person",session.getAttribute("person"));
        modelAndView.addObject("books",books);
        return modelAndView;
    }
}
