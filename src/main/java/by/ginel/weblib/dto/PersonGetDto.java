package by.ginel.weblib.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PersonGetDto extends AbstractGetDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Boolean locked;
    private String login;
    private String password;
    private String email;
    private String role;
}
