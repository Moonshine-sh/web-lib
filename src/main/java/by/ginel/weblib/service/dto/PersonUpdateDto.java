package by.ginel.weblib.service.dto;

import by.ginel.weblib.dao.entity.PersonRole;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PersonUpdateDto extends AbstractUpdateDto{

    private Long id;
    private String firstName;
    private String lastName;
    private Boolean locked;
    private String login;
    private String password;
    private String email;
    private String role;

    public PersonRole getRole() {
        return PersonRole.valueOf(role);
    }
}
