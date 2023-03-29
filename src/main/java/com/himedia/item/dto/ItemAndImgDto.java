package com.himedia.item.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.himedia.item.entity.Item;
import com.himedia.item.entity.ItemImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemAndImgDto {
	
	private Long id;
	
    private String subject; //상품명
	
    private String categoryName1;    // 큰분류명
    
    private String categoryName2;    // 작은분류명
    
    private Integer favorListNum; // 찜한 사람수
    
    private Long temperature;    // 온도
    
    private LocalDateTime regDate;    // 등록일
    
    private LocalDateTime modifiedDate;    // 수정일
    
    private Long price;    // 가격
    
    private List<ItemImg> itemImgList; //ItemImg 리스트
    
    private List<String> sizeList;    // 사이즈 리스트
    
    private List<String> colorList;    // 컬러 리스트
    
    private List<String> sellCountList; // 판매량 리스트
    
    
    
    static ModelMapper modelMapper = new ModelMapper();
    public ItemAndImgDto itemToItemDto(Item item) {
    	return modelMapper.map(item, ItemAndImgDto.class);
    }
    public Item toItem() {
    	return modelMapper.map(this, Item.class);
    }
    
   
    
   
}