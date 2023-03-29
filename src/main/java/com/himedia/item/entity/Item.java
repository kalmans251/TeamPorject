package com.himedia.item.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.himedia.item.entity.enums.CategoryEnum1;
import com.himedia.item.entity.enums.CategoryEnum2;
import com.himedia.member.entity.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	//아이템 아이디
	
	@ManyToOne
	private Member member;		//고객
	
	private String subject;		//상품
	
	@Enumerated(EnumType.STRING)
	private CategoryEnum1 category1;		//큰분류
	
	@Enumerated(EnumType.STRING)
	private CategoryEnum2 category2;		//작은분류
	
	@OneToMany(cascade = CascadeType.REMOVE,mappedBy ="item")
	private List<Favor> favorList;		//찜목록
	
	private Integer favorListNum; // 찜한사람수
	
	private Long temperature;	//온도
	
	private LocalDateTime regDate;	//등록일
	
	private LocalDateTime modifiedDate;		//수정일
	
	private Long price;		//가격
	
}
