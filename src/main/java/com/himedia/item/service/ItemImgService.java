package com.himedia.item.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.himedia.item.entity.ItemImg;
import com.himedia.item.repository.ItemImgRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

	 @Value("${itemImgLocation}")
	    private String itemImgLocation;

	    private final ItemImgRepository itemImgRepository;

	    private final FileService fileService;

	    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile, Principal principal) throws Exception{
	        String oriName = itemImgFile.getOriginalFilename();
	        String name = "";
	        String url = "";

	        //파일 업로드
	        if(!StringUtils.isEmpty(oriName)){
	            name = fileService.uploadFile(itemImgLocation, oriName,
	                    itemImgFile.getBytes());
	            url = "/images/item/" + name;
	        }

	        //상품 이미지 정보 저장
	        itemImg.updateItemImg(oriName, name, url);
	        itemImgRepository.save(itemImg);
	    }

	    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
	        if(!itemImgFile.isEmpty()){
	            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
	                    .orElseThrow(EntityNotFoundException::new);

	            //기존 이미지 파일 삭제
	            if(!StringUtils.isEmpty(savedItemImg.getName())) {
	                fileService.deleteFile(itemImgLocation+"/"+
	                        savedItemImg.getName());
	            }

	            String oriImgName = itemImgFile.getOriginalFilename();
	            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
	            String imgUrl = "/images/item/" + imgName;
	            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
	        }
	    }

	}
