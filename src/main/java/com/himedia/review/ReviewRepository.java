package com.himedia.review;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.item.entity.Item;
import com.himedia.member.entity.Member;


public interface ReviewRepository extends JpaRepository<Review, Integer> {

	 Page<Review> findAll(Pageable pageable);
	 Page<Review> findAll(Specification<Review> spec, Pageable pageable);
	 Page<Review> findByItem(Pageable pageable, Item item);
	 Optional<Review> findByIdAndVoter(Integer id, Member member);
	 
	 
	 Review findByContent(String content);

}
