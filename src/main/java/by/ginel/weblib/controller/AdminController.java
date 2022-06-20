package by.ginel.weblib.controller;


import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.service.api.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {


    private final PersonService personService;

    @GetMapping("/users")
    public ModelAndView getUserList(HttpServletRequest request) {

        HttpSession session = request.getSession();
        ModelAndView modelAndView = new ModelAndView();
        if (personService.isUsersEmpty()) {
            session.setAttribute("error", "you dont have active users");
            modelAndView.setViewName("redirect:/mistake");
            return modelAndView;
        }
        List<PersonGetDto> users = personService.getAll();
        modelAndView.addObject("users", users).setViewName("users");
        return modelAndView;
    }

    @GetMapping("/user/{id}/lock")
    public ModelAndView lock(@PathVariable Long id, HttpServletRequest request) {

        HttpSession session = request.getSession();
        ModelAndView modelAndView = new ModelAndView();
        try {
            if (personService.isAdmin(id)) {
                session.setAttribute("error", "User is admin");
                modelAndView.setViewName("redirect:/mistake");
                return modelAndView;
            }
            personService.updateLocked(id);
            modelAndView.setViewName("redirect:/users");
            return modelAndView;
        } catch (NullPointerException e) {
            session.setAttribute("error", "There is no user with such id");
            modelAndView.setViewName("redirect:/mistake");
            return modelAndView;
        }
    }
}
