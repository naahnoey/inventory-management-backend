package com.example.inventory.mapper;

import com.example.inventory.domain.ItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {

    // 목록 조회 (다중 조건 검색)
    List<ItemDto> selectItemList(ItemDto dto);

    // 단건 조회
    ItemDto selectItemById(Long itemId);

    // 등록
    int insertItem(ItemDto dto);

    // 수정
    int updateItem(ItemDto dto);

    // 삭제 (논리 삭제 — STATUS = 'INACTIVE')
    int deleteItem(Long itemId);

}