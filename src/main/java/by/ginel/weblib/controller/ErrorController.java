package by.ginel.weblib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class ErrorController {

    @GetMapping("/mistake")
    public ModelAndView getError(HttpSession session) {
        return new ModelAndView("mistake").addObject("error", session.getAttribute("error"));
    }
}
