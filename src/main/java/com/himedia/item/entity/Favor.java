package com.himedia.item.entity;

import com.himedia.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Favor {
	
	@Id
	private Long id;
	
	@ManyToOne
	private Member member;
	
	@ManyToOne
	private Item item;
	
	
	
}
