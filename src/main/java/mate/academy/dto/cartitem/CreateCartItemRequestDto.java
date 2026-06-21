package mate.academy.dto.cartitem;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCartItemRequestDto {
    @NotNull
    private Long bookId;
    @NotNull
    private int quantity;
}
