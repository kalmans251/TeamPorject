package com.himedia.qna.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.himedia.item.entity.Item;
import com.himedia.member.entity.Member;
import com.himedia.qna.answer.Answer;
import com.himedia.qna.answer.DateNotFoundException;
import com.himedia.review.Review;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	
	
private final QuestionRepository questionrepository;
	
    private Specification<Question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거 
                Join<Question, Member> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, Member> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목 
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용 
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자 
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용 
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자 
            }
        };
    }

	
	public Question getQuestion(Integer id) {
		
		Optional<Question> question = this.questionrepository.findById(id);
		if(question.isPresent()) {		
			return question.get();		
		}else {

			throw new DateNotFoundException("요청한 파일을 찾지 못했습니다.");
		 }
	}
	
	public void create(String subject, String content, Member member, String secret,Item item) {
		
		// Question 객체를 생성후 Setter 주입
		Question q = new Question();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(member);
		q.setSecret(secret);
		q.setItem(item);
		//Repository 의 save() 메소드에 q 저장
		this.questionrepository.save(q);
	}
	
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionrepository.save(question);
    }
	
	public void delete(Question question) {
		this.questionrepository.delete(question);
	}
	
	public void vote(Question question, Member member) {

		question.getVoter().add(member);
		this.questionrepository.save(question);
		
		
	}

	
	
	public Page<Question> getList(int page,String itemId, String kw) {
		 List<Sort.Order> sorts = new ArrayList<>();
		 sorts.add(Sort.Order.desc("createDate"));
		 Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		 return this.questionrepository.findAllByKeywordAndKeyword(kw,itemId, pageable);
		 }
}
