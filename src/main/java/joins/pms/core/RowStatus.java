package joins.pms.core;

public enum RowStatus implements IEnumConverter<RowStatus> {
    NORMAL("A"),
    DELETED("D")
    ;
    private final String value;
    RowStatus (String value) {
        this.value = value;
    }
    @Override
    public String getValue() {
        return this.value;
    }
}
