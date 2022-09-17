package com.example.together.PhotoService;

import com.example.together.PhotoEntity.answer.Answer;
import com.example.together.PhotoEntity.comment.Comment;
import com.example.together.PhotoEntity.comment.CommentRepository;
import com.example.together.PhotoEntity.photoquestion.PhotoQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhotoCommentService {

    private final PhotoQuestionService photoQuestionService;
    private final PhotoAnswerService photoAnswerService;
    private final CommentRepository commentRepository;

    // 답변 댓글
    public Page<Comment> getAnswerCommentList(int page, Long id) {
        Answer answer = photoAnswerService.getAnswer(id);
        Pageable pageable = PageRequest.of(page, 10);
        return this.commentRepository.findAllByAnswer(answer, pageable);
    }

    // 질문 댓글
    public Page<Comment> getQuestionCommentList(int page, Long id) {
        PhotoQuestion photoQuestion = photoQuestionService.getQuestion(id);
        Pageable pageable = PageRequest.of(page, 10);
        return this.commentRepository.findAllByPhotoQuestion(photoQuestion, pageable);
    }


    // 답변 댓글
    public Comment create(Answer answer, String content, String username, String password) {
        Comment c = new Comment();
        c.setContent(content);
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")); // 작성 시간 포멧팅
        c.setDate(currentTime);
        c.setAnswer(answer);
        c.setUsername(username);
        c.setPassword(password);
        c = this.commentRepository.save(c);
        return c;
    }

    // 질문 댓글
    public Comment create(PhotoQuestion photoQuestion, String content, String username, String password) {
        Comment c = new Comment();
        c.setContent(content);
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")); // 작성 시간 포멧팅
        c.setDate(currentTime);
        c.setPhotoQuestion(photoQuestion);
        c.setUsername(username);
        c.setPassword(password);
        c = this.commentRepository.save(c);
        return c;
    }

    public Optional<Comment> getComment(Long id) {

        return commentRepository.findById(id);
    }

    public Comment modify(Comment c, String content) {
        c.setContent(content);
        c = this.commentRepository.save(c);
        return c;
    }

    public Boolean delete(Comment c) {

        this.commentRepository.delete(c);
        return true;
    }

}
