package com.himedia.cart;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
	
	private Long cartItemId;
	
	private Long itemId;
	
	private String imgUrl;
	
	private String subject;
	
	private String size;
	
	private String color;
	
	private Integer count;
	
	private Long price;
	
	
	
}
