package joins.pms.api.v1.schedule.controller;

import joins.pms.api.v1.schedule.model.ScheduleDto;
import joins.pms.api.v1.schedule.service.ScheduleService;
import joins.pms.api.v1.tag.model.TagDto;
import joins.pms.api.v1.tag.service.TagService;
import joins.pms.core.api.ApiResponse;
import joins.pms.core.api.ApiStatus;
import joins.pms.core.model.RowStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final TagService tagService;
    private final String API_NAME = "schedule";

    public ScheduleController (ScheduleService scheduleService,
                               TagService tagService) {
        this.scheduleService = scheduleService;
        this.tagService = tagService;
    }

    @GetMapping("/" + API_NAME)
    public ResponseEntity<ApiResponse> findAll () {
        List<ScheduleDto> list = scheduleService.findAll();
        ApiResponse response = new ApiResponse(list.isEmpty() ? ApiStatus.DATA_IS_EMPTY : ApiStatus.SUCCESS, list);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/"+ API_NAME + "/{id}")
    public ResponseEntity<ApiResponse> find (@PathVariable Long id) {
        ScheduleDto scheduleDto = scheduleService.findById(id);
        ApiResponse response = new ApiResponse(scheduleDto == null ? ApiStatus.DATA_IS_EMPTY : ApiStatus.SUCCESS, scheduleDto);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/" + API_NAME)
    public ResponseEntity<ApiResponse> save (@RequestBody ScheduleDto scheduleDto) throws URISyntaxException {
        if (scheduleDto.getStatus() == null) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiResponse(ApiStatus.STATUS_MUST_BE_NOT_NUL));
        }

        Set<TagDto> tags = scheduleDto.getTags();
        tags = tags.stream().filter(tag -> tag.getId() == null)
                .map(tagService::save)
                .collect(Collectors.toSet());
        scheduleDto.setTags(tags);

        Long id = scheduleService.save(scheduleDto);
        URI uri = new URI("/schedule/" + id);
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/" + API_NAME)
    public ResponseEntity<ApiResponse> update (@RequestBody ScheduleDto scheduleDto) throws URISyntaxException {
        Long id = scheduleService.save(scheduleDto);
        URI uri = new URI("/schedule/" + id);
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/" + API_NAME + "/{id}")
    public ResponseEntity<ApiResponse> delete (@PathVariable Long id) throws URISyntaxException {
        ScheduleDto scheduleDto = scheduleService.findById(id);
        scheduleDto.setRowStatus(RowStatus.DELETED);
        scheduleService.save(scheduleDto);
        URI uri = new URI("/schedule/" + id);
        return ResponseEntity.noContent().build();
    }
}
