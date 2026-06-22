package mate.academy.service.impl;

import java.util.HashSet;
import lombok.AllArgsConstructor;
import mate.academy.dto.cartitem.CreateCartItemRequestDto;
import mate.academy.dto.shoppingcart.ShoppingCartDto;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.mapper.CartItemMapper;
import mate.academy.mapper.ShoppingCartMapper;
import mate.academy.model.Book;
import mate.academy.model.CartItem;
import mate.academy.model.ShoppingCart;
import mate.academy.model.User;
import mate.academy.repository.BookRepository;
import mate.academy.repository.CartItemRepository;
import mate.academy.repository.ShoppingCartRepository;
import mate.academy.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final BookRepository bookRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public void createShoppingCart(User owner) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartItems(new HashSet<>());
        shoppingCart.setUser(owner);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto getByOwnerId(Long userId) {
        ShoppingCart shoppingCart =
                shoppingCartRepository.getShoppingCartByUserId(userId).orElseThrow(
                        () -> new EntityNotFoundException(
                        "No shopping cart found for owner id: " + userId)
        );
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto addCartItemToCart(Long userId, CreateCartItemRequestDto requestDto) {
        ShoppingCart shoppingCart =
                shoppingCartRepository.getShoppingCartByUserId(userId).orElseThrow(
                        () -> new EntityNotFoundException(
                        "No shopping cart found for owner id: " + userId)
        );

        Book book = bookRepository.findById(requestDto.getBookId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "No book found with book id: " + requestDto.getBookId())
        );

        for (CartItem item : shoppingCart.getCartItems()) {
            if (item.getBook().getId().equals(book.getId())) {
                item.setQuantity(item.getQuantity() + requestDto.getQuantity());
                return shoppingCartMapper.toDto(shoppingCart);
            }
        }

        CartItem cartItem = cartItemMapper.toEntity(requestDto);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(book);
        cartItemRepository.save(cartItem);

        shoppingCart.getCartItems().add(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto updateCartItemQuantity(Long userId,
                                                  Long cartItemId,
                                                  Integer quantity) {
        ShoppingCart shoppingCart =
                shoppingCartRepository.getShoppingCartByUserId(userId).orElseThrow(
                        () -> new EntityNotFoundException(
                        "No shopping cart found for owner id: " + userId)
        );
        CartItem cartItem =
                cartItemRepository.findById(cartItemId).orElseThrow(
                        () -> new EntityNotFoundException(
                        "No cart item found with id: " + cartItemId)
        );

        if (!cartItem.getShoppingCart().getId().equals(shoppingCart.getId())) {
            throw new IllegalArgumentException(
                    "Cart item does not belong to this user's shopping cart.");
        }

        cartItem.setQuantity(quantity.intValue());
        cartItemRepository.save(cartItem);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public void removeCartItemFromCart(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart =
                shoppingCartRepository.getShoppingCartByUserId(userId).orElseThrow(
                        () -> new EntityNotFoundException(
                        "No shopping cart found for owner id: " + userId)
        );

        CartItem cartItem =
                cartItemRepository.findById(cartItemId).orElseThrow(
                        () -> new EntityNotFoundException(
                        "No cart item found with id: " + cartItemId)
        );

        if (!cartItem.getShoppingCart().getId().equals(shoppingCart.getId())) {
            throw new IllegalArgumentException(
                    "Cart item does not belong to this user's shopping cart.");
        }

        shoppingCart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
    }
}
