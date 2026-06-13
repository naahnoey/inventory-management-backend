package com.example.inventory.controller;

import com.example.inventory.domain.DashboardDto;
import com.example.inventory.domain.DashboardSummary;
import com.example.inventory.service.DashboardService;
import com.nexacro.uiadapter.jakarta.core.data.NexacroResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/nexacro")
@RequiredArgsConstructor
public class NexacroDashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public NexacroResult getDashboard() {

        DashboardDto dashboard = dashboardService.getDashboard();

        NexacroResult result = new NexacroResult();

        // 요약 카드
        DashboardSummary summary = new DashboardSummary(
                dashboard.getTotalItemCount(),
                dashboard.getTotalStockQty(),
                dashboard.getLowStockCount(),
                dashboard.getCategoryCount()
        );

        result.addDataSet("dsSummary", List.of(summary));

        // 카테고리별 재고
        result.addDataSet(
                "dsCategoryStock",
                dashboard.getCategoryStocks()
        );

        // 재고 부족
        result.addDataSet(
                "dsLowStock",
                dashboard.getLowStockItems()
        );

        // 최근 이력
        result.addDataSet(
                "dsHistory",
                dashboard.getRecentHistories()
        );

        return result;
    }
}