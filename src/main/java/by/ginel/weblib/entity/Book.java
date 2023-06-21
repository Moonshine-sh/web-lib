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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_genre", joinColumns = @JoinColumn(name = "book_id"),inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genre;

    @OneToMany(mappedBy = "book")
    @ToString.Exclude
    private List<OrderBook> books;
}
