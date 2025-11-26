package com.github.coffee_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.coffee_service.model.Coffee;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

}
