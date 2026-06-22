package mate.academy.service;

import mate.academy.dto.order.OrderDto;
import mate.academy.dto.orderitem.OrderItemDto;
import mate.academy.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDto placeOrder(Long userId, String shippingAddress);

    Page<OrderDto> viewOrderHistory(Long userId, Pageable pageable);

    Page<OrderItemDto> viewItemsInOrder(Long userId, Long orderId, Pageable pageable);

    OrderItemDto viewItemFromOrder(Long userId, Long orderId, Long orderItemId);

    OrderDto updateOrderStatus(Long orderId, Order.Status status);
}
