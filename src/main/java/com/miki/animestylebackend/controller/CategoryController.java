package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.CategoryDto;
import com.miki.animestylebackend.dto.CreateCategoryRequest;
import com.miki.animestylebackend.mapper.CategoryMapper;
import com.miki.animestylebackend.model.Category;
import com.miki.animestylebackend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/all")
    public List<CategoryDto> getAllCategoriesByNameContaining(@RequestParam(required = false) String name) {
        if (name != null) {
            return categoryService.getAllCategoriesByNameContaining(name).stream()
                    .map(categoryMapper::toCategoryDto)
                    .collect(Collectors.toList());
        }

        return categoryService.getAllCategories().stream()
                .map(categoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public Category createCategory(@RequestBody CreateCategoryRequest category) {
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }
}
