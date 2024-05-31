package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.CategoryData;
import com.miki.animestylebackend.dto.CategoryDto;
import com.miki.animestylebackend.dto.CreateCategoryRequest;
import com.miki.animestylebackend.dto.UpdateCategoryRequest;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.mapper.CategoryMapper;
import com.miki.animestylebackend.model.Category;
import com.miki.animestylebackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Override
    public PageData<CategoryData> getAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryData> categories = categoryRepository.findAll(pageable).map(categoryMapper::toCategoryData);
        return new PageData<>(categories, "Categories found successfully");
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name).orElseThrow();
    }


    @Override
    public PageData<CategoryData> getAllCategoriesByNameContaining(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryData> categoryDtoPage = categoryRepository.findByNameContaining(name, pageable).map(categoryMapper::toCategoryData);
        return new PageData<>(categoryDtoPage, "Categories found successfully");
    }

    @Override
    public CategoryDto createCategory(CreateCategoryRequest createCategoryRequest) {
        if (categoryRepository.existsByName(createCategoryRequest.getName())) {
            throw new RuntimeException("Category with name " + createCategoryRequest.getName() + " already exists");
        }
        Category category = Category.builder()
                .name(createCategoryRequest.getName())
                .description(createCategoryRequest.getDescription())
                .build();
        return categoryMapper.toCategoryDto(categoryRepository.save(category), "Category created successfully");
    }

    @Override
    public CategoryDto updateCategory(UUID uuid, UpdateCategoryRequest category) {
        Category categoryToUpdate = categoryRepository.findById(uuid).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryToUpdate.setName(category.getName());
        categoryToUpdate.setDescription(category.getDescription());
        return categoryMapper.toCategoryDto(categoryRepository.save(categoryToUpdate), "Category updated successfully");
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public CategoryDto getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toCategoryDto(category, "Category found successfully");
    }
}

