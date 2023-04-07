
package com.himedia.cart;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.himedia.item.entity.Item;
import com.himedia.item.entity.ItemImg;
import com.himedia.item.entity.ItemSellingInform;
import com.himedia.item.entity.enums.SizeEnum;
import com.himedia.item.repository.ItemImgRepository;
import com.himedia.item.repository.ItemSellingInformRepository;
import com.himedia.member.repository.MemberAddrRepository;
import com.himedia.member.repository.MemberRepository;
import com.himedia.order.dto.OrderDto;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {
	
	private final CartItemRepository cartItemRepository;
	private final ItemSellingInformRepository itemSellingInformRepository;
	private final MemberRepository memberRepository;
	private final ItemImgRepository itemImgRepository;
	private final MemberAddrRepository memberAddrRepository;
	
	//상품디테일 페이지에서 장바구니 추가 클릭 시
	@PostMapping("/cartAddAjax")
	@ResponseBody
	public String cartAddAjax(@RequestParam String id,@RequestParam Integer count,Principal principal) {
		
		CartItem cartItem = new CartItem();
		cartItem.setItemSellingInform(this.itemSellingInformRepository.findById(Long.parseLong(id)).get());
		cartItem.setMember(this.memberRepository.findByToken(principal.getName()).get());
		cartItem.setCount(count);
		cartItem.setRegDate(LocalDateTime.now());
		try {
			this.cartItemRepository.save(cartItem);
			return "장바구니 추가 성공";
		}catch(Exception e) {
			e.printStackTrace();
			return "장바구니 추가 실패.";
		}
		
	}
	
	
	@GetMapping("/deletecartAjax")
	@ResponseBody
	public String cartDeleteAjax(@RequestParam Long id) {
		try {
			this.cartItemRepository.delete(this.cartItemRepository.findById(id).get());
			return "삭제완료";
		}catch(Exception e) {
			e.printStackTrace();
			return "삭제실패";
		}
	}
	
	@GetMapping("/isCart")
	@ResponseBody
	public String carUpdateAjax(@RequestParam Long itemSellingInformId, Principal principal) {
		
		Optional<CartItem> _cartItem = this.cartItemRepository.findByMemberAndItemSellingInform(this.memberRepository.findByToken(principal.getName()).get(), this.itemSellingInformRepository.findById(itemSellingInformId).get());
		if(_cartItem.isEmpty()) {
			return "0";
		}else {
			return "1";
		}
	}
	
	@PostMapping("/cartCountAddAjax")
	@ResponseBody
	public String cartCountAddAjax(@RequestParam Long id,@RequestParam Integer count, Principal principal) {
		
		Optional<CartItem> _CartItem = this.cartItemRepository.findByMemberAndItemSellingInform(this.memberRepository.findByToken(principal.getName()).get(), this.itemSellingInformRepository.findById(id).get());
		_CartItem.get().addCount(count);
		
		try {
			this.cartItemRepository.save(_CartItem.get());
			return count+"개 추가 완료";
		}catch(Exception e) {
			e.printStackTrace();
			return "카트 아이템 갱신 실패";
		}
	}
	
	
	@PostMapping("/checkcount")
	@ResponseBody
	public String checkCount(@RequestParam Long id,@RequestParam Integer buyCount) {
		
		ItemSellingInform itsi = itemSellingInformRepository.findById(id).get();
		if(itsi.getSellCount()<buyCount) {
			return "재고없음";
		}else{
			itsi.setSellCount(itsi.getSellCount()-buyCount);
			this.itemSellingInformRepository.save(itsi);
			return "구매진행";
		}
		
	}
	
	@PostMapping("/rollbackcount")
	@ResponseBody
	public String rollbackCount(@RequestParam Long id,@RequestParam Integer buyCount) {
		ItemSellingInform itsi = itemSellingInformRepository.findById(id).get();
		itsi.setSellCount(itsi.getSellCount()+buyCount);
		this.itemSellingInformRepository.save(itsi);
		return "주문취소";
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/cart")
	public String openCartList(Model model,Principal principal) {
	
		List<CartItem> cartItemList = this.cartItemRepository.findAllByMember(this.memberRepository.findByToken(principal.getName()).get());
		List<CartItemDto> cartItemDtoList = new ArrayList<CartItemDto>();
		for(int i=0 ; i < cartItemList.size() ; i++) {
			CartItemDto cartItemDto = new CartItemDto();
			ItemImg itemImg = this.itemImgRepository.findByItemAndRepimgYn(cartItemList.get(i).getItemSellingInform().getItem(),"Y");
			CartItem cartItem = cartItemList.get(i);
			cartItemDto.setImgUrl(itemImg.getUrl());
			cartItemDto.setPrice(itemImg.getItem().getPrice());
			cartItemDto.setItemId(itemImg.getItem().getId());
			cartItemDto.setCartItemId(cartItem.getId());
			cartItemDto.setCount(cartItem.getCount());
			cartItemDto.setColor(cartItem.getItemSellingInform().getColor().getName());
			cartItemDto.setSize(cartItem.getItemSellingInform().getSize().getName());
			cartItemDto.setSubject(itemImg.getItem().getSubject());
			cartItemDtoList.add(cartItemDto);
		}
		
		model.addAttribute("cartItemDtoList", cartItemDtoList);
		return "cartList"; 
	}
	@GetMapping("/cart1")
	public String openCartList(Model model) {
		return "cartList";
	}
	
	@GetMapping("/orderform/{id}")
	public String orderForm(Model model,@PathVariable Long id,@RequestParam(name = "counts") Integer count,@RequestParam(name = "id") Integer isiId,Principal principal) {
		ItemSellingInform itsi = this.itemSellingInformRepository.findById(id).get();
		Item item = itsi.getItem();
		ItemImg itemImg = this.itemImgRepository.findByItemAndRepimgYn(itsi.getItem(), "Y");
		
		OrderDto orderDto = null;
		if(this.memberAddrRepository.findByMainAndMember(1, this.memberRepository.findByToken(principal.getName()).get()).isEmpty()){
			orderDto = new OrderDto(itemImg.getUrl(),item.getSubject(),item.getPrice(),itsi.getSize().getName(),itsi.getColor().getName(),count,
					this.memberRepository.findByToken(principal.getName()).get(),
					null);
		}else {
			orderDto = new OrderDto(itemImg.getUrl(),item.getSubject(),item.getPrice(),itsi.getSize().getName(),itsi.getColor().getName(),count,
					this.memberRepository.findByToken(principal.getName()).get(),
					this.memberAddrRepository.findByMainAndMember(1, this.memberRepository.findByToken(principal.getName()).get()).get(0));
		}
		
		model.addAttribute("orderDto", orderDto);
		model.addAttribute("isiId",isiId);
		return "orderform";
	}
	
	
	
	
}
