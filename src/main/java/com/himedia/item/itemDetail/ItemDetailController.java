package com.himedia.item.itemDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.himedia.item.entity.Color;
import com.himedia.item.entity.Size;

@Controller
public class ItemDetailController {
	
	@Autowired
	private ItemDetailRepository itemDetailRepository;

	@GetMapping("/aa/aa")
	public String itemForm() {
		return "/song/good";
	}

	@GetMapping("/bb/bb")
	public String itemFormenter() {
		return "/index";
	}

}
