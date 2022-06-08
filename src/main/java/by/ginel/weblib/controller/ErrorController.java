package by.ginel.weblib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    @GetMapping("/mistake")
    public ModelAndView getError(HttpServletRequest request, ModelAndView modelAndView) {

        modelAndView.addObject("error", request.getSession().getAttribute("error"))
                .addObject("person", request.getSession().getAttribute("person"))
                .setViewName("mistake");
        return modelAndView;
    }
}
