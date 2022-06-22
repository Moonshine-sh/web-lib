package by.ginel.weblib.controller;

import by.ginel.weblib.dto.PersonCreateDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.entity.Person;
import by.ginel.weblib.entity.VerificationToken;
import by.ginel.weblib.event.OnRegistrationCompleteEvent;
import by.ginel.weblib.mapper.PersonMapper;
import by.ginel.weblib.service.api.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final PersonService personService;
    private final PersonMapper personMapper;
    private final ApplicationEventPublisher eventPublisher;

    @GetMapping("/login")
    public ModelAndView getLogin() {
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView getRegister() {
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView postRegister(@ModelAttribute PersonCreateDto person, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        PersonGetDto user = personService.isUserValid(person);
        if (Objects.nonNull(user)){
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
            modelAndView.addObject("error", "User created successfully")
                    .setViewName("redirect:/login");
        }
        else
            modelAndView.addObject("error", "User with such username already exist!")
                    .setViewName("register");
        return modelAndView;
    }

    @GetMapping("/regitrationConfirm")
    public ModelAndView confirmRegistration(HttpServletRequest request, @RequestParam("token") String token, ModelAndView modelAndView){
        Locale locale = request.getLocale();

        VerificationToken verificationToken = personService.getVerificationToken(token);
        if(verificationToken == null){
            modelAndView.addObject("message", "invalid token");
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        Person person = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            modelAndView.addObject("message", "token expired");
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }
        person.setEnabled(true);
        personService.activateUser(person.getId());
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }
}
