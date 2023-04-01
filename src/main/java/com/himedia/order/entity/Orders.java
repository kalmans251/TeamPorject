package com.himedia.order.entity;

import java.time.LocalDateTime;

import com.himedia.item.entity.ItemSellingInform;
import com.himedia.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String imp_uid;
	
	private String merchant_uid;
	
	@ManyToOne
	private Member member;
	@ManyToOne
	private ItemSellingInform itemSellingInform;
	
	private LocalDateTime regDate;
	
	private Integer buyCount;
	
	
}
