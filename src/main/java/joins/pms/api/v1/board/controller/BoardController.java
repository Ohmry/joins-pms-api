package joins.pms.api.v1.board.controller;

import joins.pms.api.http.ApiResponse;
import joins.pms.api.http.ApiStatus;
import joins.pms.api.v1.board.model.BoardInfo;
import joins.pms.api.v1.board.model.BoardCreateRequest;
import joins.pms.api.v1.board.model.BoardUpdateRequest;
import joins.pms.api.v1.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
    private final BoardService boardService;
    
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse> createBoard(@RequestBody BoardCreateRequest request) {
        request.validate();
        Long boardId = boardService.createBoard(request.title, request.description);
        BoardInfo boardInfo = boardService.getBoard(boardId);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .header("Location", "/api/v1/board/" + boardId)
            .body(new ApiResponse(ApiStatus.SUCCESS, boardInfo));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getBoard(@PathVariable Long id) {
        BoardInfo boardInfo = boardService.getBoard(id);
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
    
    @PutMapping
    public ResponseEntity<ApiResponse> updateBoard(@RequestBody BoardUpdateRequest request) {
        request.validate();
        Long boardId = boardService.updateBoard(request.id, request.title, request.description);
        BoardInfo boardInfo = boardService.getBoard(boardId);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .header("Location", "/api/v1/board/" + boardId)
            .body(new ApiResponse(ApiStatus.SUCCESS, boardInfo));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBoard(HttpServletRequest servletRequest, @PathVariable long id) {
        boardService.deleteBoard(id);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(new ApiResponse(ApiStatus.SUCCESS));
    }
    
}
