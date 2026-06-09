package com.example.inventory.service;

import com.example.inventory.domain.ItemDto;
import com.example.inventory.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;

    // 목록 조회
    public List<ItemDto> getItemList(ItemDto dto) {
        return itemMapper.selectItemList(dto);
    }

    // 단건 조회
    public ItemDto getItemById(Long itemId) {
        ItemDto item = itemMapper.selectItemById(itemId);
        if (item == null) {
            throw new IllegalArgumentException("존재하지 않는 물품입니다. itemId=" + itemId);
        }
        return item;
    }

    // 등록
    @Transactional
    public void createItem(ItemDto dto) {
        itemMapper.insertItem(dto);
    }

    // 수정
    @Transactional
    public void updateItem(ItemDto dto) {
        getItemById(dto.getItemId()); // 존재 여부 확인
        itemMapper.updateItem(dto);
    }

    // 삭제 (논리 삭제)
    @Transactional
    public void deleteItem(Long itemId) {
        getItemById(itemId); // 존재 여부 확인
        itemMapper.deleteItem(itemId);
    }

}