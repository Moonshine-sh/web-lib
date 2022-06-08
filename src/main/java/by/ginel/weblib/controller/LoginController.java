package by.ginel.weblib.controller;

import by.ginel.weblib.dto.PersonCreateDto;
import by.ginel.weblib.service.api.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController {

    private final PersonService personService;

    @GetMapping("/login")
    public ModelAndView getLogin(ModelAndView modelAndView) {

        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView postLogin(@RequestParam("username") String login, @RequestParam("pass") String password, HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        if (personService.isValidCred(login, password, request))
            modelAndView.setViewName("redirect:/home");
        else
            modelAndView.addObject("error", "Incorrect login or password").setViewName("login");
        return modelAndView;
    }


    @GetMapping("/register")
    public ModelAndView getRegister(ModelAndView modelAndView) {

        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView postRegister(@ModelAttribute PersonCreateDto person) {

        ModelAndView modelAndView = new ModelAndView();
        if(personService.isUserValid(person))
            modelAndView.addObject("error", "User created successfully").setViewName("login");
        else
            modelAndView.addObject("error", "User with such username already exist!").setViewName("register");
        return modelAndView;
    }


}
