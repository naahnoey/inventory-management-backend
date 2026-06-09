package com.example.inventory.controller;

import com.example.inventory.domain.ItemDto;
import com.example.inventory.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    // 목록 조회
    @GetMapping
    public ResponseEntity<List<ItemDto>> getItemList(ItemDto dto) {
        return ResponseEntity.ok(itemService.getItemList(dto));
    }

    // 단건 조회
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.getItemById(itemId));
    }

    // 등록
    @PostMapping
    public ResponseEntity<Void> createItem(@RequestBody ItemDto dto) {
        itemService.createItem(dto);
        return ResponseEntity.ok().build();
    }

    // 수정
    @PutMapping("/{itemId}")
    public ResponseEntity<Void> updateItem(@PathVariable Long itemId,
                                           @RequestBody ItemDto dto) {
        dto.setItemId(itemId);
        itemService.updateItem(dto);
        return ResponseEntity.ok().build();
    }

    // 삭제
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }

}