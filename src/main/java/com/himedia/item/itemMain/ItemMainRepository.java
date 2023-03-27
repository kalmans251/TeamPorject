package com.himedia.item.itemMain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.item.entity.Item;
import com.himedia.item.entity.ItemImg;
import com.himedia.item.entity.enums.CategoryEnum1;
import com.himedia.item.entity.enums.CategoryEnum2;

public interface ItemMainRepository extends JpaRepository<Item, Integer> {
 
	//카테고리별 분류
	//Page<Item> findByCategoryEnum1OrCategoryEnum2(String categoryEnum1,String categoryEnum2,Pageable pageable);
	
	//등록일 순 
	List<Item> findAllByOrderByRegDateDesc();
	
	//가격 높은 순 
	List<Item> findAllByOrderByPriceAsc();
	
	//가격 낮은 순 
	List<Item> findAllByOrderByPriceDesc();
	
	
	
	
}
