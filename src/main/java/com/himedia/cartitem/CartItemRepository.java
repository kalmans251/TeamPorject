package com.himedia.cartitem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	
	//CartItem findByCartItemAndItemSellingInform(Long cartId, Long itemSellingInformId);
	
	List<CartItem> findAllByMember(Long memberId);
	
	
}
