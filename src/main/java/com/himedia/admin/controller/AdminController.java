package com.himedia.admin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String memberRoleChange(Model model) {
		List<Member> memberGet = this.memberService.allmember();
		model.addAttribute("member", memberGet);
		return "admin_memberrole";
	}
	@GetMapping(value="/member/change/manager/{idx}")
	public String ChangeToManager(@PathVariable Long idx) {
		Optional<Member> OPmember = this.memberRepository.findById(idx);
		Member member = OPmember.get();
		member.setMemberRole(MemberRole.MANAGER);
		this.memberRepository.save(member);
		return "redirect:/admin/member/change/role";
	}
	@GetMapping(value="/member/change/user/{idx}")
	public String ChangeToUser(@PathVariable Long idx) {
		Optional<Member> OPmember = this.memberRepository.findById(idx);
		Member member = OPmember.get();
		member.setMemberRole(MemberRole.USER);
		this.memberRepository.save(member);
		return "redirect:/admin/member/change/role";
	}
}
