package com.himedia.member.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.himedia.member.entity.Member;
import com.himedia.member.entity.MemberAddress;
import com.himedia.member.repository.MemberAddrRepository;
import com.himedia.member.repository.MemberRepository;
import com.himedia.member.role.MemberRole;
import com.himedia.member.role.Social;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final MemberAddrRepository memberAddrRepository;
	
	public void memberInsert(String username,String password,String nickName, String phoneNum, MemberRole memberRole) {
		
		Member member = new Member();
		
		member.setUsername(username);
		member.setPassword(this.passwordEncoder.encode(password));
		member.setPhoneNum(phoneNum);
		member.setMemberRole(memberRole.USER);
		member.setSocial(Social.LOCAL);
		member.setNickName(nickName);
		this.memberRepository.save(member);
	}
	
	public Member getMember(String username) {
		Optional<Member> member =this.memberRepository.findByUsername(username);
			return member.get();
	}
	
	public void addrInsert(Member member,Integer addr1, String addr,String reference) {
		MemberAddress memberAddr = new MemberAddress();
		memberAddr.setAddrnum(addr1);
		memberAddr.setAddr(addr);
		memberAddr.setMember(member);
		memberAddr.setCreateDate(LocalDateTime.now());
		memberAddr.setReference(reference);
		MemberAddress memberAddress = this.memberAddrRepository.findByMemberAndMain(member,1);
		if(Objects.nonNull(memberAddress)) {
			memberAddr.setMain(0);
		}else {
			memberAddr.setMain(1);
		}
		this.memberAddrRepository.save(memberAddr);
	}
	public void modifypass(String username, String password) {
		Optional<Member> bmember = this.memberRepository.findByUsername(username);
		Member member = bmember.get();
		member.setPassword(this.passwordEncoder.encode(password));
		this.memberRepository.save(member);
	}
	public void modifyMemberInfo(String username, String phonenum, String nickname) {
		Optional<Member> bmember = this.memberRepository.findByUsername(username);
		Member member = bmember.get();
		member.setNickName(nickname);
		member.setPhoneNum(phonenum);
		this.memberRepository.save(member);
	}
	public void memberDelete(String username) {
		Optional<Member> bmember = this.memberRepository.findByUsername(username);
		Member member = bmember.get();
		this.memberRepository.deleteById(member.getIdx());
	}
	public void memberAddrDel(Long idx) {
		this.memberAddrRepository.deleteById(idx);
	}
	public List<MemberAddress> findMemberAddr(Member member) {
		
		return this.memberAddrRepository.findByMainAndMember(0,member);
	}
	public MemberAddress findMemberMainAddr(Member member){
		
		return this.memberAddrRepository.findByMemberAndMain(member, 1);
	}
	public void AddrChange(Member member) {
		MemberAddress memberaddr1 = this.memberAddrRepository.findByMemberAndMain(member, 1);
		memberaddr1.setMain(0);
		this.memberAddrRepository.save(memberaddr1);
	}
	public void AddrChangeMain(Long idx) {
		Optional<MemberAddress> memberAddrb2 = this.memberAddrRepository.findById(idx);
		MemberAddress memberaddr2 = memberAddrb2.get();
		memberaddr2.setMain(1);
		this.memberAddrRepository.save(memberaddr2);
	}
}
