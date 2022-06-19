package joins.pms.api.v1.tag;

import lombok.Data;

@Data
public class TagDto {
    private Long id;
    private String name;

    public Tag toEntity () {
        return Tag.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }

    public TagDto (Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
