package com.himedia.item.itemMain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemMainController {
	
	
	private final ItemMainService itemMainService;
	
	@GetMapping("/list/{category}")
    public String getCategoryAndSorting(String category,String sort,Integer page ) {
    	
    	itemMainService.findItemsByCategory(category,sort,page);
    	
    	return null;
    }
	
}
