package by.ginel.weblib.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_order")
public class Order extends AbstractEntity {

    private String date;
    private Long price;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrderBook> books;
}
