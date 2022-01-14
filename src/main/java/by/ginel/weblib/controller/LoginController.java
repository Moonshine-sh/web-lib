package by.ginel.weblib.controller;

import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.entity.Person;
import by.ginel.weblib.service.api.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    PersonService personService;

    @GetMapping()
    public ModelAndView getLogin(ModelAndView modelAndView){

        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping()
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
}
