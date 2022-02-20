package dev.tanutapi.assignmentjavabootcamp.userShippingAddress;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserShippingAddressRepository extends JpaRepository<UserShippingAddress, Integer> {
    Optional<UserShippingAddress> findByUserId(Integer userId);
}
