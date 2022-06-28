package joins.pms.api.v1.board.controller;

import joins.pms.api.v1.board.domain.BoardInfo;
import joins.pms.api.v1.board.model.BoardCreateRequest;
import joins.pms.api.v1.board.model.BoardUpdateRequest;
import joins.pms.api.v1.board.service.BoardService;
import joins.pms.core.http.ApiResponse;
import joins.pms.core.http.ApiStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ApiResponse> getBorad(@PathVariable Long boardId) {
        BoardInfo boardInfo = boardService.getBoard(boardId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse(ApiStatus.SUCCESS, boardInfo));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getBoardList(@RequestParam int pageNo, @RequestParam int recordCount) {
        List<BoardInfo> boardList = boardService.getBoardList(pageNo, recordCount);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse(ApiStatus.SUCCESS, boardList));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(@RequestBody BoardCreateRequest request) {
        request.validate();
        Long boardId = boardService.createBoard(request);
        BoardInfo boardInfo = boardService.getBoard(boardId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/api/v1/board/" + boardId)
                .body(new ApiResponse(ApiStatus.SUCCESS, boardInfo));
    }

    @PutMapping
    public ResponseEntity<ApiResponse> update(@RequestBody BoardUpdateRequest request) {
        request.validate();
        Long boardId = boardService.updateBoard(request);
        BoardInfo boardInfo = boardService.getBoard(boardId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/api/v1/board/" + boardId)
                .body(new ApiResponse(ApiStatus.SUCCESS, boardInfo));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse(ApiStatus.SUCCESS));
    }

}
