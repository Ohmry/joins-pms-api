package joins.pms.api.v1.controller;

import joins.pms.api.v1.model.dto.ScheduleDto;
import joins.pms.api.v1.service.ScheduleService;
import joins.pms.core.api.ApiResponse;
import joins.pms.core.api.ApiStatus;
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

    public ScheduleController (ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/schedule")
    public ResponseEntity findAll () {
        List<ScheduleDto> list = scheduleService.findAll();
        ApiResponse response = new ApiResponse(list.isEmpty() ? ApiStatus.DATA_IS_EMPTY : ApiStatus.SUCCESS, list);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/schedule/{id}")
    public ResponseEntity find (@PathVariable Long id) {
        ScheduleDto scheduleDto = scheduleService.findById(id);
        ApiResponse response = new ApiResponse(scheduleDto == null ? ApiStatus.DATA_IS_EMPTY : ApiStatus.SUCCESS, scheduleDto);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/schedule")
    public ResponseEntity save (@RequestBody ScheduleDto scheduleDto) throws URISyntaxException {
        Long id = scheduleService.save(scheduleDto);
        URI uri = new URI("/schedule/" + id);
        return ResponseEntity.created(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @PutMapping("/schedule")
    public ResponseEntity update (@RequestBody ScheduleDto scheduleDto) throws URISyntaxException {
        Long id = scheduleService.save(scheduleDto);
        URI uri = new URI("/schedule/" + id);
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/schedule/{id}")
    public ResponseEntity delete (@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
