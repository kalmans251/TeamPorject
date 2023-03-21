package com.himedia.member.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.himedia.member.dto.MemberCreateForm;
import com.himedia.member.entity.Member;
import com.himedia.member.role.MemberRole;
import com.himedia.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;
	
	@GetMapping("/login")
	private String memberlogin() {
		
		return "login";
	}
	
	@GetMapping("/create")
	private String memberSave(MemberCreateForm memberCreateForm) {
		
		return "member_create";
	}
	
	@PostMapping("/create")
	private String memberinsert(@Valid MemberCreateForm memberCreateForm,BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			
			return "member_create";
		}
		if(!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect","두개의 패스워드가 일치하지 않습니다");
			return "member_create";
		}
		try {
			this.memberService.memberInsert(memberCreateForm.getUsername(), memberCreateForm.getUsername(), memberCreateForm.getPassword1() ,memberCreateForm.getPhoneNum(), MemberRole.USER, memberCreateForm.getAddr());
		}catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("singupFail","이미 등록된 사용자 입니다.");
			return "member_create";
		}catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("singupFailed",e.getMessage());
			return "member_create";
		}
		
		return "redirect:/member/login";
	}
	@PostMapping("/ajaxtest")
	@ResponseBody
	public Integer ajax(@RequestParam("username")String username) {
		System.out.println(username);

		int a;
		if(username.equals("")) {
			a=2;
			return a;
		}else{
			try {
				Member member = this.memberService.getMember(username);
				a=1;
				return a;
			}catch(Exception e) {
				a=0;
				return a;
			}
		}
	}
	
	
}
