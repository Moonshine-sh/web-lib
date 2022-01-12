package by.ginel.weblib.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "person")
public class Person extends AbstractEntity{

    private String firstName;
    private String lastName;
    private Boolean locked;
    private String login;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private PersonRole role;

    @OneToMany(mappedBy = "person")
    private List<Order> orders;
}
