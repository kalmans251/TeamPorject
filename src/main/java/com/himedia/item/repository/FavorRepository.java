package com.himedia.item.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.item.entity.Favor;
import com.himedia.item.entity.Item;
import com.himedia.member.entity.Member;

public interface FavorRepository extends JpaRepository<Favor, Long> {
	
	Optional<Favor> findByMemberAndItem(Member member, Item item);

}
