package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.CreateProductRequest;
import com.miki.animestylebackend.dto.ProductDto;
import com.miki.animestylebackend.dto.UpdateProductRequest;
import com.miki.animestylebackend.mapper.ProductMapper;
import com.miki.animestylebackend.model.Product;
import com.miki.animestylebackend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Product")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    @Operation(
            description = "Get endpoint for admin",
            summary = "This is a summary for management get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable UUID id) {
        return productMapper.toProductDto(productService.getProductById(id));
    }

    @GetMapping("/getProductsBySearch")
    public List<ProductDto> getProducts(@RequestParam(value = "name", required = false) String text,
                                        @RequestParam(value = "category", required = false) String category) {
        List<Product> products;

        if (category != null) {
            products = productService.findProductsByCategoryNameContaining(category);
            if (text != null) {
                products = products.stream()
                        .filter(product -> product.getProductName().toLowerCase().contains(text.toLowerCase()))
                        .collect(Collectors.toList());
            }
        } else if (text != null) {
            products = productService.findProductsByCategoryNameContaining(text);
        } else {
            products = productService.getAllProducts();
        }

        return products.stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());
    }
    @PostMapping
    public ProductDto addProduct(@RequestBody CreateProductRequest product) {
        return productMapper.toProductDto(productService.addProduct(product));
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody UpdateProductRequest product) {
        return productMapper.toProductDto(productService.updateProduct(product));
    }

    @DeleteMapping
    public void deleteProduct(@RequestParam UUID id) {
        productService.deleteProduct(id);
    }
}
