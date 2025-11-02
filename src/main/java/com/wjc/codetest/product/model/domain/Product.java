package com.wjc.codetest.product.model.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
// import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * [Product 엔티티 개선 요약]
 *
 * 문제
 * 1. @Setter 노출로 필드 변경 규칙이 외부에 퍼짐.
 * 2. JPA 설정: ID 전략이 데이터베이스에 종속적일 수 있음.
 * 3. 불필요한 매핑 어노테이션
 * 4. 생성자 접근 제어 미흡.
 *
 * 원인
 * - Lombok @Setter 사용과 도메인 내 변경 메서드 부재로 객체 캡슐화가 파괴됨.
 * - GenerationType.AUTO 사용으로 인해 DB에 따라 키 생성 전략이 달라져 이식성이 저하됨.
 * - JPA 기본 네이밍 규칙 (필드명과 컬럼명 일치 시 생략)을 활용하지 못함.
 * - JPA 요구사항 충족 및 무분별한 객체 생성 차단을 위한 protected 접근 제어자 미설정.
 *
 * 개선안
 * 1. 캡슐화 강화: @Setter 제거, 의도 있는 변경 메서드를 엔티티 내부에 제공하여 도메인 객체의 자율성을 확보.
 * 2. ID 전략 명확화 : 불분명한 AUTO 대신, 자동 증가 전략인 IDENTITY 사용으로 DB 종속성 최소화.
 * 3. 매핑 정리:
 *    - 기본 네이밍이면 @Column 생략
 * 4. @NoArgsConstructor 접근 제어: 
 *    - @NoArgsConstructor(access = AccessLevel.PROTECTED)를 사용하여 외부에서의 무분별한 객체 생성을 차단.
 */


@Entity
@Getter
// @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @Column(name = "product_id")
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(name = "category")
    private String category;

    // @Column(name = "name")
    private String name;

    // protected Product() {
    // }

    public Product(String category, String name) {
        this.category = category;
        this.name = name;
    }

    // public String getCategory() {
    //     return category;
    // }

    // public String getName() {
    //     return name;
    // }

    public void update(String newCategory, String newName) {
        this.category = newCategory;
        this.name = newName;
    }
}
