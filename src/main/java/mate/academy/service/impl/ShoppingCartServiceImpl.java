package mate.academy.service.impl;

import java.util.Objects;
import lombok.AllArgsConstructor;
import mate.academy.dto.shoppingcart.AddBookToCartDto;
import mate.academy.dto.shoppingcart.ModifyBookQuantityDto;
import mate.academy.dto.shoppingcart.ShoppingCartDto;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.mapper.ShoppingCartMapper;
import mate.academy.model.Book;
import mate.academy.model.CartItem;
import mate.academy.model.ShoppingCart;
import mate.academy.repository.BookRepository;
import mate.academy.repository.ShoppingCartRepository;
import mate.academy.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository cartRepository;
    private final ShoppingCartMapper cartMapper;
    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public ShoppingCartDto getByOwnerId(Long ownerId) {
        ShoppingCart shoppingCart = cartRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Couldn't find shopping cart for owner ID: " + ownerId)
        );
        return cartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto addBookToCart(Long ownerId, AddBookToCartDto requestDto) {
        ShoppingCart shoppingCart = cartRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Couldn't find shopping cart for owner ID: " + ownerId)
        );

        for (CartItem item : shoppingCart.getCartItems()) {
            if (Objects.equals(item.getBook().getId(), requestDto.getBookId())) {
                item.setQuantity(item.getQuantity() + requestDto.getQuantity());
                return cartMapper.toDto(shoppingCart);
            }
        }

        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Couldn't find book with id: " + requestDto.getBookId())
        );

        CartItem newItem = new CartItem();
        newItem.setBook(book);
        newItem.setShoppingCart(shoppingCart);
        newItem.setQuantity(requestDto.getQuantity());

        shoppingCart.getCartItems().add(newItem);
        return cartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto updateBookQuantity(Long ownerId, Long cartItemId,
                                              ModifyBookQuantityDto requestDto) {
        ShoppingCart shoppingCart = cartRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Couldn't find shopping cart for owner ID: " + ownerId)
        );

        CartItem modifiedItem = shoppingCart.getCartItems().stream()
                .filter(item -> Objects.equals(item.getId(), cartItemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Shopping cart does not contain item ID: " + cartItemId));

        modifiedItem.setQuantity(requestDto.getQuantity());
        return cartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public void removeBookFromCart(Long ownerId, Long cartItemId) {
        ShoppingCart shoppingCart = cartRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Couldn't find shopping cart for owner ID: " + ownerId)
        );

        CartItem deletedItem = shoppingCart.getCartItems().stream()
                .filter(item -> Objects.equals(item.getId(), cartItemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Shopping cart does not contain item ID: " + cartItemId));

        shoppingCart.getCartItems().remove(deletedItem);
    }
}
