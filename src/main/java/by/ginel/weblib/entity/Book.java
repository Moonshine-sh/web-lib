package by.ginel.weblib.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book extends AbstractEntity {

    private String name;
    private String author;
    private String description;
    private Double price;
    private String picPath;
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @OneToMany(mappedBy = "book")
    private List<OrderBook> books;
}
