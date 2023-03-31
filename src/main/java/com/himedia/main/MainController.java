package com.himedia.main;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.himedia.item.entity.Item;
import com.himedia.item.itemMain.ItemListingAjaxDto;
import com.himedia.item.itemMain.ItemMainService;
import com.himedia.item.repository.ItemImgRepository;
import com.himedia.item.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final ItemMainService itemMainService;
	private final ItemRepository itemRepository;
	private final ItemImgRepository itemImgRepository;
	@GetMapping("/")
	public String index() {
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
	public Page<Item> ajaxTest(@RequestBody ItemListingAjaxDto itemListingAjaxDto) {
		Page<Item> items = null;
		try {
			items = this.itemMainService.findItemsByCategory(itemListingAjaxDto.getCategory(),itemListingAjaxDto.getSort(),itemListingAjaxDto.getPage());
			System.out.println(items);
			System.out.println(itemListingAjaxDto.getCategory());
			System.out.println(itemListingAjaxDto.getSort());
			return items;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return items;
	
	}
	@GetMapping("/detailorder/{id}")
	public String detailOrder(@PathVariable Long id) {
		
		Item item = this.itemRepository.findById(id).get();
		
		item.getCategory1();
		item.getCategory2();
		item.getPrice();
		item.getSubject();
		
		item.getTemperature();
		item.getFavorList().size();
		//this.itemImgRepository.findAllByItem(id);
		
		return "detailorder";
	}
	
}
