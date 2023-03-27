package com.himedia.item.entity;

import com.himedia.item.entity.enums.ColorEnum;
import com.himedia.item.entity.enums.SizeEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ItemSellingInform {
	
	@Id
	private Long id;
	
	@ManyToOne
	private Item item;		//아이템 (판매등록 아이템)
	
	private int sellCount;  //등록된 수량
	
	@Enumerated(EnumType.STRING)
	private SizeEnum size;		//사이즈
	
	@Enumerated(EnumType.STRING)
	private ColorEnum color;	//색상
	
	
}
