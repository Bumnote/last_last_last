package com.example.together.PhotoConfigDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoSuccessDto {

    private Boolean success;

    public PhotoSuccessDto(Boolean success) {
        this.success = success;
    }

}
