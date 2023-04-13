package com.billions.forty.auth.board.service;

import com.billions.forty.auth.board.entity.Board;
import com.billions.forty.auth.board.repository.BoardRepository;
import com.billions.forty.exception.BusinessLogicException;
import com.billions.forty.exception.ExceptionCode;
import com.billions.forty.user.entity.User;
import com.billions.forty.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public Board createBoard(Board board){
        return boardRepository.save(board);
    }

    public Board updateBoard(Board board){
        verifyUser(board.getUser().getUserId());

        Board findBoard = findVerifiedBoard(board.getBoardId());

        Optional.ofNullable(board.getTitle())
                .ifPresent(findBoard::setTitle);
        Optional.ofNullable(board.getContent())
                .ifPresent(findBoard::setContent);

        return boardRepository.save(findBoard);
    }


    public Board getBoard(long boardId){
        Board findBoard = findVerifiedBoard(boardId);

        return  findBoard;
    }

    public Page<Board> getBoards(int page, int size){
        return boardRepository.findAll(PageRequest.of(page, size,
                Sort.by("boardId").descending()));
    }

    public Board findVerifiedBoard(long boardId){
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board findBoard = optionalBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return findBoard;
    }

    public void verifyUser(long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }

}
