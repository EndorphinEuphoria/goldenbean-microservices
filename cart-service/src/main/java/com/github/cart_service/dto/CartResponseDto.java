package com.github.cart_service.dto;

import java.util.List;

import lombok.Data;

@Data
public class CartResponseDto {
    private Long cartId;
    private Integer totalItems;
    private Integer totalPriceCLP;
    private List<CartItemDto> items;
}
