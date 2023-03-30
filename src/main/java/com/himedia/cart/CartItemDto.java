package com.himedia.cart;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
	
	private Long id;
	
	private String url;
	
	private String subject;
	
	private String size;
	
	private String color;
	
	private Integer count;
	
	private Long price;
}
