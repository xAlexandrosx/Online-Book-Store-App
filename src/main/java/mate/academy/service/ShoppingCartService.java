package mate.academy.service;

import mate.academy.dto.shoppingcart.AddBookToCartDto;
import mate.academy.dto.shoppingcart.ModifyBookQuantityDto;
import mate.academy.dto.shoppingcart.ShoppingCartDto;

public interface ShoppingCartService {

    ShoppingCartDto getByOwnerId(Long ownerId);

    ShoppingCartDto addBookToCart(Long ownerId, AddBookToCartDto requestDto);

    ShoppingCartDto updateBookQuantity(
            Long ownerId, Long cartItemId, ModifyBookQuantityDto requestDto);

    void removeBookFromCart(Long ownerId, Long cartItemId);
}
