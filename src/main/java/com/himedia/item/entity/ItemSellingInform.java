package com.himedia.item.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ItemSellingInform {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Item item;		//아이템 (판매등록 아이템)
	
	private int sellCount;  //상품의 수량
	
	@ManyToOne
	private Size size;		//사이즈
	
	@ManyToOne
	private Color color;	//수량
	

	
}
