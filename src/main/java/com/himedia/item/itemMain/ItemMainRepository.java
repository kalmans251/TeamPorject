package com.himedia.item.itemMain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.item.entity.Item;
import com.himedia.item.entity.enums.CategoryEnum;
import com.himedia.item.entity.enums.CategoryEnum2;


public interface ItemMainRepository extends JpaRepository<Item, Long> {

	//카테고리(Big Category : TOP/ BOTTOM/OUTER/HAT) 분류
	List<Item> findByCategoryEnum(CategoryEnum categoryEnum);
	
	//카테고리2(Small Category) 분류
	List<Item> findByCateogryEnum2(CategoryEnum2 categoryEnum2);
	
	//상품 등록일 순
	List<Item> findAllByOrderByRegDateDesc();
	
	// 가격 낮은 순
	List<Item> findAllByOrderByPriceAsc();
	
	//가격 높은 순
	List<Item> findAllByOrderByPriceDesc();
	
	//후기 평점 순
	List<Item> findAllByReviewRateDesc();
	
	// 기온??
	
	
	
	
	
	
}
