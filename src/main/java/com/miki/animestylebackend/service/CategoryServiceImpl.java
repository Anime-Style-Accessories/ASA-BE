package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.CategoryDto;
import com.miki.animestylebackend.dto.CreateCategoryRequest;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.mapper.CategoryMapper;
import com.miki.animestylebackend.model.Category;
import com.miki.animestylebackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
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
    public PageData<CategoryDto> getAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryDto> categories = categoryRepository.findAll(pageable).map(categoryMapper::toCategoryDto);
        return new PageData<>(categories);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name).orElseThrow();
    }


    @Override
    public PageData<CategoryDto> getAllCategoriesByNameContaining(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryDto> categoryDtoPage = categoryRepository.findByNameContaining(name, pageable).map(categoryMapper::toCategoryDto);
        return new PageData<>(categoryDtoPage);
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

