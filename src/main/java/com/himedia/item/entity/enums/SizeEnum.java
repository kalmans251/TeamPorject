package com.himedia.item.entity.enums;

public enum SizeEnum {
	S(90),
	M(95),
	L(100),
	XL(105);
	
	
	SizeEnum(Integer value){
		this.value= value;
	}
	Integer value;
}

// SizeEnum.RED ==> 기본생성자 value에 30 입력, SizeEnum(30)
// SizeEnum.RED.value == 30
// SIzeEnum.BLUE.value == 31 t y