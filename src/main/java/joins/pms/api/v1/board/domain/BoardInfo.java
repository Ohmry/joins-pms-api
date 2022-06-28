package joins.pms.api.v1.board.domain;

public class BoardInfo {
    public final Long id;
    public final String title;
    public final String description;

    protected BoardInfo(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public static BoardInfo valueOf(Board board) {
        return new BoardInfo(board.getId(), board.getTitle(), board.getDescription());
    }
}
