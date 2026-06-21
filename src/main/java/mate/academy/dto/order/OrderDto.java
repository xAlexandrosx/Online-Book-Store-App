package mate.academy.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import mate.academy.dto.orderitem.OrderItemDto;
import mate.academy.dto.user.UserResponseDto;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private UserResponseDto userResponseDto;
    private String status;
    private BigDecimal total;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private Set<OrderItemDto> orderItems;
}
