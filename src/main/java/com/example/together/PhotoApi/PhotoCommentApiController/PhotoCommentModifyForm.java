package com.example.together.PhotoApi.PhotoCommentApiController;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhotoCommentModifyForm {

    private String username;
    private String content;

    private String date;

    public PhotoCommentModifyForm(String content, String username , String date){
        this.content = content;
        this.username = username;
        this.date = date;
    }

    public PhotoCommentModifyForm(String content) {
        this.content = content;
    }

}
