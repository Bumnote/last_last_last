package com.example.together.PhotoApi.PhotoAnswerApiController;

import com.example.together.PhotoConfigDto.PhotoDeleteInfoDto;
import com.example.together.PhotoConfigDto.PhotoModifyInfoDto;
import com.example.together.PhotoConfigDto.PhotoSuccessDto;
import com.example.together.PhotoController.answerController.PhotoAnswerForm;
import com.example.together.PhotoEntity.photoanswer.PhotoAnswer;
import com.example.together.PhotoEntity.photoquestion.PhotoQuestion;
import com.example.together.PhotoService.PhotoAnswerService;
import com.example.together.PhotoService.PhotoQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/photo/answers")
@RequiredArgsConstructor
public class PhotoAnswerApi {

    private final PhotoQuestionService photoQuestionService;
    private final PhotoAnswerService photoAnswerService;

    // 댓글 작성 --> 게시글의 id 로 접근
    @PostMapping("/{id}")
    public ResponseEntity<PhotoAnswerModifyForm> createAnswer(@PathVariable("id") Long id,
                                                              @Valid @RequestBody PhotoAnswerForm photoAnswerForm, BindingResult bindingResult) {

        PhotoQuestion photoQuestion = photoQuestionService.getQuestion(id);

        if (bindingResult.hasErrors()) {

            throw new IllegalArgumentException("잘못된 입력 값입니다.");
        }
        this.photoAnswerService.create(photoQuestion, photoAnswerForm.getContent(), photoAnswerForm.getUsername(), photoAnswerForm.getPassword());

        PhotoAnswerModifyForm photoAnswerApiForm = new PhotoAnswerModifyForm(photoAnswerForm.getContent());
        return ResponseEntity.ok(photoAnswerApiForm);
    }

    // 댓글 보기
//    @GetMapping("/modify/{id}")
//    public ResponseEntity<AnswerForm> answerModify(AnswerForm answerForm, @PathVariable("id") Long id) {
//
//        Answer answer = this.answerService.getAnswer(id);
//        answerForm.setContent(answer.getContent());
//        return ResponseEntity.ok(answerForm);
//    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<PhotoAnswerModifyForm> answerModify(@Valid @RequestBody PhotoModifyInfoDto photoModifyInfoDto, BindingResult bindingResult,
                                                              @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("잘못된 입력 값입니다.");
        }
        PhotoAnswer photoAnswer = this.photoAnswerService.getPhotoAnswer(id); // 댓글을 불러온다.

        if (!photoAnswer.getPassword().equals(photoModifyInfoDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        PhotoAnswer a = this.photoAnswerService.new_modify(photoAnswer, photoModifyInfoDto.getContent());

        // 댓글 수정 시 --> content, username, date 까지 포함
        PhotoAnswerModifyForm photoAnswerModifyForm = new PhotoAnswerModifyForm(a.getContent(), a.getUsername(), a.getDate());
        return ResponseEntity.ok(photoAnswerModifyForm);
    }

    // 댓글 삭제 --> form-data 로 보내야 함
    @DeleteMapping("/{id}")
    public ResponseEntity<PhotoSuccessDto> answerDelete(@Valid PhotoDeleteInfoDto photoDeleteInfoDto, @PathVariable("id") Long id) {
        PhotoAnswer photoAnswer = this.photoAnswerService.getPhotoAnswer(id);

        if (!photoAnswer.getPassword().equals(photoDeleteInfoDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        PhotoSuccessDto photoSuccessDto = new PhotoSuccessDto(this.photoAnswerService.delete(photoAnswer));
        return ResponseEntity.ok(photoSuccessDto);
    }

}
