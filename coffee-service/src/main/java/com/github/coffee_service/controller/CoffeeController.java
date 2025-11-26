package com.github.coffee_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.coffee_service.dto.ResponseDto;
import com.github.coffee_service.model.Coffee;
import com.github.coffee_service.service.CoffeeService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api-v1/coffee")
@RequiredArgsConstructor
public class CoffeeController {

    private final CoffeeService coffeeService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<Coffee>>> getAll() {
        List<Coffee> coffees = coffeeService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseDto<>(
                "Coffees fetched correctly",
                200,
                coffees,
                coffees.size()
            )
        );
    }

    @GetMapping("/{idCoffee}")
    public ResponseEntity<ResponseDto<Coffee>> getById(@PathVariable Long idCoffee) {
        Coffee coffee = coffeeService.getById(idCoffee);

        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseDto<>(
                "Coffee fetched correctly.",
                200,
                coffee,
                1
            )
        );
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Coffee>> create(@RequestBody Coffee newCoffee) {
        Coffee coffee = coffeeService.create(newCoffee);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ResponseDto<>(
                "Coffee created correctly.",
                201,
                coffee,
                1
            )
        );
        
    }

    @PutMapping("/{idCoffee}")
    public ResponseEntity<ResponseDto<Coffee>> update(@PathVariable Long idCoffee, @RequestBody Coffee newCoffee) {
        Coffee coffee = coffeeService.update(idCoffee, newCoffee);

        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseDto<>(
                "Coffee updated correctly.",
                200,
                coffee,
                1
            )
        );
    }

    @DeleteMapping("/{idCoffee}")
    public ResponseEntity<ResponseDto<Void>> delete(@PathVariable Long idCoffee) {
        coffeeService.delete(idCoffee);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
            new ResponseDto<>(
                "Coffee deleted correctly.",
                200,
                0
            )
        );
    }
    
    
}