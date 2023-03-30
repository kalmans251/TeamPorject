package com.himedia.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberModifyPasswordForm {
	
	@NotBlank(message="등록되어있는 password는 필수 입력사항입니다.")
	private String password1;
	
	@NotBlank(message="변경될 password는 필수 입력사항입니다.")
	private String password2;
	
	@NotBlank(message="변경될 password 확인은 필수 입력사항입니다.")
	private String password3;
}
