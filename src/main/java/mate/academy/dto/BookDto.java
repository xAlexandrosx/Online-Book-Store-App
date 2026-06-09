package mate.academy.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

public class BookDto {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private BigDecimal price;
    @Getter
    @Setter
    private String author;
    @Getter
    @Setter
    private String isbn;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private String coverImage;
}
