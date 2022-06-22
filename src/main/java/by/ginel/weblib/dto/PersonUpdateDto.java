package by.ginel.weblib.dto;

import by.ginel.weblib.entity.PersonRole;
import by.ginel.weblib.validator.constraint.ValidLogin;
import by.ginel.weblib.validator.constraint.ValidPassword;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PersonUpdateDto extends AbstractUpdateDto {

    private Long id;
    @NotEmpty(message = "First name cant be NULL")
    private String firstName;
    @NotEmpty(message = "Last name cant be NULL")
    private String lastName;
    private Boolean locked;
    private Boolean enabled;
    @ValidLogin
    private String login;
    @ValidPassword
    private String password;
    @NotEmpty(message = "Email cant be NULL")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email should be valid")
    private String email;
    private String role;

    public PersonRole getRole() {
        return PersonRole.valueOf(role);
    }
}
