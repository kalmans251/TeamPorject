package com.himedia.item.itemMain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemOutputListAjaxDto {
	
	private Long itemId;
	
	private String url;
	
	private String subject;
	
	private Long price;
	
	private Integer favorListNum;
	
	private Boolean isFavor;
	
	private Integer totalPage;
	
	public ItemOutputListAjaxDto(Long itemId,String url,String subject , Long price,Integer favorListNum,Integer totalPage) {
		this.itemId=itemId;
		this.url=url;
		this.subject=subject;
		this.price=price;
		this.favorListNum=favorListNum;
		this.totalPage=totalPage;
	}
	
}
