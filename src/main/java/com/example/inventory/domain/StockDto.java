package com.example.inventory.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDto {

    // STOCK
    private Long   stockId;
    private Long   itemId;
    private String itemName;   // JOIN 조회용
    private String itemCode;   // JOIN 조회용
    private int    currentQty;
    private String updatedAt;

    // STOCK_HISTORY
    private Long   historyId;
    private String historyType;  // IN or OUT
    private int    qty;
    private String reason;
    private String createdBy;
    private String createdAt;

    // 재고 부족 경고 여부 (minQty 비교용)
    private int    minQty;
    private boolean lowStock;   // currentQty < minQty 이면 true

}