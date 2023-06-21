package by.ginel.weblib.controller;

import by.ginel.weblib.dto.BookGetDto;
import by.ginel.weblib.service.api.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatalogController {

    private final BookService bookService;

    @GetMapping("/catalog")
    public ModelAndView getCatalog() {
        List<BookGetDto> books = bookService.getAll();
        return new ModelAndView("catalog").addObject("books", books);
    }
}
