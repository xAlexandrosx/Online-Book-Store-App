package mate.academy.repository;

import mate.academy.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Page<OrderItem> findAllByOrderIdAndOrderUserId(
            Long orderId, Long orderUserId, Pageable pageable);

    OrderItem getOrderItemByIdAndOrderIdAndOrderUserId(
            Long id, Long orderId, Long orderUserId);
}
