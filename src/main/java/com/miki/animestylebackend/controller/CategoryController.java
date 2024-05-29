package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.CategoryData;
import com.miki.animestylebackend.dto.CategoryDto;
import com.miki.animestylebackend.dto.CreateCategoryRequest;
import com.miki.animestylebackend.dto.page.PageData;
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
public class CategoryController extends BaseController{
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/all")
    public PageData<CategoryData> getAllCategoriesByNameContaining(@RequestParam(required = false) String name,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        if (name != null) {
            return categoryService.getAllCategoriesByNameContaining(name, page, size);
        }
        return categoryService.getAllCategories(page, size);
    }

    @PostMapping("/create")
    public CategoryDto createCategory(@RequestBody CreateCategoryRequest category) {
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }
}
