package by.ginel.weblib.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
public class Person extends AbstractEntity {

    private String firstName;
    private String lastName;
    private Boolean locked;
    private Boolean enabled;
    private String login;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private PersonRole role;

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Order> orders;
}
