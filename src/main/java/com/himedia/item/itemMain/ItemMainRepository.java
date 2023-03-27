package com.himedia.item.itemMain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.item.entity.Item;
import com.himedia.item.entity.ItemImg;
import com.himedia.item.entity.enums.CategoryEnum1;
import com.himedia.item.entity.enums.CategoryEnum2;

public interface ItemMainRepository extends JpaRepository<Item, Integer> {
 
	Page<Item> findAll(Specification<Item> specification,Pageable pageable);

}
