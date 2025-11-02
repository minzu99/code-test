package com.wjc.codetest.product.service;

import com.wjc.codetest.product.model.request.CreateProductRequest;
import com.wjc.codetest.product.model.request.GetProductListRequest;
import com.wjc.codetest.product.model.domain.Product;
import com.wjc.codetest.product.model.request.UpdateProductRequest;
import com.wjc.codetest.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * [ProductService 개선 요약]
 *
 * 문제  
 * 1. update 메서드에서 도메인 캡슐화가 깨짐.  
 * 2. 페이지 인덱스(1-base/0-base) 불일치 가능.  
 * 3. deleteById에서 불필요한 SELECT 발생 가능성.  
 *
 * 원인  
 * - Service 계층이 엔티티 필드를 직접 수정(setter)하며 도메인 규칙이 노출됨.  
 * - 클라이언트와 JPA 간 인덱스 기준 차이에 대한 보정 로직 부재.  
 * - deleteById 시 선 조회 후 삭제 로직으로 불필요한 쿼리 발생 가능.
 *
 * 개선안  
 * 1. 엔티티 변경은 set 대신 의도 있는 메서드(update, rename 등)로 처리.  
 * 2. page 파라미터는 1-base 입력 → 내부 0-base 변환으로 일관성 유지.  
 * 3. deleteById는 선 조회 없이 repository.deleteById(id) 사용.  
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(CreateProductRequest dto) {
        Product product = new Product(dto.getCategory(), dto.getName());
        return productRepository.save(product);
    }

    public Product getProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("product not found");
        }
        return productOptional.get();
    }

    // public Product update(UpdateProductRequest dto) {
    //     Product product = getProductById(dto.getId());
    //     product.setCategory(dto.getCategory());
    //     product.setName(dto.getName());
    //     Product updatedProduct = productRepository.save(product);
    //     return updatedProduct;
    // }
    public Product update(UpdateProductRequest dto) {
        Product product = getProductById(dto.getId()); 
        product.update(dto.getCategory(), dto.getName()); 
        Product updatedProduct = productRepository.save(product);
        return updatedProduct;
    }

    // public void deleteById(Long productId) {
    //     Product product = getProductById(productId);
    //     productRepository.delete(product);
    // }
    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }

    // public Page<Product> getListByCategory(GetProductListRequest dto) {
    //     PageRequest pageRequest = PageRequest.of(dto.getPage(), dto.getSize(), Sort.by(Sort.Direction.ASC, "category"));
    //     return productRepository.findAllByCategory(dto.getCategory(), pageRequest);
    // }
    public Page<Product> getListByCategory(GetProductListRequest dto) {
        int pageIndex = dto.getPage() > 0 ? dto.getPage() - 1 : 0; 
        PageRequest pageRequest = PageRequest.of(pageIndex, dto.getSize(), Sort.by(Sort.Direction.ASC, "category"));
        return productRepository.findAllByCategory(dto.getCategory(), pageRequest);
    }

    public List<String> getUniqueCategories() {
        return productRepository.findDistinctCategories();
    }
}