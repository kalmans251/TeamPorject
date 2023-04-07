package com.himedia.qna.answer;

import java.time.LocalDateTime;
import java.util.Set;

import com.himedia.member.entity.Member;
import com.himedia.qna.question.Question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 4000)
	private String content;

	private LocalDateTime createDate;	
	
	private LocalDateTime modifyDate;
	
	@OneToOne		
	private Question question; 
	
	@ManyToOne		
	private Member author;
	
	@ManyToMany
	Set<Member> voter;
}
