package by.ginel.weblib.controller;

import by.ginel.weblib.dto.PersonCreateDto;
import by.ginel.weblib.service.api.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final PersonService personService;

    @GetMapping("/login")
    public ModelAndView getLogin() {
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView getRegister() {
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView postRegister(@ModelAttribute PersonCreateDto person) {
        ModelAndView modelAndView = new ModelAndView();
        if (personService.isUserValid(person))
            modelAndView.addObject("error", "User created successfully")
                    .setViewName("login");
        else
            modelAndView.addObject("error", "User with such username already exist!")
                    .setViewName("register");
        return modelAndView;
    }
}
