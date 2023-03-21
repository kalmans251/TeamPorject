package com.himedia.item.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ItemImg {
	
	@Id
	private Long id;
	
	@ManyToOne
	private Item item;
	
	private String oriName;
	private String name;
	private String url;
	private String repImgYn;
	private LocalDateTime regDate;
	private LocalDateTime modifiedDate;
}
