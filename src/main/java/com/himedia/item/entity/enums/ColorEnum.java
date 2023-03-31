package com.himedia.item.entity.enums;

import lombok.Getter;

@Getter
public enum ColorEnum {
	COLOR1("white"),
	COLOR2("black"),
	COLOR3("ibory"),
	COLOR4("morlar"),
	COLOR5("Red"),
	COLOR6("Blue");
	
	ColorEnum(String value){
		this.value= value;
	}
	private String value;
}
