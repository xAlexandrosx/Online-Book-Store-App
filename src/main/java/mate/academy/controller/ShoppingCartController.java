package mate.academy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import mate.academy.dto.shoppingcart.AddBookToCartDto;
import mate.academy.dto.shoppingcart.ModifyBookQuantityDto;
import mate.academy.dto.shoppingcart.ShoppingCartDto;
import mate.academy.model.User;
import mate.academy.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management")
@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
public class ShoppingCartController {

    private final ShoppingCartService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get shopping cart")
    @PreAuthorize("hasRole('USER')")
    public ShoppingCartDto getShoppingCart(@AuthenticationPrincipal User user) {
        return service.getByOwnerId(user.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Add book to cart")
    public ShoppingCartDto addBookToCart(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody AddBookToCartDto requestDto) {
        return service.addBookToCart(user.getId(), requestDto);
    }

    @PutMapping("/cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update book quantity in cart")
    public ShoppingCartDto updateBookQuantity(
            @AuthenticationPrincipal User user,
            @PathVariable Long cartItemId,
            @Valid @RequestBody ModifyBookQuantityDto requestDto) {
        return service.updateBookQuantity(user.getId(), cartItemId, requestDto);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Remove book from cart")
    public void removeBookFromCart(
            @AuthenticationPrincipal User user,
            @PathVariable Long cartItemId) {
        service.removeBookFromCart(user.getId(), cartItemId);
    }
}
