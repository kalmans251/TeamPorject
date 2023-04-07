package com.himedia.review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.himedia.item.entity.Item;
import com.himedia.item.service.ItemImgService;
import com.himedia.member.entity.Member;
import com.himedia.qna.answer.DateNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {

	private final ReviewRepository reviewrepository;
	private final ReviewImgRepository reviewImgRepository;
	private final ReviewImgService reviewImgService;
	
	public Review getReview(Integer id) {

		Optional<Review> review = this.reviewrepository.findById(id);
		if (review.isPresent()) {
			return review.get();
		} else {

			throw new DateNotFoundException("요청한 파일을 찾지 못했습니다.");
		}
	}

	public List<Review> listReview() {

		return this.reviewrepository.findAll();
	}

	public void create(String content, Member member, Integer Star,List<MultipartFile> file,Item item) throws Exception {

		Review review = new Review();
		review.setContent(content);
		review.setCreateDate(LocalDateTime.now());
		review.setAuthor(member);
		review.setStar(Star);
		review.setItem(item);
		this.reviewrepository.save(review);
		
		for(int i=0; i<file.size(); i++) {
			ReviewImg reviewImg = new ReviewImg();
			reviewImg.setReview(review);
			reviewImg.setUrl(this.reviewImgService.returnReviewImg(file.get(i)));
			this.reviewImgRepository.save(reviewImg);
		}
	}

	public void modify(Review review, String content, Integer Star) {
		review.setContent(content);
		review.setModifyDate(LocalDateTime.now());
		review.setStar(Star);
		this.reviewrepository.save(review);
	}

	public void delete(Review review) {
		this.reviewrepository.delete(review);
	}

	public void vote(Review review, Member member) {
		Optional<Review> _review = this.reviewrepository.findByIdAndVoter(review.getId(), member);
		if(_review.isPresent()) {
			review.getVoter().remove(member);
		}else {
			review.getVoter().add(member);
		}
		this.reviewrepository.save(review);
	}


	public Page<Review> getList(int page,Item item) {
		 List<Sort.Order> sorts = new ArrayList<>();
		 sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));
		return this.reviewrepository.findByItem(pageable, item);
	}
	public List<Review> getReviewList(long itemId){
		return this.reviewrepository.findByItem(itemId);
	}
	
}
