package com.himedia.configurations;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.himedia.member.entity.Member;
import com.himedia.member.entity.MemberAddress;
import com.himedia.member.repository.MemberAddrRepository;
import com.himedia.member.repository.MemberRepository;
import com.himedia.member.role.MemberRole;
import com.himedia.member.role.Social;

import jakarta.servlet.http.HttpSession;

@Service
public class CustomOAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	HttpSession httpSession;
	
	@Autowired
	MemberAddrRepository memberAddressRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService delegate = new DefaultOAuth2UserService();
	    OAuth2User oAuth2User = delegate.loadUser(userRequest);

	    String registration = userRequest.getClientRegistration().getRegistrationId();

	    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
	    String email;
	    String name; 
	    String token;

	    Map<String, Object>response = oAuth2User.getAttributes();

	    if(registration.equals("naver")) {
	        Map<String, Object> hash = (Map<String, Object>)response.get("response");
	        email = (String) hash.get("email");
	        System.out.println(email);
	        name = (String) hash.get("name");
	        System.out.println(name);
	        token =(String) hash.toString();
	    } else if(registration.equals("google")) {
	        email = (String) response.get("email");
	        name = (String) response.get("name");
	       	token = (String) response.get("sub");

	        // Principal 객체 생성
	        Principal principal = new Principal() {
	            @Override
	            public String getName() {
	            	
	                return email;
	            }
	        };
	        System.out.println(principal.getName());

	        // Authentication 객체 생성
	        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, 
	                AuthorityUtils.createAuthorityList(MemberRole.USER.toString()));

	        // SecurityContextHolder에 Authentication 객체 저장
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	    } else {
	        throw new OAuth2AuthenticationException("허용되지 않는 인증입니다.");
	    }
	    Member member = null;
	    
	    Optional<Member> optionalMember = memberRepository.findByUsername(email);
	    if(optionalMember.isPresent()) {
	        member = optionalMember.get();
	        member.setToken(token);
	        this.memberRepository.save(member);
	    } else {
	        if(registration.equals("naver")) {
	            member = new Member();
	            member.setNickName(name);
	            member.setUsername(email);
	            member.setToken(token);
	            member.setMemberRole(MemberRole.USER);
	            member.setSocial(Social.NAVER);
	            this.memberRepository.save(member);
	        } else if(registration.equals("google")) {
	            member = new Member();
	            member.setNickName(name);
	            member.setToken(token);
	            member.setUsername(email);
	            member.setMemberRole(MemberRole.USER);
	            member.setSocial(Social.GOOGLE);
	            this.memberRepository.save(member);
	        }
	        MemberAddress memberAddress = new MemberAddress();
            memberAddress.setMain(1);
            memberAddress.setAddr("대표주소를 설정해 주셔야 합니다.");
            memberAddress.setMember(member);
            memberAddress.setCreateDate(LocalDateTime.now());
            memberAddress.setReference("");
            this.memberAddressRepository.save(memberAddress);
	    }
	    httpSession.setAttribute("member", member);
	    
	    return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getMemberRole().toString())), 
	            oAuth2User.getAttributes(), 
	            userNameAttributeName);
	}

}
