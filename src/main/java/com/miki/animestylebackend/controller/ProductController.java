package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.CreateProductRequest;
import com.miki.animestylebackend.dto.ProductDto;
import com.miki.animestylebackend.dto.UpdateProductRequest;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.mapper.ProductMapper;
import com.miki.animestylebackend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public ResponseEntity<PageData<ProductDto>> getProductsByName(@RequestParam(value = "name", required = false) String name,
                                                            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                                            @RequestParam(value = "sort", required = false, defaultValue = "ASC") Sort.Direction sort,
                                                            @RequestParam(value = "sortBy", required = false, defaultValue = "productPrice") String sortBy  ) {


         return ResponseEntity.ok(productService.getProductsByName(name, page, size, sort, sortBy));
    }

    @GetMapping("/getProductsByCategoryAndName")
    public ResponseEntity<PageData<ProductDto>> getProductsByCategoryAndName(@RequestParam(value = "category", required = false) String category,
                                                            @RequestParam(value = "name", required = false) String name,
                                                            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                                            @RequestParam(value = "sort", required = false, defaultValue = "ASC") Sort.Direction sort,
                                                            @RequestParam(value = "sortBy", required = false, defaultValue = "productPrice") String sortBy  ) {
        return ResponseEntity.ok(productService.getProductsByCategoryAndName(category, name, page, size, sort, sortBy));
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
