package com.himedia.item.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.himedia.item.dto.FavorDto;
import com.himedia.item.entity.Favor;
import com.himedia.item.entity.Item;
import com.himedia.item.entity.ItemImg;
import com.himedia.item.repository.FavorRepository;
import com.himedia.item.repository.ItemImgRepository;
import com.himedia.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FavorController {
	
	private final FavorRepository favorRepository;
	private final MemberRepository memberRepository;
	private final ItemImgRepository itemImgRepository;

	@GetMapping("/favorlist")
	public String favorList(Model model, Principal principal) {
		
		List<FavorDto> favorDtoList = new ArrayList<>();
		List<Favor> favorList = this.favorRepository.findByMember(this.memberRepository.findByToken(principal.getName()).get());
		for(int i=0; i<favorList.size(); i++) {
			
			Item item = favorList.get(i).getItem();
			ItemImg itemImg = this.itemImgRepository.findByItemAndRepimgYn(item, "Y");
			FavorDto favorDto = new FavorDto(item.getId(),itemImg.getUrl(),item.getSubject(),item.getPrice(),favorList.get(i).getId());
			favorDtoList.add(favorDto);
		}
		model.addAttribute("favorDtoList", favorDtoList);
		return "favorList";
	}
	
	@GetMapping("/deleteFavor")
	@ResponseBody
	public String deleteFavorAjax(@RequestParam Long favorId) {
		try {
			this.favorRepository.delete(this.favorRepository.findById(favorId).get());
		}catch(Exception e) {
			return "오류발생";
		}
	
		return "삭제완료";
	}
	
	
}
