package com.miki.animestylebackend.repository;

import com.miki.animestylebackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByNameContaining(String name);

    void deleteById(UUID categoryId);


    Optional<Category> findByNameIgnoreCase(String name);
}
