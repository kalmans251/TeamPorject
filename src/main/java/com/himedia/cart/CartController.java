/**
 * 
 */
package com.himedia.cart;

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

import com.himedia.item.entity.ItemImg;
import com.himedia.item.repository.ItemImgRepository;
import com.himedia.item.repository.ItemSellingInformRepository;
import com.himedia.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author USER
 *
 */
@Controller
@RequiredArgsConstructor
public class CartController {
	
	private final CartItemRepository cartItemRepository;
	private final ItemSellingInformRepository itemSellingInformRepository;
	private final MemberRepository memberRepository;
	private final ItemImgRepository itemImgRepository;
	
	//상품디테일 페이지에서 장바구니 추가 클릭 시
	@PostMapping("/cartAddAjax")
	@ResponseBody
	public String cartAddAjax(@RequestParam String id,@RequestParam Integer count,Principal principal) {
		
		CartItem cartItem = new CartItem();
		cartItem.setItemSellingInform(this.itemSellingInformRepository.findById(Long.parseLong(id)).get());
		cartItem.setMember(this.memberRepository.findByUsername(principal.getName()).get());
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
	
	@GetMapping("/cart")
	public String openCartList(Model model,Principal principal) {
	
		List<CartItem> cartItemList = this.cartItemRepository.findAllByMember(this.memberRepository.findByUsername(principal.getName()).get().getIdx());
		List<CartItemDto> cartItemDtoList = new ArrayList<CartItemDto>();
		for(int i=0 ; i < cartItemList.size() ; i++) {
			CartItemDto cartItemDto = new CartItemDto();
			ItemImg itemImg = this.itemImgRepository.findByItemAndRepimgYn(cartItemList.get(i).getItemSellingInform().getItem().getId(),"Y");
			CartItem cartItem = cartItemList.get(i);
			cartItemDto.setUrl(itemImg.getUrl());
			cartItemDto.setPrice(itemImg.getItem().getPrice());
			cartItemDto.setId(itemImg.getItem().getId());
			cartItemDto.setCount(cartItem.getCount());
			cartItemDto.setColor(cartItem.getItemSellingInform().getColor().toString());
			cartItemDto.setSize(cartItem.getItemSellingInform().getSize().toString());
			//cartItemDto.setSubject(itemImg.getItem().getSubject());
			cartItemDtoList.add(cartItemDto);
		}
		
		model.addAttribute("cartItemDtoList", cartItemDtoList);
		return "cartList";
	}
	@GetMapping("/cart1")
	public String openCartList() {
		return "cartList";
	}
	
	
}
