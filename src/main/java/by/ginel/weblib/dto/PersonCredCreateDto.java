package by.ginel.weblib.dto;

import by.ginel.weblib.validator.constraint.ValidLogin;
import by.ginel.weblib.validator.constraint.ValidPassword;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PersonCredCreateDto extends AbstractCreateDto{

    private Long personId;
    private Boolean locked;
    private Boolean enabled;
    @ValidLogin
    private String login;
    @ValidPassword
    private String password;
}
