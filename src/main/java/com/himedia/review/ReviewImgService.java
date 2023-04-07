package com.himedia.review;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.himedia.item.service.FileService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class ReviewImgService {
	
	 @Value("${itemImgLocation}")
     private String itemImgLocation;
	
	 private final FileService fileService;
	 
	 public String returnReviewImg(MultipartFile img) throws Exception{


			String oriName = img.getOriginalFilename();

			String name ="";

			String url = "";


			if(!StringUtils.isEmpty(oriName)) {

			name = fileService.uploadFile(itemImgLocation, oriName, img.getBytes());

			return url = "/images/item/"+name;

			}


			return null;


			}

		
}
