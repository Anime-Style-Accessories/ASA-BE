package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.*;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Product")
public class ProductController extends BaseController{
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
    public ResponseEntity<ProductDto> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productMapper.toProductDto(productService.getProductById(id), "Product found successfully"));
    }

    @PostMapping("/getProductByListId")
    public ResponseEntity<PageData<ProductData>> getProductsByListId(@RequestBody List<UUID> uuids,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getProductsByListId(uuids, page, size));
    }

    @GetMapping("/getCategoryAndProductByCategory")
    public ResponseEntity<PageData<GetProductGroupByCategoryData>> getCategoryAndProductByCategory(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                                   @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                                                                   @RequestParam(value = "sort", required = false, defaultValue = "ASC") Sort.Direction sort,
                                                                                   @RequestParam(value = "sortBy", required = false, defaultValue = "productPrice") String sortBy) {
        return ResponseEntity.ok(productService.getCategoryAndProductByCategory(page, size, sort, sortBy));
    }

    @GetMapping("/getProductsBySearch")
    public ResponseEntity<PageData<ProductData>> getProductsByName(@RequestParam(value = "name") String name,
                                                            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                                            @RequestParam(value = "sort", required = false, defaultValue = "ASC") Sort.Direction sort,
                                                            @RequestParam(value = "sortBy", required = false, defaultValue = "productPrice") String sortBy  ) {


         return ResponseEntity.ok(productService.getProductsByName(name, page, size, sort, sortBy));
    }

    @GetMapping("/getProductsByCategoryAndName")
    public ResponseEntity<PageData<ProductData>> getProductsByCategoryAndName(@RequestParam(value = "category") String category,
                                                                              @RequestParam(value = "name", required = false) String name,
                                                                              @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                              @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                                                              @RequestParam(value = "sort", required = false, defaultValue = "ASC") Sort.Direction sort,
                                                                              @RequestParam(value = "sortBy", required = false, defaultValue = "productPrice") String sortBy  ) {
        return ResponseEntity.ok(productService.getProductsByCategoryAndName(category, name, page, size, sort, sortBy));
    }
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody CreateProductRequest product) {
        return ResponseEntity.ok(productMapper.toProductDto(productService.addProduct(product), "Product added successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable UUID id, @RequestBody UpdateProductRequest product) {
        return ResponseEntity.ok(productMapper.toProductDto(productService.updateProduct(id, product), "Product updated successfully"));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }
}
