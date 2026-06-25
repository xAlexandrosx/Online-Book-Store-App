package mate.academy.mapper;

import mate.academy.config.MapperConfig;
import mate.academy.dto.shoppingcart.CartItemDto;
import mate.academy.dto.shoppingcart.ShoppingCartDto;
import mate.academy.model.CartItem;
import mate.academy.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(source = "owner.id", target = "userId")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
