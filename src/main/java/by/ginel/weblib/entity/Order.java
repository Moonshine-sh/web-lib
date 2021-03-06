package by.ginel.weblib.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_order")
public class Order extends AbstractEntity{

    private String date;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrderBook> books;
}
