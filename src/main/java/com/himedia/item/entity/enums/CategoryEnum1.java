package com.himedia.item.entity.enums;

import lombok.Getter;

@Getter
public enum CategoryEnum1 {
	TOP("TOP"),
	BOTTOM("BOTTOM"),
	OUTER("OUTER"),
	HAT("HAT");
	
	CategoryEnum1(String value){
		this.value= value;
	}
	private String value;
}
