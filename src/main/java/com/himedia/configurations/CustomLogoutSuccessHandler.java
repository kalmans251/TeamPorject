package com.himedia.configurations;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.himedia.member.entity.Member;
import com.himedia.member.repository.MemberRepository;
import com.himedia.member.role.Social;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
 

    private final MemberRepository memberRepository;

    public CustomLogoutSuccessHandler(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        Optional<Member> memberget = memberRepository.findByToken(username);
        Member member = memberget.get();
        if(member.getSocial().equals(Social.GOOGLE)||member.getSocial().equals(Social.NAVER)) {
        	member.setToken(null);
        	memberRepository.save(member);
        }
        String redirectUrl = "/";
        response.sendRedirect(redirectUrl);
    }
}
