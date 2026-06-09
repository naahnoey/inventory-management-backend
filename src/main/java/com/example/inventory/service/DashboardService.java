package com.example.inventory.service;

import com.example.inventory.domain.DashboardDto;
import com.example.inventory.mapper.DashboardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardMapper dashboardMapper;

    public DashboardDto getDashboard() {
        return DashboardDto.builder()
                .totalItemCount  (dashboardMapper.selectTotalItemCount())
                .totalStockQty   (dashboardMapper.selectTotalStockQty())
                .lowStockCount   (dashboardMapper.selectLowStockCount())
                .categoryCount   (dashboardMapper.selectCategoryCount())
                .categoryStocks  (dashboardMapper.selectCategoryStocks())
                .lowStockItems   (dashboardMapper.selectLowStockItems())
                .recentHistories (dashboardMapper.selectRecentHistories())
                .build();
    }

}