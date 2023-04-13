package com.billions.forty.auth.board.entity;

import com.billions.forty.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Board {
    private Long boardId;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
