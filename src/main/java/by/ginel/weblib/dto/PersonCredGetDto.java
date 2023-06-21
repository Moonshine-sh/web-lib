package by.ginel.weblib.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PersonCredGetDto extends AbstractGetDto {

    private Long id;
    private Long personId;
    private Boolean locked;
    private String login;
    private Boolean enabled;
    private String password;
}
