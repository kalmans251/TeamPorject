package com.himedia.member.role;

import lombok.Getter;

@Getter
public enum MemberRole {
	
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	MemberRole(String value){
		this.value=value;
	}
	private String value;

}
