package joins.pms.core.entity;

import joins.pms.core.entity.converter.IEnumConverter;

public enum RowStatus implements IEnumConverter {
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
