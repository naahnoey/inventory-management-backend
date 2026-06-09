package com.example.inventory.mapper;

import com.example.inventory.domain.StockDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StockMapper {

    // 현재 재고 조회 (물품별)
    StockDto selectStockByItemId(Long itemId);

    // 전체 재고 현황 목록 (대시보드용)
    List<StockDto> selectStockList();

    // 재고 수량 증가 (입고)
    int increaseStock(StockDto dto);

    // 재고 수량 감소 (출고)
    int decreaseStock(StockDto dto);

    // 입출고 이력 등록
    int insertStockHistory(StockDto dto);

    // 입출고 이력 조회 (물품별)
    List<StockDto> selectHistoryByItemId(Long itemId);

}