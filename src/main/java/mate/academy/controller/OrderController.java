package mate.academy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.order.OrderDto;
import mate.academy.dto.order.UpdateStatusRequest;
import mate.academy.dto.orderitem.OrderItemDto;
import mate.academy.model.User;
import mate.academy.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order Management")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Place an order")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public OrderDto placeOrder(
            @AuthenticationPrincipal User user,
            @NotNull String shippingAddress) {

        return orderService.placeOrder(user.getId(), shippingAddress);
    }

    @GetMapping
    @Operation(summary = "View order history")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public Page<OrderDto> viewOrderHistory(
            @AuthenticationPrincipal User user,
            Pageable pageable) {

        return orderService.viewOrderHistory(user.getId(), pageable);
    }

    @GetMapping("/{orderId}/items")
    @Operation(summary = "View items in order.")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public Page<OrderItemDto> viewItemsInOrder(
            @AuthenticationPrincipal User user,
            @PathVariable Long orderId,
            Pageable pageable) {

        return orderService.viewItemsInOrder(user.getId(), orderId, pageable);
    }

    @GetMapping("/{orderId}/items/{id}")
    @Operation(summary = "View specific item from order")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public OrderItemDto viewItemFromOrder(
            @AuthenticationPrincipal User user,
            @PathVariable Long orderId,
            @PathVariable Long id) {

        return orderService.viewItemFromOrder(user.getId(), orderId, id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update order status")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public OrderDto updateOrderStatus(
            @PathVariable Long id,
            @RequestBody @Valid UpdateStatusRequest request) {

        return orderService.updateOrderStatus(id, request.status());
    }
}
