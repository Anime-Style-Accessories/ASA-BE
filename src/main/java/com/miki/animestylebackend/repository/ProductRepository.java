package com.miki.animestylebackend.repository;

import com.miki.animestylebackend.dto.ProductDto;
import com.miki.animestylebackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findByProductNameContainsIgnoreCase(String productName, Pageable pageable);

    Page<Product> findByCategory_NameContainsIgnoreCase(String name, Pageable pageable);

    Page<Product> findByProductNameContainsAndCategory_NameAllIgnoreCase(String productName, String name, Pageable pageable);
}
