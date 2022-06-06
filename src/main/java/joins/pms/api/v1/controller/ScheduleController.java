package joins.pms.api.v1.controller;

import joins.pms.api.v1.model.dto.ScheduleDto;
import joins.pms.api.v1.service.ScheduleService;
import joins.pms.core.api.ApiResponse;
import joins.pms.core.api.ApiStatus;
import joins.pms.core.model.code.RowStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final String API_NAME = "schedule";

    public ScheduleController (ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
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
        scheduleDto.setStatus(RowStatus.DELETED);
        scheduleService.save(scheduleDto);
        URI uri = new URI("/schedule/" + id);
        return ResponseEntity.noContent().build();
    }
}
