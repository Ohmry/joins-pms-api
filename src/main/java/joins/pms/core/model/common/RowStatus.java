package joins.pms.core.model.common;

import joins.pms.core.model.common.converter.IEnumConverter;

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
