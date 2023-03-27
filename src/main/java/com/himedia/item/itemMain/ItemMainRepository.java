package com.himedia.item.itemMain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.item.entity.Item;
import com.himedia.item.entity.ItemImg;
import com.himedia.item.entity.enums.CategoryEnum;
import com.himedia.item.entity.enums.CategoryEnum2;


public interface ItemMainRepository extends JpaRepository<Item, Long> {

	//카테고리 분류
	Page<Item> findByCategoryEnumOrCategoryEnum2(CategoryEnum categoryEnum, CategoryEnum2 categoryEnum2, Pageable pageable);
	
	//상품 등록일 순
	List<Item> findAllByOrderByRegDateDesc();
	 
	// 가격 낮은 순
	List<Item> findAllByOrderByPriceAsc();
	
	//가격 높은 순
	List<Item> findAllByOrderByPriceDesc();
	
	//후기 평점 순
	//List<Item> findAllByReviewRateDesc();
	
	// 기온??
	
	
	
	
	
	
}
