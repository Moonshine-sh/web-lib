package by.ginel.weblib.controller;

import by.ginel.weblib.dto.PersonCredGetDto;
import by.ginel.weblib.dto.PersonCredUpdateDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.dto.PersonUpdateDto;
import by.ginel.weblib.mapper.PersonCredMapper;
import by.ginel.weblib.service.api.PersonCredService;
import by.ginel.weblib.service.api.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/userProfile")
@RequiredArgsConstructor
public class UserProfileController {

    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;
    private final PersonCredService personCredService;
    private final PersonCredMapper personCredMapper;

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ModelAndView userProfile() {
        PersonGetDto person = personService.getById(getPersonFromContext().getPersonId());
        return new ModelAndView("userProfile").addObject("person", person);
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile() {
        PersonGetDto person = personService.getById(getPersonFromContext().getPersonId());
        return new ModelAndView("editProfile").addObject("person", person);
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView confirmEdit(@ModelAttribute PersonUpdateDto personUpdateDto, @ModelAttribute PersonCredUpdateDto personCredUpdateDto) {
        PersonGetDto personGetDto = personService.getById(getPersonFromContext().getPersonId());
        PersonCredGetDto personCredGetDto = getPersonFromContext();
        setAttributesForUpdateDto(personUpdateDto, personGetDto);
        personService.update(personUpdateDto);
        return new ModelAndView("redirect:/userProfile").addObject("person", personUpdateDto);
    }

    @GetMapping("/changePassword")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView changePassword() {
        return new ModelAndView("changePassword");
    }

    @PostMapping("/changePassword")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView confirmChangePassword(@RequestParam String oldPassword, @RequestParam String newPassword,
                                              @RequestParam String confirmPassword) {
        PersonCredGetDto personCred = getPersonFromContext();
        if (!passwordEncoder.matches(oldPassword, personCred.getPassword()))
            return new ModelAndView("changePassword").addObject("error", "Old password doesnt match");
        if (!newPassword.equals(confirmPassword))
            return new ModelAndView("changePassword").addObject("error", "New passwords dont match");
        personCred.setPassword(passwordEncoder.encode(newPassword));
        personCredService.update(personCredMapper.mapToPersonCredUpdateDto(personCred));
        return new ModelAndView("redirect:/userProfile").addObject("message", "Password changed successfully");
    }

    private void setAttributesForUpdateDto(PersonUpdateDto personUpdateDto, PersonGetDto personGetDto) {
        personUpdateDto.setId(personGetDto.getId());
        personUpdateDto.setRole(personGetDto.getRole());
    }

    private PersonCredGetDto getPersonFromContext() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return personCredService.findByLogin(user.getUsername());
    }
}
