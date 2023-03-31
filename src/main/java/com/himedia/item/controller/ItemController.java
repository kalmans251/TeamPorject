package com.himedia.item.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.himedia.item.dto.ItemAndImgDto;
import com.himedia.item.service.ItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {
	
	private final ItemService itemService;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/good")
	public String itemForm(Model model,ItemAndImgDto itemAndImgDto) {
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
	
//	   @GetMapping(value = "/admin/item/{itemId}")
//	    public String itemDtl(@PathVariable("itemId") Long itemId, Model model){
//
//	        try {
//	            ItemAndImgDto itemAndImgDto = itemService.get
//	            model.addAttribute("itemAndImgDto", itemAndImgDto);
//	        } catch(EntityNotFoundException e){
//	            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
//	            model.addAttribute("itemFormDto", new ItemFormDto());
//	            return "item/itemForm";
//	        }
//
//	        return "item/itemForm";
//	    }
	
	
	

}

