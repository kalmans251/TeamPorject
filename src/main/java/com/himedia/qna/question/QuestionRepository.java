package com.himedia.qna.question;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;




public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	Question findBySubject(String subject);
	 Question findBySubjectAndContent(String subject, String content);
	 List<Question> findBySubjectLike(String subject);
	 Page<Question> findAll(Pageable pageable);
	 Page<Question> findAll(Specification<Question> spec, Pageable pageable);
	
	 
	 
		@Query("select distinct q " +
		        "from Question q " +
		        "left join q.author u1 " +
		        "left join q.answerList a " +
		        "left join a.author u2 " +
		        "where q.item.id like :itemId " +
		        "and (q.subject like %:kw% "+
		        "or q.content like %:kw% " +
		        "or u1.username like %:kw% " +
		        "or a.content like %:kw% " +
		        "or u2.username like %:kw% )")
		Page<Question> findAllByKeywordAndKeyword(@Param("kw") String kw,@Param("itemId") String itemId, Pageable pageable);

		
		
}
