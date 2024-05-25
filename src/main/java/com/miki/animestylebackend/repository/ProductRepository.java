package com.miki.animestylebackend.repository;

import com.miki.animestylebackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByProductNameContainsIgnoreCase(String productName);
}
