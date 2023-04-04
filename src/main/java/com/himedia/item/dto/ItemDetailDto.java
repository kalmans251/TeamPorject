package com.himedia.item.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDetailDto {
	
	private Long price;
	private String subject;
	private String category1;
	private String category2;
	private List<String> urlList;
	public ItemDetailDto(Long price,String subject, String category1 , String category2,List<String> urlList){
		this.price=price;
		this.subject=subject;
		this.category1=category1;
		this.category2=category2;
		this.urlList=urlList;
	}
	
}
