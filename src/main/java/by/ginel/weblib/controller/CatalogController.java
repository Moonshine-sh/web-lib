package by.ginel.weblib.controller;

import by.ginel.weblib.dto.BookGetDto;
import by.ginel.weblib.service.api.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatalogController {

    private final BookService bookService;

    @GetMapping("/catalog")
    public ModelAndView getCatalog() {
        ModelAndView modelAndView = new ModelAndView();
        List<BookGetDto> books = bookService.getAll();
        modelAndView.addObject("books", books)
                .setViewName("catalog");
        return modelAndView;
    }
}
