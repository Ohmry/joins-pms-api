package joins.pms.api.v1.board.service;

import joins.pms.api.domain.RowStatus;
import joins.pms.api.exception.DomainNotFoundException;
import joins.pms.api.v1.board.domain.Board;
import joins.pms.api.v1.board.model.BoardInfo;
import joins.pms.api.v1.board.repository.BoardRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    
    public Long createBoard(String title, String description) {
        Board board = Board.create(title, description);
        return boardRepository.save(board).getId();
    }
    
    public BoardInfo getBoard(Long id) {
        Board board = boardRepository.findByIdAndRowStatus(id, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(Board.class));
        return BoardInfo.valueOf(board);
    }
    
    public List<BoardInfo> getBoardList(int pageNo, int recordCount) {
        return boardRepository
            .findAllByRowStatus(RowStatus.NORMAL, PageRequest.of(pageNo, recordCount))
            .stream()
            .map(BoardInfo::valueOf)
            .collect(Collectors.toList());
    }
    
    public Long updateBoard(Long id, String title, String description) {
        Board board = boardRepository.findByIdAndRowStatus(id, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(Board.class));
        board.update(Board.Field.title, title);
        board.update(Board.Field.description, description);
        return boardRepository.save(board).getId();
    }
    
    public void deleteBoard(Long id) {
        Board board = boardRepository.findByIdAndRowStatus(id, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(Board.class));
        board.update(Board.Field.rowStatus, RowStatus.DELETED);
        boardRepository.save(board);
    }
}
