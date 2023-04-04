package com.himedia.admin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.himedia.member.entity.Member;
import com.himedia.member.repository.MemberRepository;
import com.himedia.member.role.MemberRole;
import com.himedia.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

	private final MemberService memberService;
	private final MemberRepository memberRepository;
	
	@GetMapping("/member/change/role")
	public String memberRoleChange(Model model,@RequestParam(value = "role", defaultValue="") String role) {
		List<Member> member;
		if(role.equals("")) {
			member = this.memberService.allmember();
			model.addAttribute("member", member);
		} else if(role.equals("manager")) {
			member = this.memberService.selectByRole(MemberRole.MANAGER);
			model.addAttribute("member", member);
		} else if(role.equals("user")) {
			member = this.memberService.selectByRole(MemberRole.USER);
			model.addAttribute("member", member);
		} else if(role.equals("admin")) {
			member = this.memberService.selectByRole(MemberRole.ADMIN);
			model.addAttribute("member", member);
		}
		
		return "admin_memberrole";
	}
	@GetMapping(value="/member/change/{memberRole}/{idx}")
	public String ChangeToManager(@PathVariable("idx") Long idx, @PathVariable("memberRole") String memberRole) {
		Optional<Member> OPmember = this.memberRepository.findById(idx);
		Member member = OPmember.get();
		if(memberRole.equals("manager")) {
			member.setMemberRole(MemberRole.MANAGER);
		}else if(memberRole.equals("user")) {
			member.setMemberRole(MemberRole.USER);
		}
		this.memberRepository.save(member);
		return "redirect:/admin/member/change/role";
	}
}

