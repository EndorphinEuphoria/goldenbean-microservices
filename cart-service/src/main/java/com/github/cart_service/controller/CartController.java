package com.github.cart_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cart_service.dto.ResponseDto;
import com.github.cart_service.model.Cart;
import com.github.cart_service.model.CartItem;
import com.github.cart_service.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<ResponseDto<Cart>> createCart() {
        Cart cart = cartService.getOrCreateCart();

        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ResponseDto<>(
                "Cart created successfully",
                HttpStatus.CREATED.value(),
                cart,
                1
            )
        );
    }

    @Operation(
        summary = "Get cart by ID",
        description = "Returns cart details with items",
        responses = {
            @ApiResponse(responseCode = "200", description = "Cart returned correctly",
                content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Cart not found")
        }
    )
    @GetMapping("/{cartId}")
    public ResponseEntity<ResponseDto<Cart>> getCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCart(cartId);

        return ResponseEntity.ok(
            new ResponseDto<>(
                "Cart fetched correctly",
                HttpStatus.OK.value(),
                cart,
                1
            )
        );
    }

    @Operation(
        summary = "Add item to cart",
        description = "Adds a new item. Coffee data must be provided by frontend.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Item added successfully")
        }
    )
    @PostMapping("/{cartId}/items")
    public ResponseEntity<ResponseDto<Cart>> addItem(
        @PathVariable Long cartId,
        @RequestBody CartItem item
    ) {
        Cart cart = cartService.addItem(cartId, item);

        return ResponseEntity.ok(
            new ResponseDto<>(
                "Item added to cart",
                HttpStatus.OK.value(),
                cart,
                cart.getItems().size()
            )
        );
    }

    @Operation(
        summary = "Update item quantity",
        description = "Updates the quantity of a specific item",
        responses = {
            @ApiResponse(responseCode = "200", description = "Item updated successfully")
        }
    )
    @PutMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<ResponseDto<Cart>> updateItem(
        @PathVariable Long cartId,
        @PathVariable Long itemId,
        @RequestParam Integer quantity
    ) {
        Cart cart = cartService.updateItem(cartId, itemId, quantity);

        return ResponseEntity.ok(
            new ResponseDto<>(
                "Item updated correctly",
                HttpStatus.OK.value(),
                cart,
                cart.getItems().size()
            )
        );
    }

    @Operation(
        summary = "Remove item",
        description = "Deletes an item inside the cart",
        responses = {
            @ApiResponse(responseCode = "200", description = "Item removed successfully")
        }
    )
    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<ResponseDto<Cart>> removeItem(
        @PathVariable Long cartId,
        @PathVariable Long itemId
    ) {
        Cart cart = cartService.removeItem(cartId, itemId);

        return ResponseEntity.ok(
            new ResponseDto<>(
                "Item removed correctly",
                HttpStatus.OK.value(),
                cart,
                cart.getItems().size()
            )
        );
    }

    @Operation(
        summary = "Delete entire cart",
        description = "Removes all items and deletes the cart",
        responses = {
            @ApiResponse(responseCode = "204", description = "Cart deleted successfully")
        }
    )
    @DeleteMapping("/{cartId}")
    public ResponseEntity<ResponseDto<Void>> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
            new ResponseDto<>(
                "Cart deleted correctly",
                HttpStatus.NO_CONTENT.value(),
                0
            )
        );
    }
}
