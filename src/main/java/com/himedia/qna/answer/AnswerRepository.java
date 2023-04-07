package com.himedia.qna.answer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.member.entity.Member;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

	Optional<Answer> findByIdAndVoter(Integer id, Member member);
}
