 package com.himedia.cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
	Cart findByMember(Long memberIdx);

}
