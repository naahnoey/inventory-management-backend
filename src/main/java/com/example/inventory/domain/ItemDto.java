package com.example.inventory.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

    private Long   itemId;
    private Long   categoryId;
    private String categoryName;   // JOIN 조회용
    private Long   locationId;
    private String locationName;   // JOIN 조회용
    private String itemName;
    private String itemCode;
    private String unit;
    private int    minQty;
    private String status;
    private String createdAt;

    // 목록 조회 검색 조건
    // 규모 커지면 분리할 것
    private String searchName;     // 물품명 검색
    private String searchCategory; // 카테고리 필터
    private String searchStatus;   // 상태 필터

}