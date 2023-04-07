package com.himedia.main;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.himedia.item.dto.ItemDetailDto;
import com.himedia.item.entity.Favor;
import com.himedia.item.entity.Item;
import com.himedia.item.entity.ItemImg;
import com.himedia.item.entity.ItemSellingInform;
import com.himedia.item.itemMain.ItemListingAjaxDto;
import com.himedia.item.itemMain.ItemMainService;
import com.himedia.item.itemMain.ItemOutputListAjaxDto;
import com.himedia.item.repository.FavorRepository;
import com.himedia.item.repository.ItemImgRepository;
import com.himedia.item.repository.ItemRepository;
import com.himedia.item.repository.ItemSellingInformRepository;
import com.himedia.member.entity.Member;
import com.himedia.member.repository.MemberRepository;
import com.himedia.member.service.MemberService;
import com.himedia.review.Review;
import com.himedia.review.ReviewService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final ItemMainService itemMainService;
	private final ItemRepository itemRepository;
	private final ItemImgRepository itemImgRepository;
	private final ItemSellingInformRepository itemSellingInformRepository;
	private final MemberService memberService;
	private final MemberRepository memberRepository;
	private final FavorRepository favorRepository;
	private final ReviewService reviewService;
	
	@GetMapping("/")
	public String index(HttpServletRequest request,Model model) {
		String username = request.getRemoteUser();
		if(username!=null) {
			Member member = this.memberService.getMember(username);
			model.addAttribute("member", member);
	        model.addAttribute("loggedIn", true);
		}else {
	        model.addAttribute("loggedIn", false);	
		}
		
		return "index";
	}
	
	@GetMapping("/test")
	public String test() {
		return "itemdetail";
	}
	
	@GetMapping("/item/category/{category}")
	public String category(Model model,@PathVariable String category) {
		model.addAttribute("category", category);
		return "category";
	}
	
	
	@PostMapping("/test")
	@ResponseBody
	public List<ItemOutputListAjaxDto> ajaxTest(@RequestBody ItemListingAjaxDto itemListingAjaxDto,Principal principal) {
		Page<Item> items = null;
		try {
			items = this.itemMainService.findItemsByCategory(itemListingAjaxDto.getCategory(),itemListingAjaxDto.getSort(),itemListingAjaxDto.getPage());
			
			List<ItemOutputListAjaxDto> iolaList= new ArrayList<>();
			for(Item item : items) {
				
				ItemOutputListAjaxDto iola= new ItemOutputListAjaxDto(item.getId(),this.itemImgRepository.findByItemAndRepimgYn(item, "Y").getUrl(),item.getSubject(), item.getPrice(), item.getFavorListNum());
				
				if(principal==null) {
					iola.setIsFavor(false);
				}else {
					if(this.favorRepository.findByMemberAndItem(this.memberRepository.findByToken(principal.getName()).get(), item).isEmpty()) {
						iola.setIsFavor(false);
					}else {
						iola.setIsFavor(true);
					}
				}
				
				iolaList.add(iola);
			}
			
			return iolaList;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	
	}
	
	@GetMapping("/detailorder/{id}")
	public String detailOrder(@PathVariable Long id,Model model) {
		Item item = this.itemRepository.findById(id).get();
		List<ItemImg> item_list =this.itemImgRepository.findByItem(item);
		List<String> urlList = new ArrayList<>();
//		List<Review> review = this.reviewService.getReviewList(id);
//		Long staradd = 0L ;
//		for(int i = 0 ; i < review.size(); i++) {
//		    staradd += review.get(i).getStar();
//		}
//		long starAvg = staradd / review.size();
//		System.out.println(starAvg);
		for(ItemImg itemImg : item_list) {
			urlList.add(itemImg.getUrl());
		}
		ItemDetailDto itemDetailDto = new ItemDetailDto(item.getPrice(),item.getSubject(),item.getCategory1(),item.getCategory2(),urlList);
		
		model.addAttribute("itemDetailDto", itemDetailDto);
		
		List<ItemSellingInform> isiList = this.itemSellingInformRepository.findByItem(item);
		System.out.println(isiList);
		model.addAttribute("isiList", isiList);
		model.addAttribute("itemId",id);
		
		
//		
//		item.getCategory1();
//		item.getCategory2();
//		item.getPrice();
//		item.getSubject();
//		
//		item.getTemperature();
//		item.getFavorList().size();
		//this.itemImgRepository.findAllByItem(id);
		
		return "detailorder";
	}
	
	
	@PostMapping("/addfavor")
	@ResponseBody
	public String addFavor(@RequestParam Long id, Principal principal) {
		
		Item item = this.itemRepository.findById(id).get();
		Member member = this.memberRepository.findByToken(principal.getName()).get();
		
		System.out.println(id);
		if(this.favorRepository.findByMemberAndItem(member, item).isEmpty()){
			Favor favor =new Favor();
			favor.setItem(item);
			favor.setMember(member);
			try {
				this.favorRepository.save(favor);
				item.setFavorListNum(this.favorRepository.findByItem(item).size());
				this.itemRepository.save(item);
				return "찜등록";
			}catch(Exception e) {
				return "찜등록실패";
			}
		}
		else {
			return "이미 찜목록에 등록된 상품입니다.";
		}
	}
	
	@PostMapping("/countcheck")
	@ResponseBody
	public String countCheck(@RequestParam Integer countNum,@RequestParam Long orderNum) {
		
		Integer remain = this.itemSellingInformRepository.findById(orderNum).get().getSellCount();
		if(remain >= countNum) {
			return "재고있음";
		}else {
			return "재고부족";
		}
	}
	
	
}
