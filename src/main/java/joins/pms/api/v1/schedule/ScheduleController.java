package joins.pms.api.v1.schedule;

import joins.pms.api.http.ApiResponse;
import joins.pms.api.http.ApiStatus;
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
        List<ScheduleInfo> list = scheduleService.findAll();
        ApiResponse response = new ApiResponse(list.isEmpty() ? ApiStatus.DATA_IS_EMPTY : ApiStatus.SUCCESS, list);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/"+ API_NAME + "/{id}")
    public ResponseEntity<ApiResponse> find (@PathVariable Long id) {
        ScheduleInfo scheduleInfo = scheduleService.findById(id);
        ApiResponse response = new ApiResponse(scheduleInfo == null ? ApiStatus.DATA_IS_EMPTY : ApiStatus.SUCCESS, scheduleInfo);
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

    @PutMapping("/" + API_NAME + "/{id}")
    public ResponseEntity<ApiResponse> update (@PathVariable Long id, @RequestBody ScheduleDto scheduleDto) throws URISyntaxException {
        id = scheduleService.update(id, scheduleDto);
        URI uri = new URI("/schedule/" + id);
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/" + API_NAME + "/{id}")
    public ResponseEntity<ApiResponse> delete (@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
