package com.himedia.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberAddrForm {

	@NotBlank(message="주소는 필수 입력입니다.")
	public String addr1;
	@NotBlank(message="주소는 필수 입력입니다.")
	public String addr2;
	@NotBlank(message="주소는 필수 입력입니다.")
	public String addr3;
	
	public String reference;
	
}
