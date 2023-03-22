package com.himedia.member.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateForm {

	@NotBlank(message="사용하실 이메일은 필수 입력입니다.")
	private String username;
	
	@NotBlank(message="사용하실 비밀번호는 필수 입력입니다.")
	public String password1;
	
	@NotBlank(message="비밀번호 확인은 필수 입력입니다.")
	public String password2;
	
	@NotBlank(message="사용하실 닉네임은 필수 입력입니다.")
	public String nickName;
	
	@NotNull(message="사용하실 전화번호는 필수 입력입니다.")
	public String phoneNum;
	
	@NotBlank(message="주소는 필수 입력입니다.")
	public String addr1;
	@NotBlank(message="주소는 필수 입력입니다.")
	public String addr2;
	@NotBlank(message="주소는 필수 입력입니다.")
	public String addr3;
	
	public String reference;
	
	
}
