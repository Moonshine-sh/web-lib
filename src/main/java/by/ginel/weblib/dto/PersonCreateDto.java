package by.ginel.weblib.dto;

import by.ginel.weblib.entity.PersonRole;
import by.ginel.weblib.validator.constraint.ValidLogin;
import by.ginel.weblib.validator.constraint.ValidPassword;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PersonCreateDto extends AbstractCreateDto {

    @NotEmpty(message = "First name cant be NULL")
    private String firstName;
    @NotEmpty(message = "Last name cant be NULL")
    private String lastName;
    @NotEmpty(message = "Email cant be NULL")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email should be valid")
    private String email;
    private List<String> role;
    private String mobNum;
}
