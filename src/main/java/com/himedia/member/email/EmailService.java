package com.himedia.member.email;

public interface EmailService {
    String sendSimpleMessage(String to)throws Exception;
    
    String createPassword(String to)throws Exception;
}
