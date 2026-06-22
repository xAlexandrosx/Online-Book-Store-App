package mate.academy.service;

import mate.academy.dto.cartitem.CreateCartItemRequestDto;
import mate.academy.dto.shoppingcart.ShoppingCartDto;
import mate.academy.model.User;

public interface ShoppingCartService {

    void createShoppingCart(User user);

    ShoppingCartDto getByOwnerId(Long userId);

    ShoppingCartDto addCartItemToCart(Long userId, CreateCartItemRequestDto requestDto);

    ShoppingCartDto updateCartItemQuantity(Long userId, Long cartItemId, Integer quantity);

    void removeCartItemFromCart(Long userId, Long cartItemId);
}
