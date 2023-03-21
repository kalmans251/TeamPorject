package com.himedia.item.itemDetail;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.item.entity.ItemImg;

public interface ItemDetailRepository extends JpaRepository<ItemImg, Integer> {

}
