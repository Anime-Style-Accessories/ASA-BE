package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.CreateProductRequest;
import com.miki.animestylebackend.dto.ProductDto;
import com.miki.animestylebackend.dto.UpdateProductRequest;
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
    public PageData<ProductDto> findProductsByNameContaining(String name, int page, int size, Sort.Direction sort, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, sortBy));
        return new PageData<>(productRepository.findByProductNameContainsIgnoreCase(name, pageable).map(productMapper::toProductDto));
    }

    @Override
    public PageData<ProductDto> findProductsByCategoryNameContaining(String category, int page, int size, Sort.Direction sort, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, sortBy));
        return new PageData<>(productRepository.findByCategory_NameContainsIgnoreCase(category, pageable).map(productMapper::toProductDto));
    }

    @Override
    public PageData<ProductDto> getProductsByCategoryAndName(String category, String name, int page, int size, Sort.Direction sort, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, sortBy));
        Page<ProductDto> products = productRepository
                .findByProductNameContainsAndCategory_NameAllIgnoreCase(name, category, pageable)
                .map(productMapper::toProductDto);

        return new PageData<>(products);
    }

    @Override
    public PageData<ProductDto> getProductsByName(String name, Integer page, Integer size, Sort.Direction sort, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, sortBy));
        Page<ProductDto> products = productRepository.findByProductNameContainsIgnoreCase(name, pageable).map(productMapper::toProductDto);
        return new PageData<>(products);
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
                .build();
        log.info("Product added: {}", product);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(UpdateProductRequest createProductRequest) {
        Product product = productRepository.findById(createProductRequest.getId()).orElseThrow();
        product.setProductName(createProductRequest.getName());
        product.setProductDescription(createProductRequest.getDescription());
        product.setProductColor(createProductRequest.getColor());
        product.setProductPrice(createProductRequest.getPrice());
        product.setProductQuantity(createProductRequest.getQuantity());
        log.info("Product updated: {}", product);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(UUID uuid) {
        productRepository.deleteById(uuid);
    }
}
