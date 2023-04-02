package com.himedia.item.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavorDto {
	
	private Long id;	//itemId
	
	private String imgUrl;
	
	private String subject;
	
	private Long price;
	
	private Long favorId;
	
	public FavorDto() {}
	
	public FavorDto(Long id, String imgUrl, String subject, Long price, Long favorId) {
		this.id=id;
		this.imgUrl=imgUrl;
		this.subject=subject;
		this.price=price;
		this.favorId=favorId;
	}
	

}
