package com.himedia.review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.himedia.item.entity.Item;
import com.himedia.member.entity.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Review {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@Column(length = 2000)
	private String content;
	
	@Column(updatable = false)
	private LocalDateTime createDate;
	
	private LocalDateTime modifyDate;

	@ManyToOne
	private Member author;
	
	@ManyToMany
	Set<Member> voter;
	
	@Column
	private Integer star;
	
	@OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE)
	private List<ReviewImg> reviewImg;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "item", nullable = false)
	private Item item;
}
