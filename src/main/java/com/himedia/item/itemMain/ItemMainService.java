package com.himedia.item.itemMain;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.himedia.item.entity.Item;
import com.himedia.item.entity.enums.CategoryEnum1;
import com.himedia.item.entity.enums.CategoryEnum2;

@Service
public class ItemMainService {
	
	private final ItemMainRepository itemMainRepository;
	
	public ItemMainService(ItemMainRepository itemMainRepository) {
		this.itemMainRepository = itemMainRepository;
	}
	
	//카테고리 정렬 
	public Page<Item> findItemsByCategory(CategoryEnum1 categoryEnum1, CategoryEnum2 categoryEnum2, int page) {
		
		int size = 9;
		Pageable pageable= PageRequest.of(page-1, size);
		
		return itemMainRepository.findByCategoryEnumOrCategoryEnum2(categoryEnum1, categoryEnum2, pageable);
	}

	//등록일 순
	public List<Item> getItemListByRegDate(){
		return itemMainRepository.findAllByOrderByRegDateDesc();
	}
	
	//가격 높은 순
	public List<Item> getItemListByPriceAsc(){
		return itemMainRepository.findAllByOrderByPriceAsc();
	}
	//가격 낮은 순 
	public List<Item> getItemListByPriceDesc(){
		return itemMainRepository.findAllByOrderByPriceDesc();
	}
}

