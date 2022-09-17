package com.example.together.PhotoEntity.photocomment;

import com.example.together.PhotoEntity.photoanswer.PhotoAnswer;
import com.example.together.PhotoEntity.photoquestion.PhotoQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<PhotoComment, Long> {

    Page<PhotoComment> findAllByPhotoQuestion(PhotoQuestion photoQuestion, Pageable pageable);

    Page<PhotoComment> findAllByPhotoAnswer(PhotoAnswer question, Pageable pageable);

}
