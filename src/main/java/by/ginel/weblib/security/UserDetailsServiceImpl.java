package by.ginel.weblib.security;

import by.ginel.weblib.dao.api.PersonCredDao;
import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.entity.Person;
import by.ginel.weblib.entity.PersonCred;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonCredDao personCredDao;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        PersonCred personCred = Optional.ofNullable(personCredDao.findByLogin(login))
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));

        return User.builder()
                .username(personCred.getLogin())
                .password(personCred.getPassword())
                .authorities(personCred.getPerson().getRole().stream().map(role -> String.valueOf(role.getName())).collect(Collectors.toList()).toArray(String[]::new))
                .accountLocked(personCred.getLocked())
                .disabled(!personCred.getEnabled())
                .build();
    }
}
