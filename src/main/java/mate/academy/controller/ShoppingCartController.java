package mate.academy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.cartitem.CreateCartItemRequestDto;
import mate.academy.dto.cartitem.UpdateCartItemRequestDto;
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

@Tag(name = "Shopping Cart Management")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @Operation(summary = "Get shoppingcart")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public ShoppingCartDto getCart(
            @AuthenticationPrincipal User user) {
        return shoppingCartService.getByOwnerId(user.getId());
    }

    @PostMapping
    @Operation(summary = "Add item to shopping cart")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    public ShoppingCartDto addCartItemToCart(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CreateCartItemRequestDto requestDto) {

        return shoppingCartService.addCartItemToCart(user.getId(), requestDto);
    }

    @PutMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Update quantity of cart item.")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public ShoppingCartDto updateCartItemQuantity(
            @AuthenticationPrincipal User user,
            @PathVariable Long cartItemId,
            @RequestBody @Valid UpdateCartItemRequestDto requestDto) {

        return shoppingCartService
                .updateCartItemQuantity(user.getId(), cartItemId, requestDto.getQuantity());
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Remove item from cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    public void removeCartItemFromCart(
            @AuthenticationPrincipal User user,
            @PathVariable Long cartItemId) {

        shoppingCartService.removeCartItemFromCart(user.getId(), cartItemId);
    }
}
