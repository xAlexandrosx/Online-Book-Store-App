package mate.academy.dto.orderitem;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import mate.academy.dto.book.BookDto;
import mate.academy.dto.order.OrderDto;

@Getter
@Setter
public class OrderItemDto {
    private Long id;
    private Long bookId;
    private int quantity;
    private BigDecimal price;
}
