package joins.pms.api.v1.model;

import joins.pms.core.model.converter.IEnumConverter;

public enum Status implements IEnumConverter {
    READY("RDY"),
    PROCEED("PRD"),
    ABORT("ABT"),
    FINISHED("FND")
    ;

    private final String value;

    Status(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
