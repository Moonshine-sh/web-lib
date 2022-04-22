package by.ginel.weblib.controller;


import by.ginel.weblib.dto.OrderGetDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.dto.PersonUpdateDto;
import by.ginel.weblib.entity.PersonRole;
import by.ginel.weblib.service.api.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class AdminController {

    @Autowired
    PersonService personService;

    @GetMapping("/users")
    public ModelAndView getUserList(HttpServletRequest request){

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");
        if(person != null  && person.getRole() == PersonRole.ADMIN.toString()){
            List<PersonGetDto> users = personService.getAll();
            if (users == null || users.size()==0){
                session.setAttribute("error","you dont have active users");
                return new ModelAndView("redirect:/mistake");
            }
            return new ModelAndView("users")
                    .addObject("users",users)
                    .addObject("person",session.getAttribute("person"));
        }
        else{
            session.setAttribute("error", "You cant access this feature");
            return new ModelAndView("redirect:/mistake");
        }
    }

    @GetMapping("/user/{id}/lock")
    public ModelAndView lock(@PathVariable Long id, HttpServletRequest request){

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");
        if(person != null  && person.getRole() == PersonRole.ADMIN.toString()){

            try {
                PersonGetDto user = personService.getById(id);
                if (user.getRole() == PersonRole.ADMIN.toString()){
                    session.setAttribute("error","User is admin");
                    return new ModelAndView("redirect:/mistake");
                }
                personService.updateLocked(id);

                return new ModelAndView("redirect:/users");
            }catch (NullPointerException e){
                session.setAttribute("error","There is no user with such id");
                return new ModelAndView("redirect:/mistake");
            }
        }
        else{
            session.setAttribute("error", "You cant access this feature");
            return new ModelAndView("redirect:/mistake");
        }
    }
}
