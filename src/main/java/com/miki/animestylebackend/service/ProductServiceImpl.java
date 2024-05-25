package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.CreateProductRequest;
import com.miki.animestylebackend.dto.UpdateProductRequest;
import com.miki.animestylebackend.exception.ProductNotFoundException;
import com.miki.animestylebackend.model.Category;
import com.miki.animestylebackend.model.Product;
import com.miki.animestylebackend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

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
    public List<Product> findProductsByCategoryNameContaining(String categoryName) {
        return productRepository.findByProductNameContainsIgnoreCase(categoryName);
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
