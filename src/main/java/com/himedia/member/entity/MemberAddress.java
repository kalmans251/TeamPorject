package com.himedia.member.entity;


import java.time.LocalDateTime;

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
public class MemberAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idx;
	
	private Integer addrnum;
	
	private String addr;
	
	private Integer main;
	
	@ManyToOne
	public Member member;
	
	public LocalDateTime createDate;
	
	public String reference;

	
}
