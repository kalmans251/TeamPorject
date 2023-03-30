package com.himedia.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.member.entity.Member;
import com.himedia.member.entity.MemberAddress;

public interface MemberAddrRepository extends JpaRepository<MemberAddress, Long> {
	List<MemberAddress> findByMainAndMember(Integer main, Member member);
	MemberAddress findByMemberAndMain(Member member, Integer main);
}