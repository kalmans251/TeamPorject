package com.himedia.item.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ItemImg {
	
	@Id
	@Column(name="item_img_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;		//이미지 아이디
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;		//상품 아이디
	
	private String oriName;			//원본이미지이름
	
	private String name;			//변형된 이름 (랜덤)
	
	private String url;				//이미지주소
	
	private String repimgYn;		//대표이미지
	
	private LocalDateTime regDate;		//등록일
	
	private LocalDateTime modifiedDate;		//수정일
	
	public void updateItemImg(String oriName, String name, String url) {
		this.oriName = oriName;
		this.url = url;
		this.name = name;
	}
}