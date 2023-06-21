package by.ginel.weblib.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Orders extends AbstractEntity {

    private String date;
    private Long price;

    @ManyToMany
    @JoinTable(name = "order_status", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "status_id"))
    private List<Status> status;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<OrderBook> books;
}
