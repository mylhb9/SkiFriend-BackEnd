package com.ppjt10.skifriend.entity;

import com.ppjt10.skifriend.dto.CommentDto;
import com.ppjt10.skifriend.time.TimeConversion;
import com.ppjt10.skifriend.time.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private FreePost freePost;

    @Column(nullable = false)
    private String content;

    public Comment(User user, FreePost freePost, String content){
        this.user = user;
        this.freePost = freePost;
        this.content = content;
    }

    public void update(CommentDto.RequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
