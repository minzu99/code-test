package com.wjc.codetest.product.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * [UpdateProductRequest 개선 요약]
 *
 * 문제  
 * - 가독성과 유지보수성이 저하됨.  
 * - 필드 초기화 로직이 동일함에도 불구하고, 인자 개수에 따라 생성자가 중복 작성되어 있었음.  
 * - DTO의 목적인 단순 데이터 전달에 비해 코드가 불필요하게 복잡해짐.  
 *
 * 원인  
 * - Lombok의 코드 자동 생성 기능을 충분히 활용하지 못했음.  
 * - 필드 개수별 다양한 생성자가 필요하다고 판단해 수동으로 작성했으나,  
 *   @NoArgsConstructor와 @AllArgsConstructor로 대체할 수 있었음.  
 *
 * 개선안  
 * - Lombok의 @NoArgsConstructor와 @AllArgsConstructor를 적용하여  
 *   수동으로 작성된 생성자를 제거하고 코드 중복을 해소함.  
 * - DTO의 단순한 데이터 전달 목적에 맞게 클래스를 간결하게 유지.  
 */

@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor
public class UpdateProductRequest {
    private Long id;
    private String category;
    private String name;

    // public UpdateProductRequest(Long id) {
    //     this.id = id;
    // }

    // public UpdateProductRequest(Long id, String category) {
    //     this.id = id;
    //     this.category = category;
    // }

    // public UpdateProductRequest(Long id, String category, String name) {
    //     this.id = id;
    //     this.category = category;
    //     this.name = name;
    // }
}

