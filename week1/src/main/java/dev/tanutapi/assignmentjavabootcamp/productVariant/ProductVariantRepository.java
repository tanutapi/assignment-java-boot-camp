package dev.tanutapi.assignmentjavabootcamp.productVariant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    Optional<ProductVariant> findByProductIdAndName(Integer productId, String name);
}
