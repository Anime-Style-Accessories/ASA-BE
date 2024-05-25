package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.CreateCategoryRequest;
import com.miki.animestylebackend.model.Category;
import com.miki.animestylebackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name).orElseThrow();
    }


    @Override
    public List<Category> getAllCategoriesByNameContaining(String name) {
        return categoryRepository.findByNameContaining(name);
    }

    @Override
    public Category createCategory(CreateCategoryRequest createCategoryRequest) {
        Category category = Category.builder()
                .name(createCategoryRequest.getName())
                .description(createCategoryRequest.getDescription())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}

