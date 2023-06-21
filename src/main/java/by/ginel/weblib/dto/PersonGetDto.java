package by.ginel.weblib.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PersonGetDto extends AbstractGetDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> role;
    private String mobNum;
}
