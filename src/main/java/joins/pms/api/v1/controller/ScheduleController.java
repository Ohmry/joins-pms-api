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
    public ApiResponse getAllSchedule () {
        List<ScheduleVo> list = scheduleService.findAll();
        return new ApiResponse(HttpStatus.OK, list);
    }

    @GetMapping("/schedule/{scheduleId}")
    public ApiResponse getSchedule (@PathVariable Long scheduleId) {
        ScheduleVo scheduleVo = scheduleService.findById(scheduleId);
        return new ApiResponse(HttpStatus.OK, scheduleVo);
    }

    @PostMapping("/schedule")
    public ApiResponse saveSchedule (@RequestBody ScheduleDto scheduleDto) {
        return new ApiResponse(HttpStatus.OK, scheduleService.save(scheduleDto));
    }
}
