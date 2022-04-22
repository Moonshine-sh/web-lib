package by.ginel.weblib.controller;

import by.ginel.weblib.dto.PersonCreateDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.entity.PersonRole;
import by.ginel.weblib.service.api.OrderService;
import by.ginel.weblib.service.api.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping
public class LoginController {

    @Autowired
    PersonService personService;

    @Autowired
    OrderService orderService;

    @GetMapping("/login")
    public ModelAndView getLogin(ModelAndView modelAndView){

        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView postLogin(@RequestParam("username") String login, @RequestParam("pass") String password, HttpServletRequest request){

        PersonGetDto person = personService.findByLogin(login);
        if(person != null && person.getPassword().equals(password)){
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(3600);
            session.setAttribute("person",person);
            return new ModelAndView("redirect:/home");
        }else{
            return new ModelAndView("login").addObject("error","Incorrect login or password");
        }
    }

    @GetMapping("/register")
    public ModelAndView getRegister(ModelAndView modelAndView){

        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView postRegister(@ModelAttribute PersonCreateDto person){
        PersonGetDto personFromDB = personService.findByLogin(person.getLogin());
        if(personFromDB == null){
            person.setLocked(false);
            person.setRole(PersonRole.USER.toString());
            personService.save(person);
            return new ModelAndView("login").addObject("error","User created successfully");
        }else{
            return new ModelAndView("register").addObject("error","User with such username already exist!");
        }
    }
}
