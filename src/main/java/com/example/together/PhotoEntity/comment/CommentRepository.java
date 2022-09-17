package com.example.together.PhotoEntity.comment;

import com.example.together.PhotoEntity.answer.Answer;
import com.example.together.PhotoEntity.photoquestion.PhotoQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByQuestion(PhotoQuestion question, Pageable pageable);

    Page<Comment> findAllByAnswer(Answer question, Pageable pageable);

}
