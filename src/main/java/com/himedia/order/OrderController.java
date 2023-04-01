package com.himedia.order;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.himedia.item.entity.Item;
import com.himedia.item.entity.ItemSellingInform;
import com.himedia.item.repository.ItemSellingInformRepository;
import com.himedia.member.repository.MemberRepository;
import com.himedia.order.entity.Orders;
import com.himedia.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	
	private final ItemSellingInformRepository itemSellingInformRepository;
	private final MemberRepository memberRepository;
	private final OrderRepository orderRepository;
	@PostMapping("/save")
	@ResponseBody
	public String orderInformSave(@RequestParam String imp_uid,@RequestParam String merchant_uid, Principal principal, @RequestParam Long id,@RequestParam Integer buyCount) {
		ItemSellingInform itsi = this.itemSellingInformRepository.findById(id).get();
		Item item = itsi.getItem();
		
		Orders orders = new Orders();
		orders.setItemSellingInform(itsi);
		orders.setMerchant_uid(merchant_uid);
		orders.setImp_uid(imp_uid);
		orders.setRegDate(LocalDateTime.now());
		orders.setBuyCount(buyCount);
		orders.setMember(this.memberRepository.findByToken(principal.getName()).get());
		this.orderRepository.save(orders);
		System.out.println(id);
		System.out.println(buyCount);
		System.out.println(imp_uid);
		System.out.println(merchant_uid);
		
		
		
		
		return "결제 완료!";
	}
	
}
