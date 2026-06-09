package com.example.inventory.mapper;

import com.example.inventory.domain.DashboardDto;
import com.example.inventory.domain.StockDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DashboardMapper {

    // 요약 카드 4개
    int selectTotalItemCount();
    int selectTotalStockQty();
    int selectLowStockCount();
    int selectCategoryCount();

    // 카테고리별 재고
    List<DashboardDto.CategoryStockDto> selectCategoryStocks();

    // 재고 부족 물품 목록
    List<StockDto> selectLowStockItems();

    // 최근 입출고 이력 10건
    List<StockDto> selectRecentHistories();

}