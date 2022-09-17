package com.example.together.PhotoApi.PhotoAnswerApiController;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhotoAnswerModifyForm {

    private String username;
    private String content;
    private String date;

    public PhotoAnswerModifyForm(String content, String username, String date) {
        this.content = content;
        this.username = username;
        this.date = date;
    }

    public PhotoAnswerModifyForm(String content){
        this.content = content;
    }
}
