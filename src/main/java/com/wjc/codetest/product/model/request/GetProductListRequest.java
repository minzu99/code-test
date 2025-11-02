package com.wjc.codetest.product.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * [GetProductListRequest 개선 요약]
 *
 * 문제  
 * - 코드의 간결성이 떨어지고 Lombok 기능을 충분히 활용하지 못함.  
 *
 * 원인  
 * - 기본 생성자(NoArgsConstructor)를 수동으로 작성하거나 아예 추가하지 않아 코드가 불완전했음.  
 *
 * 개선안  
 * - Lombok의 @NoArgsConstructor를 적용하여 인자 없는 기본 생성자를 자동으로 생성하도록 수정.  
 * - 불필요한 수동 코드 작성 없이 DTO의 간결성과 일관성을 확보함.  
 */

@Getter
@Setter
@NoArgsConstructor
public class GetProductListRequest {
    private String category;
    private int page;
    private int size;
}