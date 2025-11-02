package com.wjc.codetest.product.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * [CreateProductRequest 개선 요약]
 *
 * 문제  
 * - 가독성과 유지보수성이 저하됨.  
 * - 필드 초기화 로직이 동일함에도 불구하고, 인자 개수에 따라 생성자가 수동으로 중복 작성되어 있었음.  
 * - DTO의 목적인 단순 데이터 전달에 비해 코드가 불필요하게 복잡해짐.  
 *
 * 원인  
 * - Lombok의 코드 자동 생성 기능(@NoArgsConstructor, @AllArgsConstructor)을 충분히 활용하지 못함.  
 *
 * 개선안  
 * - Lombok의 @NoArgsConstructor와 @AllArgsConstructor를 적용하여  
 *   수동으로 작성된 생성자를 제거하고 코드 중복을 해소함.  
 * - DTO 본연의 역할인 단순 데이터 전달에 집중해 코드 간결성과 가독성을 높임.  
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    private String category;
    private String name;

    // public CreateProductRequest(String category) {
    //     this.category = category;
    // }

    // public CreateProductRequest(String category, String name) {
    //     this.category = category;
    //     this.name = name;
    // }
}

