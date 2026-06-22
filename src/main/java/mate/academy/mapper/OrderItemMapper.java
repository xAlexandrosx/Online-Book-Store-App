package mate.academy.mapper;

import mate.academy.config.MapperConfig;
import mate.academy.dto.orderitem.OrderItemDto;
import mate.academy.model.CartItem;
import mate.academy.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {

    @Mapping(target = "bookId", source = "book.id")
    OrderItemDto toDto(OrderItem orderItem);

    default OrderItem toEntityFromCartItem(CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(cartItem.getBook());
        orderItem.setPrice(cartItem.getBook().getPrice());
        orderItem.setQuantity(cartItem.getQuantity());
        return orderItem;
    }
}
