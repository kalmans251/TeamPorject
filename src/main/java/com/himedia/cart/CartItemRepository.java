package com.himedia.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.item.entity.ItemSellingInform;
import com.himedia.member.entity.Member;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	
	//CartItem findByCartItemAndItemSellingInform(Long cartId, Long itemSellingInformId);
	
	List<CartItem> findAllByMember(Member member);
	Optional<CartItem> findByMemberAndItemSellingInform(Member member, ItemSellingInform itemSellingInform);
	
	
}
