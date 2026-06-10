package mate.academy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    @Column(nullable = false)
    private String title;
    @Getter
    @Setter
    @Column(nullable = false)
    private String author;
    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String isbn;
    @Getter
    @Setter
    @Column(nullable = false)
    private BigDecimal price;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private String coverImage;
}
