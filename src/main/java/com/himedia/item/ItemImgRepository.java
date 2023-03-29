package com.himedia.item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.item.entity.ItemImg;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
	ItemImg findAllByItemAndRepImgYn(Long itemId,String repImgYn);
}
