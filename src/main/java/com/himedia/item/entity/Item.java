package com.himedia.item.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.himedia.member.entity.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item {
	
	@Id
	private Long id;
	
	@ManyToOne
	private Member member;
	@ManyToOne
	private Category category;
	
	@OneToMany(cascade = CascadeType.REMOVE,mappedBy ="item")
	private List<Favor> favorList;
	
	private Long temperature;
	private Integer views;
	private LocalDateTime regDate;
	private LocalDateTime modifiedDate;
	
}
