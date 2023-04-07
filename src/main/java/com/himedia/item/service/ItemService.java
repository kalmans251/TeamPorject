package com.himedia.item.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
import com.himedia.member.entity.Member;
import com.himedia.member.repository.MemberRepository;

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
	private final MemberRepository memberRepository;
	
	public Long saveItem(ItemAndImgDto itemAndImgDto, List<MultipartFile> imgfile, Principal principal)
			throws Exception {

		Item items = itemAndImgDto.toItem(itemAndImgDto);
		/*
		 * System.out.println(items.getSubject()); System.out.println(items.getPrice());
		 * System.out.println(items.getCategory1());
		 * System.out.println(items.getCategory2());
		 */
		Optional<Member> member = this.memberRepository.findByToken(principal.getName());
		items.setMember(member.get());
		items.setRegDate(LocalDateTime.now());
		items.setFavorListNum(0);
		items.setTemperature(itemAndImgDto.getTemperature());
		
		
		
		this.itemRepository.save(items);
		
		
		List<String> colorList = itemAndImgDto.getColorList();
		List<String> sellCountList = itemAndImgDto.getSellCountList();
		List<String> sizeList = itemAndImgDto.getSizeList();
		
		/*
		 * System.out.println(sizeList.toString());
		 * System.out.println(sellCountList.toString());
		 * System.out.println(colorList.toString());
		 */
		
		for(int i = 0 ; i < colorList.size() ; i++) {
			ItemSellingInform itSI = new ItemSellingInform();
			
			itSI.setItem(items);
			itSI.setSize(this.sizeRepository.findByName(sizeList.get(i)));
			itSI.setColor(this.colorRepository.findByName(colorList.get(i)));
			itSI.setSellCount(Integer.parseInt(sellCountList.get(i)));
			this.itemSellingInformRepository.save(itSI);
			
		}
		


		for (int i = 0; i < imgfile.size(); i++) {
			ItemImg itemImg = new ItemImg();
			itemImg.setItem(items);
			System.out.println(itemImg.getItem().getId());
			if (i == 0)
				itemImg.setRepimgYn("Y");
			else
				itemImg.setRepimgYn("N");
			if (!(imgfile.get(i).getOriginalFilename() == ""))
				this.itemImgService.saveItemImg(itemImg, imgfile.get(i), principal);
		}
		
		 return items.getId();
	}
	public List<ItemImg> getImgList(Long idx){
		
		List<ItemImg> itemImg = this.itemImgRepository.findByItemIdOrderByIdAsc(idx);
		
		return itemImg;
	}
	public Optional<Item> getOneItem(Long idx){
		
		return this.itemRepository.findById(idx);
	}
	

	
	
	
	

	
}
