package com.himedia.qna.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.himedia.item.entity.Item;
import com.himedia.member.entity.Member;
import com.himedia.qna.answer.Answer;

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

@Entity 
@Getter 
@Setter
public class Question {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String subject;
	
	@Column(length = 2000)
	private String content;
	
	@Column(updatable = false)
	private LocalDateTime createDate;
	
	private LocalDateTime modifyDate;

	private String secret;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
	
	@ManyToOne
	private Member author;
	

	
	@ManyToMany
	Set<Member> voter;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "item")
	private Item item;
	
	
}
