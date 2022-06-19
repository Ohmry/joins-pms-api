package joins.pms.api.v1.tag.service;

import joins.pms.api.v1.tag.model.Tag;
import joins.pms.api.v1.tag.model.TagDto;
import joins.pms.api.v1.tag.repository.TagRepository;
import joins.pms.core.model.RowStatus;
import joins.pms.core.model.converter.ModelConverter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final ModelConverter modelConverter;

    public TagService (TagRepository tagRepository,
                       ModelConverter modelConverter) {
        this.tagRepository = tagRepository;
        this.modelConverter = modelConverter;
    }

    public TagDto save (TagDto tagDto) {
        Optional<Tag> found = tagRepository.findByName(tagDto.getName());
        if (found.isPresent()) {
            return modelConverter.convert(found.get(), TagDto.class);
        } else {
            Tag tag = modelConverter.convert(tagDto, Tag.class);
            tag = tagRepository.save(tag);
            return modelConverter.convert(tag, TagDto.class);
        }
    }
}
