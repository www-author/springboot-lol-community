package com.lol.community.freeBoard.service;

import com.lol.community.board.domain.Board;
import com.lol.community.freeBoard.dto.FreeBoardDTO;
import com.lol.community.freeBoard.repository.FreeBoardRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class FreeBoardServiceImpl implements FreeBoardService{

  private final FreeBoardRepository freeBoardRepository;

  @Override
  public FreeBoardDTO getArticle(Long articleId){
    Board board = freeBoardRepository.findById(articleId).orElseThrow();

    return FreeBoardDTO.builder()
        .id(board.getId())
        .title(board.getTitle())
        .content(board.getContent())
        .writerName(board.getUser().getName())
        .created_at(board.getCreatedAt())
        .updated_at(board.getUpdatedAt())
        .category(board.getCategory().getCategoryName())
        .build();
  }
  @Override
  public List<Board> getArticleList(){
    return freeBoardRepository.findAll();
  }

  @Override
  public Long write(FreeBoardDTO freeBoardDTO){
    log.info(freeBoardDTO);

    Board board = dtoToEntity(freeBoardDTO);

    freeBoardRepository.save(board);

    return board.getId();
  }

  @Override
  public Long modify(FreeBoardDTO freeBoardDTO){
    log.info(freeBoardDTO);
    Optional<Board> existCheck = freeBoardRepository.findById(freeBoardDTO.getId());
    if(existCheck.isPresent()){
      Board board = existCheck.get();

    }
  }

}
