package com.example.inventory.mapper;

import com.example.inventory.domain.DashboardDto;
import com.example.inventory.domain.DashboardSummary;
import com.nexacro.uiadapter.jakarta.core.data.NexacroResult;

import java.util.List;

public class DashboardNexacroMapper {

    public static NexacroResult toResult(DashboardDto dashboardDto) {
        NexacroResult result = new NexacroResult();

        // 요약 카드
        DashboardSummary summary = new DashboardSummary(
                dashboardDto.getTotalItemCount(),
                dashboardDto.getTotalStockQty(),
                dashboardDto.getLowStockCount(),
                dashboardDto.getCategoryCount()
        );

        result.addDataSet("dsSummary", List.of(summary));

        // 카테고리별 재고
        result.addDataSet(
                "dsCategoryStock",
                dashboardDto.getCategoryStocks()
        );

        // 재고 부족
        result.addDataSet(
                "dsLowStock",
                dashboardDto.getLowStockItems()
        );

        // 최근 이력
        result.addDataSet(
                "dsHistory",
                dashboardDto.getRecentHistories()
        );

        return result;
    }

}
