package com.example.together.PhotoEntity.answer;

import com.example.together.PhotoEntity.comment.Comment;
import com.example.together.PhotoEntity.photoquestion.PhotoQuestion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn
    private PhotoQuestion question;

    @CreatedDate
    private String date;

    private String username;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "answer", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

}
