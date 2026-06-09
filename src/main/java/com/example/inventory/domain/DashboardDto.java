package com.example.inventory.domain;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDto {

    // 요약 카드
    private int totalItemCount;      // 전체 물품 수
    private int totalStockQty;       // 총 재고 수량
    private int lowStockCount;       // 재고 부족 물품 수
    private int categoryCount;       // 카테고리 수

    // 카테고리별 재고
    private List<CategoryStockDto> categoryStocks;

    // 재고 부족 목록
    private List<StockDto> lowStockItems;

    // 최근 입출고 이력
    private List<StockDto> recentHistories;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategoryStockDto {
        private String categoryName;
        private int    itemCount;     // 카테고리 내 물품 수
        private int    totalQty;      // 카테고리 내 총 재고
    }

}