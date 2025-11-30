package com.github.cart_service.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Long coffeeId;
    private String coffeeName;
    private Integer priceCLP;
    private Integer quantity;
    private Integer subtotal;
}
