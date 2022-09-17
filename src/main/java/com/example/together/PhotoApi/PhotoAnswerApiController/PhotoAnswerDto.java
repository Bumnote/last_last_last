package com.example.together.PhotoApi.PhotoAnswerApiController;

import com.example.together.PhotoEntity.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PhotoAnswerDto {

    private Long id;

    private String content;

    private String date;

    private String username;

    private List<Comment> commentList;


    public PhotoAnswerDto(Long id, String content, String createDate,
                          String username, List<Comment> commentList) {
        this.id = id;
        this.content = content;
        this.date = createDate;
        this.username = username;
        this.commentList = commentList;
    }
}
