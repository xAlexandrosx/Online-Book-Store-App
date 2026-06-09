package mate.academy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

public class CreateBookRequestDto {
    @Getter
    @Setter
    @NotBlank
    private String title;
    @Getter
    @Setter
    @NotBlank
    private String author;
    @Getter
    @Setter
    @NotBlank
    private String isbn;
    @Getter
    @Setter
    @NotBlank
    private String description;
    @Getter
    @Setter
    @NotNull
    @Positive
    private BigDecimal price;
    @Getter
    @Setter
    private String coverImage;
}
