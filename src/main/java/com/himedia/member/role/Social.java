package com.himedia.member.role;

public enum Social {

	NAVER("NAVER"),
	GOOGLE("GOOGLE"),
	LOCAL("LOCAL");
	
	Social(String value){
		this.value=value;
	}
	private String value;
}
