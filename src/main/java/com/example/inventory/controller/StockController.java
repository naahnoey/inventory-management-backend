package com.example.inventory.controller;

import com.example.inventory.domain.StockDto;
import com.example.inventory.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    // 전체 재고 현황 GET /api/stocks
    @GetMapping
    public ResponseEntity<List<StockDto>> getStockList() {
        return ResponseEntity.ok(stockService.getStockList());
    }

    // 물품별 현재 재고 GET /api/stocks/{itemId}
    @GetMapping("/{itemId}")
    public ResponseEntity<StockDto> getStock(@PathVariable Long itemId) {
        return ResponseEntity.ok(stockService.getStock(itemId));
    }

    // 입고 POST /api/stocks/{itemId}/in
    @PostMapping("/{itemId}/in")
    public ResponseEntity<Void> stockIn(@PathVariable Long itemId,
                                        @RequestBody StockDto dto) {
        dto.setItemId(itemId);
        stockService.stockIn(dto);
        return ResponseEntity.ok().build();
    }

    // 출고 POST /api/stocks/{itemId}/out
    @PostMapping("/{itemId}/out")
    public ResponseEntity<Void> stockOut(@PathVariable Long itemId,
                                         @RequestBody StockDto dto) {
        dto.setItemId(itemId);
        stockService.stockOut(dto);
        return ResponseEntity.ok().build();
    }

    // 입출고 이력 조회 GET /api/stocks/{itemId}/history
    @GetMapping("/{itemId}/history")
    public ResponseEntity<List<StockDto>> getHistory(@PathVariable Long itemId) {
        return ResponseEntity.ok(stockService.getHistory(itemId));
    }

}