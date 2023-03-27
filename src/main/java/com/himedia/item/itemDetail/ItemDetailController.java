package com.himedia.item.itemDetail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemDetailController {
	
	@GetMapping("/aa/aa")
	public String itemForm() {
		return "/song/good";
	}
	
	@GetMapping("/bb/bb")
	public String itemFormenter() {
		return "/index";
	}
	

}
