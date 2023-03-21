package com.himedia.member.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.himedia.member.entity.Member;
import com.himedia.member.repository.MemberRepository;
import com.himedia.member.role.MemberRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public void memberInsert(String username,String password,String nickName, Long phoneNum, MemberRole memberRole ,String addr) {
		
		Member member = new Member();
		
		member.setUsername(username);
		member.setPassword(this.passwordEncoder.encode(password));
		member.setPhoneNum(phoneNum);
		member.setMemberRole(memberRole.USER);
		member.setNickName(nickName);
		member.setAddr(addr);
		this.memberRepository.save(member);
	}
	
	public Member getMember(String username) {
		Optional<Member> member =this.memberRepository.findByUsername(username);
			return member.get();
	}
	
	
	
}
