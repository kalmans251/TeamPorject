package com.himedia.order;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.himedia.item.entity.Item;
import com.himedia.item.entity.ItemImg;
import com.himedia.item.entity.ItemSellingInform;
import com.himedia.item.repository.ItemImgRepository;
import com.himedia.item.repository.ItemSellingInformRepository;
import com.himedia.member.entity.Member;
import com.himedia.member.entity.MemberAddress;
import com.himedia.member.repository.MemberRepository;
import com.himedia.order.dto.OrderDto;
import com.himedia.order.entity.Orders;
import com.himedia.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	
	private final ItemSellingInformRepository itemSellingInformRepository;
	private final MemberRepository memberRepository;
	private final OrderRepository orderRepository;
	private final ItemImgRepository itemImgRepository;
	
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
	
	
	@GetMapping("/orderlist")
	public String orderList(Model model, Principal principal) {
		
		List<OrderDto> orderDtoList = new ArrayList<>();
		Member member = this.memberRepository.findByToken(principal.getName()).get();
		List<Orders> orderList = this.orderRepository.findByMember(member);
		
		for(int i=0; i<orderList.size(); i++) {
			OrderDto orderDto = null;
			ItemSellingInform itemSellingInform = orderList.get(i).getItemSellingInform();
			Item item = itemSellingInform.getItem();
			ItemImg itemImg = this.itemImgRepository.findByItemAndRepimgYn(item, "Y");
			Orders orders = orderList.get(i);
			List<MemberAddress> memberAddrList = member.getMemberAddresses();
			if(memberAddrList.isEmpty()) {
				orderDto = new OrderDto(orders.getRegDate(),itemImg.getUrl(),item.getSubject(),item.getPrice(),itemSellingInform.getSize().getName(),itemSellingInform.getColor().getName(),orders.getBuyCount(),null,null);
				orderDto.setId(item.getId());
			}else {
				orderDto = new OrderDto(orders.getRegDate(),itemImg.getUrl(),item.getSubject(),item.getPrice(),itemSellingInform.getSize().getName(),itemSellingInform.getColor().getName(),orders.getBuyCount(),null,memberAddrList.get(i));
				orderDto.setId(item.getId());
			}
			orderDtoList.add(orderDto);
		}
		
		model.addAttribute("orderDtoList", orderDtoList);
		return "orderList"; 
	}
	
	
	
	
	
	
	
	
}



