package com.github.cart_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.cart_service.model.Cart;
import com.github.cart_service.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    Optional<CartItem> findByCoffeeId(Long coffeeId);
    List<CartItem> findByCart(Cart cart);
}
