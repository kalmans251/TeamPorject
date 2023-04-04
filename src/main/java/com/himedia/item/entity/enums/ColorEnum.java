package com.himedia.item.entity.enums;

import lombok.Getter;

@Getter
public enum ColorEnum {
	
	COLOR1("Red"),
	COLOR2("Blue"),
	COLOR3("Green"),
	COLOR4("Yellow"),
	COLOR5("Gray"),
	COLOR6("Black");
	
	ColorEnum(String value){
		this.value= value;
	}
	private String value;
}
