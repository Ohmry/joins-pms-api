package joins.pms.api.v1.board.model;

import joins.pms.api.v1.board.domain.Board;
import joins.pms.core.annotations.ValueObject;

@ValueObject
public class BoardSimpleInfo {
    public final Long id;
    public final String title;
    public final String description;

    public BoardSimpleInfo(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public static BoardSimpleInfo valueOf(Board board) {
        return new BoardSimpleInfo(board.getId(), board.getTitle(), board.getDescription());
    }
}
