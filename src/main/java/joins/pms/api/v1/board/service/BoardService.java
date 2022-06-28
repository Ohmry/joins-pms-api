package joins.pms.api.v1.board.service;

import joins.pms.api.v1.board.domain.Board;
import joins.pms.api.v1.board.domain.BoardInfo;
import joins.pms.api.v1.board.model.BoardCreateRequest;
import joins.pms.api.v1.board.model.BoardUpdateRequest;
import joins.pms.api.v1.board.repository.BoardRepository;
import joins.pms.api.v1.exception.DomainNotFoundException;
import joins.pms.core.domain.RowStatus;
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

    public BoardInfo getBoard(Long boardId) {
        Board board = boardRepository.findByIdAndRowStatus(boardId, RowStatus.NORMAL)
                .orElseThrow(DomainNotFoundException::new);
        return BoardInfo.valueOf(board);
    }

    public List<BoardInfo> getBoardList(int pageNo, int recordCount) {
        return boardRepository.findAllByRowStatus(RowStatus.NORMAL, PageRequest.of(pageNo, recordCount))
                .stream()
                .map(BoardInfo::valueOf)
                .collect(Collectors.toList());
    }

    public Long createBoard(BoardCreateRequest request) {
        Board board = new Board(request.title, request.description);
        return boardRepository.save(board).getId();
    }

    public Long updateBoard(BoardUpdateRequest request) {
        Board board = boardRepository.findByIdAndRowStatus(request.id, RowStatus.NORMAL)
                .orElseThrow(DomainNotFoundException::new);
        board.update(Board.Field.title, request.title);
        board.update(Board.Field.description, request.description);
        return boardRepository.save(board).getId();
    }

    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findByIdAndRowStatus(boardId, RowStatus.NORMAL)
                .orElseThrow(DomainNotFoundException::new);
        board.update(Board.Field.rowStatus, RowStatus.DELETED);
        boardRepository.save(board);
    }
}
