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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@SQLDelete(sql = "UPDATE books SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(nullable = false)
    @Getter @Setter
    private String title;

    @Column(nullable = false)
    @Getter @Setter
    private String author;

    @Column(nullable = false, unique = true)
    @Getter @Setter
    private String isbn;

    @Column(nullable = false)
    @Getter @Setter
    private BigDecimal price;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private String coverImage;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT(1)")
    @Getter @Setter
    private boolean isDeleted = false;
}
