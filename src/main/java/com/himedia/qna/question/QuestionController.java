package com.himedia.qna.question;

import java.security.Principal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.himedia.item.entity.Item;
import com.himedia.item.service.ItemService;
import com.himedia.member.entity.Member;
import com.himedia.member.service.MemberService;
import com.himedia.qna.answer.Answer;
import com.himedia.qna.answer.AnswerForm;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	
	private final QuestionService questionService;
	private final MemberService memberService;
	private final ItemService itemService;

	@GetMapping(value="/question/list/{idx}")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw,@PathVariable("idx") Long idx) {
		
		Optional<Item> item = this.itemService.getOneItem(idx);
		Item item2 = item.get();
		Page<Question> paging = this.questionService.getList(page,idx.toString(),kw);
		System.out.println();
		model.addAttribute("item", item2);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		return "question_list";
	}

	// 상세 페이지를 처리하는 메소드 : /question/detail/1
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value="/question/shop/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm,Principal principal) {

		System.out.println(principal.getName());
		// 서비스 클래스의 메소드 호출 : 상세페이지 보여달라
		Question q = this.questionService.getQuestion(id);

		// Model 객체에 담아서 클라이언트에게 전송
		model.addAttribute("question", q);
		return "read"; // templates : question_detail.html

	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value="/question/create/{id}")
	public String questioncreate(QuestionForm questionForm,@PathVariable("id") Long idx, Model model) {
		Optional<Item> items = this.itemService.getOneItem(idx);
		Item item = items.get();
		model.addAttribute("item", item);
		
		return "write";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/question/create/{idx}")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal,@PathVariable("idx") Long idx) {

		if (bindingResult.hasErrors()) {
			return "write";
		}

		Optional<Item> items = this.itemService.getOneItem(idx);
		Item item = items.get();
		
		Member member = this.memberService.getMember(principal.getName());
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), member,
				questionForm.getSecret(),item);
		return String.format("redirect:/question/list/%s", idx);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("question/modify/{id}")
	public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal,Model model) {
		Question question = this.questionService.getQuestion(id);
		
		model.addAttribute("question", question);
		return "question_modify";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("question/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal,
			@PathVariable("id") Integer id) {
		if (bindingResult.hasErrors()) {
			return "question_modify";
		}
		Question question = this.questionService.getQuestion(id);
		if (!question.getAuthor().getToken().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		
		return String.format("redirect:/question/shop/%s", id);


	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("question/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);

		
		if (!question.getAuthor().getToken().equals(principal.getName())) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
	}
	this.questionService.delete(question);

		
	return String.format("redirect:/question/shop/%s", id);
		
		
	}

	// id : Question 객체
	// principal : 현재 투표하는 객체를 가지고 온다.

	@PreAuthorize("isAuthenticated()")
	@GetMapping("question/vote/{id}")
	public String questionVote(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		Member member = this.memberService.getMember(principal.getName());
		this.questionService.vote(question, member);
		return String.format("redirect:/question/shop/%s", id);
	}
	
	 private Specification<Question> search(String kw) {
		 return new Specification<>() {
		 private static final long serialVersionUID = 1L;
		 @Override
		 public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query,
		CriteriaBuilder cb) {
		 query.distinct(true); // 중복을 제거
		 Join<Question, Member> u1 = q.join("username", JoinType.LEFT);
		 Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
		 Join<Answer, Member> u2 = a.join("username", JoinType.LEFT);
		 return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
		 cb.like(q.get("content"), "%" + kw + "%"), // 내용
		 cb.like(u1.get("username"), "%" + kw + "%"), // 질문 작성자
		 cb.like(a.get("content"), "%" + kw + "%"), // 답변 내용
		 cb.like(u2.get("username"), "%" + kw + "%")); // 답변 작성자
		 }
		 };
		 }
}
