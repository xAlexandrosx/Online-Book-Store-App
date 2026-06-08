package mate.academy.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {

    private Long id;
    private String title;
    private BigDecimal price;
    private String author;
    private String isbn;
    private String description;
    private String coverImage;
}
