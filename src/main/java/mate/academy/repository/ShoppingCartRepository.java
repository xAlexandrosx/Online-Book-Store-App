package mate.academy.repository;

import java.util.Optional;
import mate.academy.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> getShoppingCartByUserId(Long userId);
}
