package com.wjc.codetest.product.controller;

import com.wjc.codetest.product.model.request.CreateProductRequest;
import com.wjc.codetest.product.model.request.GetProductListRequest;
import com.wjc.codetest.product.model.domain.Product;
import com.wjc.codetest.product.model.request.UpdateProductRequest;
import com.wjc.codetest.product.model.response.ProductListResponse;
import com.wjc.codetest.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * [ProductController 개선 요약]
 *
 * 문제  
 * 1. RESTful API 설계 원칙 위반으로 인한 엔드포인트의 일관성 및 가독성 저하.
 * 2. HTTP 응답 상태 코드의 부적절한 사용 및 미흡한 예외 처리.  
 * 3. API 기본 경로의 모호성
 * 4. 메서드 이름의 중복
 * 5. @PathVariable(name = "...")의 불필요한 명시
 *
 * 원인  
 * 1. URL 설계 시 리소스(명사) 중심이 아닌 행위(동사) 중심으로 작성되어 RESTful 규칙이 제대로 반영되지 않음.  
 * 2. HTTP 상태 코드의 의미를 충분히 활용하지 못하고, 대부분 200 OK로 통일됨.  
 * 3. Service 계층에서 발생하는 RuntimeException에 대한 Controller 레벨의 방어 로직이 부재함.  
 * 4. API 기본 경로가 구체적이지 않아 확장성 및 유지보수성 저하.
 * 5. 메서드 이름이 중복되어 혼동을 초래할 수 있음.
 * 6. @PathVariable 어노테이션에서 name 속성은 변수명과 동일할 경우 생략 가능함에도 불구하고 불필요하게 명시됨.
 *
 * 개선안  
 * 1. RESTful 원칙 철저 적용: URL에서 동사를 제거하고, {리소스명}/{ID} 형태로 통일.  
 *    - 예: `/api/products/{productId}` 형태로 변경하고, HTTP Method를 CRUD 행위에 맞게 매핑.  
 * 2. HTTP 상태 코드 최적화:  
 *    - 생성 → 201 Created  
 *    - 삭제 성공 → 204 No Content  
 *    - 조회/수정 성공 → 200 OK  
 * 3. 예외 처리 보완:  
 *    - 리소스 조회/삭제/수정 실패 시 404 Not Found 반환.  
 *    - 메서드 내 try-catch 적용 
 * 4. API 기본 경로 명확화: `/api/products`로 설정하여 제품 관련 모든 엔드포인트를 포괄.
 * 5. 메서드 이름 중복 제거: 각 메서드의 기능을 명확히 나타내는 고유한 이름 사용.
 * 6. @PathVariable 어노테이션에서 name 속성 제거: 변수명과 동일한 경우 생략하여 코드 간결화.
*/

@RestController
// @RequestMapping
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // @GetMapping(value = "/get/product/by/{productId}")
    // public ResponseEntity<Product> getProductById(@PathVariable(name = "productId") Long productId){
    //     Product product = productService.getProductById(productId);
    //     return ResponseEntity.ok(product);
    // }
    @GetMapping(value = "/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // @PostMapping(value = "/create/product")
    // public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest dto){
    //     Product product = productService.create(dto);
    //     return ResponseEntity.ok(product);
    // }
    @PostMapping(value = "")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest dto){
        Product product = productService.create(dto);
        return ResponseEntity.status(201).body(product);
    }

    // @PostMapping(value = "/delete/product/{productId}")
    // public ResponseEntity<Boolean> deleteProduct(@PathVariable(name = "productId") Long productId){
    //     productService.deleteById(productId);
    //     return ResponseEntity.ok(true);
    // }
    @DeleteMapping(value = "/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteById(productId);
            
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // @PostMapping(value = "/update/product")
    // public ResponseEntity<Product> updateProduct(@RequestBody UpdateProductRequest dto){
    //     Product product = productService.update(dto);
    //     return ResponseEntity.ok(product);
    // }
    @PutMapping(value = "/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId,
                                                @RequestBody UpdateProductRequest dto) {
        dto.setId(productId);
        try {
            Product updated = productService.update(dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // @PostMapping(value = "/product/list")
    // public ResponseEntity<ProductListResponse> getProductListByCategory(@RequestBody GetProductListRequest dto){
    //     Page<Product> productList = productService.getListByCategory(dto);
    //     return ResponseEntity.ok(new ProductListResponse(productList.getContent(), productList.getTotalPages(), productList.getTotalElements(), productList.getNumber()));
    // }
    @PostMapping(value = "/list")
    public ResponseEntity<ProductListResponse> getProductListByCategory(@RequestBody GetProductListRequest dto){
        Page<Product> productList = productService.getListByCategory(dto);
        return ResponseEntity.ok(new ProductListResponse(productList.getContent(), productList.getTotalPages(), productList.getTotalElements(), productList.getNumber()));
    }

    // @GetMapping(value = "/product/category/list")
    // public ResponseEntity<List<String>> getProductListByCategory(){
    //     List<String> uniqueCategories = productService.getUniqueCategories();
    //     return ResponseEntity.ok(uniqueCategories);
    // }
    @GetMapping(value = "/categories")
    public ResponseEntity<List<String>> getCategoryList(){
        List<String> uniqueCategories = productService.getUniqueCategories();
        return ResponseEntity.ok(uniqueCategories);
    }
}