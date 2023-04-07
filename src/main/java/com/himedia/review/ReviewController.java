package com.himedia.review;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.himedia.item.entity.Item;
import com.himedia.item.service.ItemService;
import com.himedia.member.entity.Member;
import com.himedia.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/review")
public class ReviewController {

	private final ReviewService reviewService;
	private final MemberService memberService;
	private final ItemService itemService;

	@GetMapping("/list/{idx}")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page, ReviewForm reviewForm,@PathVariable("idx") Long idx) {
		Optional<Item> item = this.itemService.getOneItem(idx);
		Page<Review> paging = this.reviewService.getList(page,item.get());
		model.addAttribute("paging", paging);
		List<Review> q = this.reviewService.listReview();
		model.addAttribute("item", item.get());
		// Model 객체에 담아서 클라이언트에게 전송
		model.addAttribute("Review", q);
		return "review_list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{idx}")
	public String ReviewCreate(@RequestParam List<MultipartFile> file, @Valid ReviewForm reviewForm, 
			BindingResult bindingResult, Principal principal,@PathVariable("idx") Long idx) throws Exception {
	    if (bindingResult.hasErrors()) {
	        return "review_list";
	    }
	    Member member = this.memberService.getMember(principal.getName());
	    Optional<Item> item = this.itemService.getOneItem(idx);
	    this.reviewService.create(reviewForm.getContent(), member, reviewForm.getStar(),file,item.get());
	    return String.format("redirect:/review/list/%s", idx);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String reviewModify(ReviewForm reviewForm, @PathVariable("id") Integer id, Principal principal,
			Model model) {
		Review review = this.reviewService.getReview(id);

		model.addAttribute("review", review);
		return "review_modify";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String reviewModify(@Valid ReviewForm reviewForm, BindingResult bindingResult, Principal principal,
	        @PathVariable("id") Integer id) {
	    if (bindingResult.hasErrors()) {
	        return "review_modify";
	    }
	    Review review = this.reviewService.getReview(id);
	    Long idx = review.getItem().getId();
	    if (!review.getAuthor().getToken().equals(principal.getName())) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	    }
	    review.setContent(reviewForm.getContent());
	    review.setStar(reviewForm.getStar()); // 별점 수정
	    this.reviewService.modify(review, reviewForm.getContent(), reviewForm.getStar());
	    return String.format("redirect:/review/list/%s", idx);
	}



	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{idx}")
	public String reviewDelete(Principal principal, @PathVariable("idx") Integer idx) {
		Review review = this.reviewService.getReview(idx);
		Long id = review.getItem().getId();
		if (!review.getAuthor().getToken().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		}
		this.reviewService.delete(review);

		
		return String.format("redirect:/review/list/%s", id);

	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String reviewVote(Principal principal, @PathVariable("id") Integer id) {
		Review review = this.reviewService.getReview(id);
		Member member = this.memberService.getMember(principal.getName());
		Long idx = review.getItem().getId();
		
		this.reviewService.vote(review, member);
		
		return String.format("redirect:/review/list/%s", idx);
	}
}
