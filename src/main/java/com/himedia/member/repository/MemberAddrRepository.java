package com.himedia.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.member.entity.Member;
import com.himedia.member.entity.MemberAddress;

public interface MemberAddrRepository extends JpaRepository<MemberAddress, Long> {
	List<MemberAddress> findByMember(Member member);
	Optional<MemberAddress> findByMemberAndMain(Member member, Integer main);
}
