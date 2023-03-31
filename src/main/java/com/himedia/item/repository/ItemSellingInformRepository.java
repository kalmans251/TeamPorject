package com.himedia.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.item.entity.Item;
import com.himedia.item.entity.ItemSellingInform;

public interface ItemSellingInformRepository extends JpaRepository<ItemSellingInform, Long> {
	
	List<ItemSellingInform> findByItem(Item item);
	
}
