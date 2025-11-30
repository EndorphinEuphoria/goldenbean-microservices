package com.github.cart_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.cart_service.exception.CartNotFoundException;
import com.github.cart_service.model.Cart;
import com.github.cart_service.model.CartItem;
import com.github.cart_service.repository.CartItemRepository;
import com.github.cart_service.repository.CartRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Cart getOrCreateCart() {
        return getOrCreateCart(null); // llama al original con null
    }

    
    public Cart getOrCreateCart(Long cartId) {
        if (cartId == null) {
            Cart newCart = new Cart();
            newCart.setTotalItems(0);
            newCart.setTotalPriceCLP(0);
            newCart.setItems(new ArrayList<>()); // <- LISTA MUTABLE

            // Guardar y devolver el carrito nuevo
            return cartRepository.save(newCart);
        }

        // Si ya existe, devolverlo
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id " + cartId));
    }

    public Cart getCart(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id " + id));
    }

    @Transactional
    public Cart addItem(Long cartId, CartItem newItem) {
        Cart cart = getOrCreateCart(cartId);

        newItem.setCart(cart);
        cartItemRepository.save(newItem);

        recalculateCart(cart);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateItem(Long cartId, Long itemId, Integer quantity) {
        Cart cart = getCart(cartId);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new CartNotFoundException("Item not found with id " + itemId));

        item.setQuantity(quantity);
        cartItemRepository.save(item);

        recalculateCart(cart);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeItem(Long cartId, Long itemId) {
        Cart cart = getCart(cartId);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new CartNotFoundException("Item not found with id " + itemId));

        cartItemRepository.delete(item);

        recalculateCart(cart);
        return cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(Long cartId) {
        Cart cart = getCart(cartId);

        cartItemRepository.deleteAll(cart.getItems());
        cartRepository.delete(cart);
    }

    private void recalculateCart(Cart cart) {
        int totalItems = 0;
        int totalPrice = 0;

        List<CartItem> items = cartItemRepository.findByCart(cart);
        cart.setItems(items);

        for (CartItem item : items) {
            totalItems += item.getQuantity();
            totalPrice += item.getPriceCLP() * item.getQuantity();
        }

        cart.setTotalItems(totalItems);
        cart.setTotalPriceCLP(totalPrice);
    }
}
