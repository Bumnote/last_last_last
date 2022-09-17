package com.example.together.PhotoService;

import com.example.together.PhotoEntity.photoanswer.AnswerRepository;
import com.example.together.PhotoEntity.photoanswer.PhotoAnswer;
import com.example.together.PhotoEntity.photoquestion.PhotoQuestion;
import com.example.together.PhotoException.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhotoAnswerService {

    private final AnswerRepository answerRepository;
    private final PhotoQuestionService photoQuestionService;

    // 답변 생성
    public void create(PhotoQuestion photoQuestion, String content, String username, String password) {
        PhotoAnswer photoAnswer = new PhotoAnswer();
        photoAnswer.setContent(content);
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")); // 작성 시간 포멧팅
        photoAnswer.setDate(currentTime);
        photoAnswer.setPhotoQuestion(photoQuestion);
        photoAnswer.setUsername(username);
        photoAnswer.setPassword(password);
        this.answerRepository.save(photoAnswer);
    }

    // 답변 페이징 처리
    public Page<PhotoAnswer> getList(int page, Long id) {
        PhotoQuestion photoQuestion = photoQuestionService.getQuestion(id);
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("date"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.answerRepository.findAllByPhotoQuestion(photoQuestion, pageable);
    }

    // 아래 두 함수는 AnswerController 에서 필요한 답변조회와 답변수정 기능
    public PhotoAnswer getPhotoAnswer(Long id) {
        Optional<PhotoAnswer> photoAnswer = this.answerRepository.findById(id);
        if (photoAnswer.isPresent()) {
            return photoAnswer.get();
        } else {
            throw new DataNotFoundException("요청하신 데이터를 찾을 수 없습니다.");
        }
    }

    public void modify(PhotoAnswer photoAnswer, String content) {
        photoAnswer.setContent(content);
        this.answerRepository.save(photoAnswer);
    }

    public Boolean delete(PhotoAnswer photoAnswer) {
        this.answerRepository.delete(photoAnswer);;
        return true;
    }
}
