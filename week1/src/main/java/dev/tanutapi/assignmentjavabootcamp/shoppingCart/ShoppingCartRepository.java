package dev.tanutapi.assignmentjavabootcamp.shoppingCart;

import dev.tanutapi.assignmentjavabootcamp.productVariant.ProductVariant;
import dev.tanutapi.assignmentjavabootcamp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    List<ShoppingCart> findByUserId(Integer userId);

    void deleteAllByUserIdAndProductVariantId(Integer userId, Integer productVariantId);

    void deleteAllByUserId(Integer userId);
}
