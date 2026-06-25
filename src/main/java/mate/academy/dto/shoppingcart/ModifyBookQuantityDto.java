package mate.academy.dto.shoppingcart;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyBookQuantityDto {
    @Positive
    private int quantity;
}
