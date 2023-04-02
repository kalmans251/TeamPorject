package com.himedia.member.entity;

import java.util.ArrayList;
import java.util.List;

import com.himedia.member.role.MemberRole;
import com.himedia.member.role.Social;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long idx;
	
	@Column(unique=true)
	public String username;
	
	public String password;
	
	public String nickName;
	
	public String type;
	
	public String phoneNum;
	
	@Enumerated(EnumType.STRING)
	public MemberRole memberRole;

	@Enumerated(EnumType.STRING)
	public Social social;

	@Column(unique=true)
	public String token;
	
//	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
//    private List<MemberAddress> memberAddresses;
}
