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
    private String email;
    private String mobNum;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "person_role", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<PersonRole> role;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Orders> orders;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private PersonCred credentials;
}
