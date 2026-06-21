package mate.academy.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.order.OrderDto;
import mate.academy.dto.orderitem.OrderItemDto;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.mapper.OrderItemMapper;
import mate.academy.mapper.OrderMapper;
import mate.academy.model.Order;
import mate.academy.model.OrderItem;
import mate.academy.model.ShoppingCart;
import mate.academy.model.User;
import mate.academy.repository.OrderItemRepository;
import mate.academy.repository.OrderRepository;
import mate.academy.repository.ShoppingCartRepository;
import mate.academy.repository.UserRepository;
import mate.academy.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public OrderDto placeOrder(Long userId) {

        ShoppingCart cart = shoppingCartRepository.getShoppingCartByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("No shopping cart for user with id: " + userId)
        );

        Set<OrderItem> orderItems = cart.getCartItems()
                .stream()
                .map(orderItemMapper::toEntityFromCartItem)
                .collect(Collectors.toSet());

        cart.getCartItems().clear();
        shoppingCartRepository.save(cart);

        Order order = new Order();

        BigDecimal total = BigDecimal.valueOf(0);
        for (OrderItem item : orderItems) {
            item.setOrder(order);
            BigDecimal multipliedPrice = item.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(multipliedPrice);
            orderItemRepository.save(item);
        }
        order.setOrderItems(orderItems);

        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find user with id: " + userId)
        );

        order.setShippingAddress(user.getShippingAddress());
        order.setUser(user);
        order.setTotal(total);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.Status.NEW);

        Order saved = orderRepository.save(order);
        return orderMapper.toDto(saved);
    }

    @Override
    public Page<OrderDto> viewOrderHistory(Long userId, Pageable pageable) {
        return orderRepository
                .findAllByUserId(userId, pageable)
                .map(orderMapper::toDto);
    }

    @Override
    public Page<OrderItemDto> viewItemsInOrder(Long userId, Long orderId, Pageable pageable) {
        return orderItemRepository
                .findAllByOrderIdAndOrderUserId(orderId, userId, pageable)
                .map(orderItemMapper::toDto);
    }

    @Override
    public OrderItemDto viewItemFromOrder(Long userId, Long orderId, Long orderItemId) {

        OrderItem orderItem = orderItemRepository
                .getOrderItemByIdAndOrderIdAndOrderUserId(orderItemId, orderId, userId);
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, Order.Status status) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("No order found with id: " + orderId)
        );
        order.setStatus(status);
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }
}
