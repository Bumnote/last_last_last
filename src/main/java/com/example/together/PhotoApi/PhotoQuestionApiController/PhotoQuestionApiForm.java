package com.example.together.PhotoApi.PhotoQuestionApiController;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhotoQuestionApiForm {


    private String subject;

    private String content;

    private String username;

    private String filename;

    private String filepath;

    public PhotoQuestionApiForm(String subject, String content, String username, String filename, String filepath) {
        this.subject = subject;
        this.content = content;
        this.username = username;
        this.filename = filename;
        this.filepath = filepath;
    }

}
