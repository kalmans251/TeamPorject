package com.himedia.qna.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.himedia.member.entity.Member;
import com.himedia.qna.question.Question;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor		
@Service
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
public Answer create(Question question, String content, Member author) {
		
		Answer answer = new Answer();
		
		
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setAuthor(author);
		
		this.answerRepository.save(answer);
		
		return answer;
	}
	
	public Answer getAnswer(Integer id) {
		
		Optional<Answer> answer = this.answerRepository.findById(id);
		
		if(answer.isPresent()) {
			return answer.get();
		}else {
			throw new DateNotFoundException("failed");
		}
	}
	
	public void modify(Answer answer, String content) {
		
		System.out.println("기존의 닶변을 수정함");
		
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		this.answerRepository.save(answer);
		
	}
	
	public void delete(Answer answer) {
		this.answerRepository.delete(answer);
	}
	
	public void vote(Answer answer, Member member) {
		answer.getVoter().add(member);
		this.answerRepository.save(answer);
	}
}
