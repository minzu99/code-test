package com.wjc.codetest.product.model.response;

import com.wjc.codetest.product.model.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author : 변영우 byw1666@wjcompass.com
 * @since : 2025-10-27
 */

/**
 * [ProductListResponse 개선 요약]
 *
 * 문제  
 * - 가독성과 유지보수성이 저하됨.  
 * - 필드 초기화 로직이 동일함에도 불구하고, 모든 인자를 받는 생성자가 수동으로 작성되어 있었음.
 * 
 * 원인  
 * - Lombok의 코드 자동 생성 기능(@AllArgsConstructor)을 충분히 활용하지 못했음.  
 * - 모든 필드를 인자로 받는 생성자가 필요하다고 판단해 수동으로 작성했으나,  
 *   Lombok 어노테이션으로 쉽게 대체 가능했음.  
 *
 * 개선안  
 * - Lombok의 @AllArgsConstructor를 적용하여 모든 필드를 인자로 받는 생성자를 자동 생성하도록 변경.  
 * - 수동으로 작성된 생성자를 제거해 코드 중복을 해소하고 가독성을 향상시킴.  
 */

@Getter
@Setter
@AllArgsConstructor
public class ProductListResponse {
    private List<Product> products;
    private int totalPages;
    private long totalElements;
    private int page;

    // public ProductListResponse(List<Product> content, int totalPages, long totalElements, int number) {
    //     this.products = content;
    //     this.totalPages = totalPages;
    //     this.totalElements = totalElements;
    //     this.page = number;
    // }
}
