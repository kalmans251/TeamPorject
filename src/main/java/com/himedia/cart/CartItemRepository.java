package com.himedia.cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.member.entity.Member;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	
	//CartItem findByCartItemAndItemSellingInform(Long cartId, Long itemSellingInformId);
	
	List<CartItem> findAllByMember(Member member);
	
	
}
