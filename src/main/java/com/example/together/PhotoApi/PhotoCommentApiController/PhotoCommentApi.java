package com.example.together.PhotoApi.PhotoCommentApiController;

import com.example.together.PhotoConfigDto.PhotoDeleteInfoDto;
import com.example.together.PhotoConfigDto.PhotoModifyInfoDto;
import com.example.together.PhotoConfigDto.PhotoSuccessDto;
import com.example.together.PhotoController.commentController.PhotoCommentForm;
import com.example.together.PhotoEntity.answer.Answer;
import com.example.together.PhotoEntity.comment.Comment;
import com.example.together.PhotoService.PhotoAnswerService;
import com.example.together.PhotoService.PhotoCommentService;
import com.example.together.PhotoService.PhotoQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/photo/comments")
public class PhotoCommentApi {

    private final PhotoAnswerService photoAnswerService;
    private final PhotoCommentService photoCommentService;
    private final PhotoQuestionService photoQuestionService;

    // 답변 댓글 가져오기
//    @GetMapping("/answer/list/{answerId}")
//    public ResponseEntity<Map<String, Object>> answerCommentList(@PathVariable("answerId") Long id,
//                                  @RequestParam(value = "page", defaultValue = "0") int page) {
//
//        Answer answer = answerService.getAnswer(id);
//        AnswerDto answerDto = new AnswerDto(answer.getId(), answer.getContent(), answer.getCreateDate(), answer.getUsername(), answer.getVoter().size(), answer.getCommentList());
//        Page<Comment> commentPage = commentService.getAnswerCommentList(page, id);
//
//        if (commentPage.getNumberOfElements() == 0 && page != 0) {
//            throw new IllegalArgumentException("잘못된 입력 값입니다");
//        }
//
//        Page<CommentDto> commentDtoPage = commentPage.map(
//                post -> new CommentDto (
//                        post.getId(),post.getContent(),post.getCreateDate(),
//                        post.getModifyDate(),post.getUsername()
//                ));
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("answer", answerDto);
//        result.put("comments", commentDtoPage);
//
//        return ResponseEntity.ok(result);
//    }
//    // 질문 댓글 가져오기
//    @GetMapping("/question/list/{questionId}")
//    public ResponseEntity<Map<String, Object>> questionCommentList(@PathVariable("questionId") Long id,
//                                    @RequestParam(value = "page", defaultValue = "0") int page) {
//
//        Question question = questionService.getQuestion(id);
//        QuestionDto questionDto = new QuestionDto(question.getId(), question.getSubject(), question.getContent(), question.getCreateDate(), question.getModifyDate(),question.getUsername() ,question.getView(), question.getVoter().size());
//        Page<Comment> commentPage = commentService.getQuestionCommentList(page, id);
//
//        if (commentPage.getNumberOfElements() == 0 && page != 0) {
//            throw new IllegalArgumentException("잘못된 입력 값입니다.");
//        }
//
//        Page<CommentDto> commentDtoPage = commentPage.map(
//                post -> new CommentDto (
//                        post.getId(),post.getContent(),post.getCreateDate(),
//                        post.getModifyDate(),post.getUsername()
//                ));
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("question", questionDto);
//        result.put("comments", commentDtoPage);
//
//        return ResponseEntity.ok(result);
//    }

    // 답변 댓글 생성
    @PostMapping(value = "/answers/{id}")
    public ResponseEntity<PhotoCommentCreateForm> createAnswerComment(@PathVariable("id") Long id, @Valid @RequestBody PhotoCommentForm photoCommentForm,
                                                                      BindingResult bindingResult) {

        Optional<Answer> answer = Optional.ofNullable(this.photoAnswerService.getAnswer(id));

        if (answer.isPresent()) {
            if (bindingResult.hasErrors()) {
                throw new IllegalArgumentException("잘못된 입력 값입니다.");
            }
           Comment c = this.photoCommentService.create(answer.get(), photoCommentForm.getContent(), photoCommentForm.getUsername(), photoCommentForm.getPassword());
            // 이름이랑 제목만 전달
            PhotoCommentCreateForm photoCommentCreateForm = new PhotoCommentCreateForm(photoCommentForm.getContent(), photoCommentForm.getUsername());
            return ResponseEntity.ok(photoCommentCreateForm);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "요청하신 데이터를 찾을 수 없습니다.");
        }
    }

    // 질문 댓글 생성
//    @PostMapping(value = "/post/{id}")
//    public ResponseEntity createQuestionComment(@PathVariable("id") Long id, @Valid @RequestBody PhotoCommentForm commentForm,
//                                                BindingResult bindingResult) {
//
//        Optional<Question> question = Optional.ofNullable(this.questionService.getQuestion(id));
//
//        if (question.isPresent()) {
//            if (bindingResult.hasErrors()) {
//                throw new IllegalArgumentException("잘못된 입력 값입니다.");
//            }
//            Comment c = this.commentService.create(question.get(), commentForm.getContent(), commentForm.getUsername(), commentForm.getPassword());
//
//            PhotoCommentModifyForm commentApiForm = new PhotoCommentModifyForm(commentForm.getContent());
//            return ResponseEntity.ok(commentApiForm);
//
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "요청하신 데이터를 찾을 수 없습니다.");
//        }
//
//    }

//    @GetMapping("/modify/{id}")
//    public ResponseEntity<CommentForm> modifyComment(CommentForm commentForm, @PathVariable("id") Long id) {
//        Optional<Comment> comment = this.commentService.getComment(id);
//        if (comment.isPresent()) {
//            Comment c = comment.get();
//
//            commentForm.setContent(c.getContent());
//        }
//
//        return ResponseEntity.ok(commentForm);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<PhotoCommentModifyForm> modifyComment(@Valid @RequestBody PhotoModifyInfoDto photoModifyInfoDto, BindingResult bindingResult,
                                                                @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("잘못된 입력 값입니다.");
        }
        Optional<Comment> comment = this.photoCommentService.getComment(id);
        if (comment.isPresent()) {
            Comment c = comment.get();

            if (!c.getPassword().equals(photoModifyInfoDto.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
            }

            c = this.photoCommentService.modify(c, photoModifyInfoDto.getContent());

            PhotoCommentModifyForm photoCommentModifyForm = new PhotoCommentModifyForm(photoModifyInfoDto.getContent());
            return ResponseEntity.ok(photoCommentModifyForm);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "요청하신 데이터를 찾을 수 없습니다.");
        }
    }

    // 대댓글 삭제 --> form-data로 받아야 한다.
    @DeleteMapping("/{id}")
    public ResponseEntity<PhotoSuccessDto> deleteComment(@PathVariable("id") Long id, @Valid PhotoDeleteInfoDto deleteInfoDto) {
        Optional<Comment> comment = this.photoCommentService.getComment(id);
        if (comment.isPresent()) {
            Comment c = comment.get();

            if (!c.getPassword().equals(deleteInfoDto.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
            }

            PhotoSuccessDto photoSuccessDto = new PhotoSuccessDto(this.photoCommentService.delete(c));
            return ResponseEntity.ok(photoSuccessDto);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "요청하신 데이터를 찾을 수 없습니다.");
        }
    }

}
