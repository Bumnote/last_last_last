package com.example.together.PhotoApi.PhotoCommentApiController;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhotoCommentCreateForm {

    private String username;

    private String content;

    public PhotoCommentCreateForm(String username, String content){
        this.username = username;
        this.content = content;
    }

}
