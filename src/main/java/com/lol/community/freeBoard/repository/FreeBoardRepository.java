package com.lol.community.freeBoard.repository;

import com.lol.community.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardRepository extends JpaRepository<Board,Long> {


}
