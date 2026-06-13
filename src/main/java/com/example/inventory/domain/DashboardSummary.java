package com.example.inventory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardSummary {

    private int totalItemCount;
    private int totalStockQty;
    private int lowStockCount;
    private int categoryCount;

}