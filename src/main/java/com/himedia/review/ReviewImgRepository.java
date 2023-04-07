package com.himedia.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long> {

	
	List<ReviewImg> findByReview(Review review);
	


}
