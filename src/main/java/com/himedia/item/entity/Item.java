package com.himedia.item.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.himedia.member.entity.Member;
import com.himedia.qna.question.Question;
import com.himedia.review.Review;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
	
	
	private String category1;		//큰분류
	
	private String category2;		//작은분류
	
	@OneToMany(cascade = CascadeType.REMOVE,mappedBy ="item",targetEntity = Favor.class)
	private List<Favor> favorList;		//찜목록
	
	private Integer favorListNum; // 찜한사람수
	
	private Long temperature;	//온도
	
	private LocalDateTime regDate;	//등록일
	
	private LocalDateTime modifiedDate;		//수정일
	
	private Long price;		//가격
	
	@OneToMany(cascade = CascadeType.REMOVE,mappedBy ="item",targetEntity = Question.class)
	private List<Question> question;
	
	@OneToMany(cascade = CascadeType.REMOVE,mappedBy ="item",targetEntity = Review.class)
	private List<Review> review;
	
}
