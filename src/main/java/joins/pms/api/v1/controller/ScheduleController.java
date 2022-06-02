package joins.pms.api.v1.controller;

import joins.pms.api.v1.dto.ScheduleDto;
import joins.pms.api.v1.service.ScheduleService;
import joins.pms.api.v1.vo.ScheduleVo;
import joins.pms.core.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController (ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/schedule")
    public ApiResponse findAll () {
        List<ScheduleVo> list = scheduleService.findAll();
        return new ApiResponse(HttpStatus.OK, list);
    }

    @GetMapping("/schedule/{id}")
    public ApiResponse find (@PathVariable Long id) {
        ScheduleVo scheduleVo = scheduleService.findById(id);
        return new ApiResponse(HttpStatus.OK, scheduleVo);
    }

    @PostMapping("/schedule")
    public ApiResponse save (@RequestBody ScheduleDto scheduleDto) {
        return new ApiResponse(HttpStatus.OK, scheduleService.save(scheduleDto));
    }

    @PutMapping("/schedule")
    public ApiResponse update (@RequestBody ScheduleDto scheduleDto) {
        return new ApiResponse(HttpStatus.OK, scheduleService.save(scheduleDto));
    }

    @DeleteMapping("/schedule/{id}")
    public ApiResponse delete (@PathVariable Long id) {
        scheduleService.delete(id);
        return new ApiResponse(HttpStatus.OK, null);
    }
}