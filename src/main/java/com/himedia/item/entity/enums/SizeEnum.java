package com.himedia.item.entity.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum SizeEnum {
	
	SIZE1("S"),
	SIZE2("M"),
	SIZE3("L"),
	SIZE4("XL");
	
	SizeEnum(String value){
		this.value= value;
	}
	private String value;
}
