package com.himedia.member.email;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmailController {
	
	private final EmailService emailService;
	
	@PostMapping("/emailConfirm")
	@ResponseBody
	public String emailConfirm(@RequestParam String email) throws Exception {

		System.out.println(email);
	  String confirm = emailService.sendSimpleMessage(email);

	  return confirm;
	}
	@PostMapping("/login/form")
	public String email(@RequestParam("username") String email) {
		try {
			 System.out.println(email);
			 String confirm = emailService.sendSimpleMessage(email);
		}catch(Exception e) {
			 e.printStackTrace();
		}
				return "login";
	}
}
