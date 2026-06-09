package com.example.inventory.service;

import com.example.inventory.domain.StockDto;
import com.example.inventory.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockMapper stockMapper;

    // 현재 재고 조회
    public StockDto getStock(Long itemId) {
        StockDto stock = stockMapper.selectStockByItemId(itemId);
        if (stock == null) {
            throw new IllegalArgumentException("존재하지 않는 물품입니다. itemId=" + itemId);
        }
        return stock;
    }

    // 전체 재고 현황
    public List<StockDto> getStockList() {
        return stockMapper.selectStockList();
    }

    // 입고
    @Transactional
    public void stockIn(StockDto dto) {
        dto.setHistoryType("IN");

        // 1. 재고 증가
        stockMapper.increaseStock(dto);

        // 2. 이력 기록
        stockMapper.insertStockHistory(dto);
    }

    // 출고
    @Transactional
    public void stockOut(StockDto dto) {
        // 1. 현재 재고 확인
        StockDto current = getStock(dto.getItemId());

        // 2. 재고 부족 체크
        if (current.getCurrentQty() < dto.getQty()) {
            throw new IllegalStateException(
                    "재고가 부족합니다. 현재 재고: " + current.getCurrentQty()
                            + ", 출고 요청: " + dto.getQty()
            );
        }

        dto.setHistoryType("OUT");

        // 3. 재고 감소
        stockMapper.decreaseStock(dto);

        // 4. 이력 기록
        stockMapper.insertStockHistory(dto);
    }

    // 입출고 이력 조회
    public List<StockDto> getHistory(Long itemId) {
        return stockMapper.selectHistoryByItemId(itemId);
    }

}