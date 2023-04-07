package com.himedia.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.item.entity.Item;
import com.himedia.item.entity.ItemSellingInform;
import com.himedia.member.entity.Member;
import com.himedia.order.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {
	
	List<Orders> findByMember(Member member);
	Optional<Orders> findByMemberAndItemSellingInform(Member member, ItemSellingInform itemSellingInform);
	List<Orders> findByItemSellingInform(ItemSellingInform itemSellingInform);

}
