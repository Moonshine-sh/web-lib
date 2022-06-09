package by.ginel.weblib.entity;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_book")
public class OrderBook extends AbstractEntity {

    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "order_id")
    private Order order;
}
