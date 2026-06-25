package mate.academy.dto.shoppingcart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBookToCartDto {
    @NotNull
    private Long bookId;
    @Positive
    private int quantity;
}
