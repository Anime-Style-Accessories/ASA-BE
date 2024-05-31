package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.*;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.exception.ProductNotFoundException;
import com.miki.animestylebackend.mapper.ProductMapper;
import com.miki.animestylebackend.model.Category;
import com.miki.animestylebackend.model.Product;
import com.miki.animestylebackend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    private final ProductMapper productMapper;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(UUID uuid) {
        return productRepository.findById(uuid)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product with id %s not found", uuid)));
    }

    @Override
    public PageData<ProductData> findProductsByNameContaining(String name, int page, int size, Sort.Direction sort, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, sortBy));
        return new PageData<>(productRepository.findByProductNameContainsIgnoreCase(name, pageable).map(productMapper::toProductData), "Find products by name successfully");
    }

    @Override
    public PageData<ProductData> findProductsByCategoryNameContaining(String category, int page, int size, Sort.Direction sort, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, sortBy));
        return new PageData<>(productRepository.findByCategory_NameContainsIgnoreCase(category, pageable).map(productMapper::toProductData), "Find products by category name successfully");
    }

    @Override
    public PageData<ProductData> getProductsByCategoryAndName(String category, String name, int page, int size, Sort.Direction sort, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, sortBy));
        Page<ProductData> products = productRepository
                .findByProductNameContainsAndCategory_NameAllIgnoreCase(name, category, pageable)
                .map(productMapper::toProductData);

        return new PageData<>(products, "Get products by category and name successfully");
    }

    @Override
    public PageData<ProductData> getProductsByName(String name, Integer page, Integer size, Sort.Direction sort, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, sortBy));
        Page<ProductData> products = productRepository.findByProductNameContainsIgnoreCase(name, pageable).map(productMapper::toProductData);
        return new PageData<>(products, "Get products by name successfully");
    }

    @Override
    public PageData<GetProductGroupByCategoryData> getCategoryAndProductByCategory(Integer page, Integer size, Sort.Direction sort, String sortBy) {
        List<GetProductGroupByCategoryData> getProductGroupByCategoryData = new LinkedList<>();
        List<CategoryData> categories = categoryService.getAllCategories();
        for(CategoryData category: categories){
            List<ProductData> products = productRepository.findByCategory_Name(category.getName())
                    .stream()
                    .map(productMapper::toProductData)
                    .collect(Collectors.toList());

            getProductGroupByCategoryData.add(new GetProductGroupByCategoryData(category, products));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, sortBy));
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), getProductGroupByCategoryData.size());
        Page<GetProductGroupByCategoryData> productDataPage = new PageImpl<>(getProductGroupByCategoryData.subList(start, end), pageable, getProductGroupByCategoryData.size());
        return new PageData<>(productDataPage, "Get category and product by category successfully");
    }

    @Override
    public PageData<ProductData> getProductsByListId(List<UUID> uuids, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ProductData> products = productRepository.findAllById(uuids).stream()
                .map(productMapper::toProductData)
                .collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), products.size());
        Page<ProductData> productDataPage = new PageImpl<>(products.subList(start, end), pageable, products.size());
        return new PageData<>(productDataPage, "Get products by list id successfully");
    }

    @Override
    public Product addProduct(CreateProductRequest createProductRequest) {
        Category category = categoryService.getCategoryByName(createProductRequest.getCategory());
        Product product = Product.builder()
                .productName(createProductRequest.getName())
                .productDescription(createProductRequest.getDescription())
                .productColor(createProductRequest.getColor())
                .productPrice(createProductRequest.getPrice())
                .productQuantity(createProductRequest.getQuantity())
                .category(category)
                .productImage(createProductRequest.getImage())
                .productSize(createProductRequest.getSize())
                .build();
        log.info("Product added: {}", product);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(UUID uuid, UpdateProductRequest createProductRequest) {
        Product product = getProductById(uuid);
        Category category = categoryService.getCategoryByName(createProductRequest.getCategory());
        product.setProductName(createProductRequest.getName());
        product.setProductDescription(createProductRequest.getDescription());
        product.setProductColor(createProductRequest.getColor());
        product.setProductPrice(createProductRequest.getPrice());
        product.setProductQuantity(createProductRequest.getQuantity());
        product.setCategory(category);
        log.info("Product updated: {}", product);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(UUID uuid) {
        productRepository.deleteById(uuid);
    }
}
