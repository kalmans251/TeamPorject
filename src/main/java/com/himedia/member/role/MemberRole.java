package com.himedia.member.role;

import lombok.Getter;

@Getter
public enum MemberRole {
	
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER"),
	MANAGER("ROLE_MANAGER");

	private final String value;
	
	MemberRole(String value){
		this.value=value;
	}

    public String getRole() {
        return "ROLE_" + value;
    }
	
}
