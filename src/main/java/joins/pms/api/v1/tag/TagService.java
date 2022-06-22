package joins.pms.api.v1.tag;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService (TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagDto save (TagDto tagDto) {
        return null;
    }
}
