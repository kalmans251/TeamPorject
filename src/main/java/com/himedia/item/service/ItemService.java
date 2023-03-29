package com.himedia.item.service;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.himedia.item.dto.ItemAndImgDto;
import com.himedia.item.entity.ColorRepository;
import com.himedia.item.entity.Item;
import com.himedia.item.entity.ItemImg;
import com.himedia.item.entity.ItemSellingInform;
import com.himedia.item.entity.SizeRepository;
import com.himedia.item.repository.ItemImgRepository;
import com.himedia.item.repository.ItemRepository;
import com.himedia.item.repository.ItemSellingInformRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	private final ItemImgService itemImgService;
	private final ColorRepository colorRepository;
	private final SizeRepository sizeRepository;
	private final ItemImgRepository itemImgRepository;
	private final ItemSellingInformRepository itemSellingInformRepository;
	
	public void saveItem(ItemAndImgDto itemAndImgDto, List<MultipartFile> imgfile, Principal principal)
			throws Exception {

		Item items = itemAndImgDto.toItem();
		

		List<String> colorList = itemAndImgDto.getColorList();
		List<String> sellCountList = itemAndImgDto.getSellCountList();
		List<String> sizeList = itemAndImgDto.getSizeList();
		
	
		
		for(int i = 0 ; i < sellCountList.size() ; i++) {
			ItemSellingInform itSI = new ItemSellingInform();
			
			itSI.setItem(items);
			itSI.setSize(this.sizeRepository.findByName(sizeList.get(i)));
			itSI.setColor(this.colorRepository.findByName(colorList.get(i)));
			itSI.setSellCount(Integer.parseInt(sellCountList.get(i)));
			this.itemSellingInformRepository.save(itSI);
		}
		

		this.itemRepository.save(items);

		for (int i = 0; i < imgfile.size(); i++) {
			ItemImg itemImg = new ItemImg();
			itemImg.setItem(items);

			if (i == 0)
				itemImg.setRepimgYn("Y");
			else
				itemImg.setRepimgYn("N");

			if (!(imgfile.get(i).getOriginalFilename() == ""))
				this.itemImgService.saveItemImg(itemImg, imgfile.get(i), principal);

		}
	}
	
	
}
