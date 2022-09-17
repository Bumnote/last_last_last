package com.example.together.PhotoApi.PhotoQuestionApiController;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhotoQuestionModifyForm {

    private String subject;
    private String content;

    private String username;

    private String date;
    private String filename;
    private String filepath;


    public PhotoQuestionModifyForm(String subject, String content, String filename, String filepath, String date, String username) {
        this.subject = subject;
        this.content = content;
        this.filename = filename;
        this.filepath = filepath;
        this.username = username;
        this.date = date;
    }

}