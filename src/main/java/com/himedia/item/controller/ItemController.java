package com.himedia.item.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.himedia.item.dto.ItemAndImgDto;
import com.himedia.item.entity.ItemImg;
import com.himedia.item.service.ItemService;
import com.himedia.member.entity.Member;
import com.himedia.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {
	
	private final ItemService itemService;
	private final MemberService memberService;
	
//	@PreAuthorize("isAuthenticated()")
	@GetMapping("/good")
	public String itemForm(Model model,ItemAndImgDto itemAndImgDto, Principal principal) {
		
		System.out.println("good 컨트롤러 호출 완료 : ");
		System.out.println("principal 출력 Name==> : "+ principal.getName());
		System.out.println("principal 출력 ==> : "+ principal.toString());
		Member member = this.memberService.getMember(principal.getName());
		System.out.println(member.getMemberRole());
		model.addAttribute("itemAndImgDto", new ItemAndImgDto());
		
		
		
		
		return "upload";
		
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/shop/create")
	public String itemNew(@Valid ItemAndImgDto itemAndImgDto, BindingResult bindingResult,
			Model model, @RequestParam("imgfile") List<MultipartFile> imgfile, Principal principal 
             ) {
		
		if(bindingResult.hasErrors()) { 
			return "upload";
		}
		if (imgfile.get(0).isEmpty() && itemAndImgDto.getId() == null) {
			model.addAttribute("errorMessage","첫번쨰 상품 이미지는 필수 입력 값 입니다.");
			return "index";
		}
		
		try {
			itemService.saveItem(itemAndImgDto, imgfile , principal);
			
			
			
		}catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
			return "index";
		}
		
		
		return "redirect:/";
	}
	
	@GetMapping(value="/detail/{itemId}")				//3월5일
	public String itemdetail(Model model, @PathVariable long itemId) {
		
		List<ItemImg> itemImgList = this.itemService.getImgList(itemId);
		
		model.addAttribute("itemImgList", itemImgList);
		
		for(ItemImg img : itemImgList) {
			if(img.getRepimgYn().equals("Y")) {
				model.addAttribute("repimg", img);
				model.addAttribute("item", img.getItem());
			}
		}
		
		return "detail";
	}

}

