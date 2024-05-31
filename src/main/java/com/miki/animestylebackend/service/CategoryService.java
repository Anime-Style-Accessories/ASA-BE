package com.miki.animestylebackend.service;


import com.miki.animestylebackend.dto.*;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    PageData<CategoryData> getAllCategories(int page, int size);
    List<CategoryData> getAllCategories();
    Category getCategoryByName(String category);
    PageData<CategoryData> getAllCategoriesByNameContaining(String name, int page, int size);
    CategoryDto createCategory(CreateCategoryRequest category);
    CategoryDto updateCategory(UUID uuid, UpdateCategoryRequest category);
    void deleteCategory(UUID categoryId);

    CategoryDto getCategoryById(UUID id);
    PageData<CategoryDto> getAllProductGroupByCategory(int page, int size);
    PageData<GetProductGroupByCategoryData> getProductGroupByCategory(List<UUID> uuids, int page, int size);
}
