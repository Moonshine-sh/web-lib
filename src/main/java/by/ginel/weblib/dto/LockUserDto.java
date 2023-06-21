package by.ginel.weblib.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Builder
public class LockUserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobNum;
    private Boolean locked;
}
