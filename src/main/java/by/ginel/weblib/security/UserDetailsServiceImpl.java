package by.ginel.weblib.security;

import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.entity.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonDao personDao;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Person person = Optional.ofNullable(personDao.findByLogin(login))
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));

        return User.builder()
                .username(person.getLogin())
                .password(person.getPassword())
                .authorities(person.getRole().toString())
                .accountLocked(person.getLocked())
                .disabled(!person.getEnabled())
                .build();
    }
}
