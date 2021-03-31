package main.controller;



import main.model.dto.CalendarDTO;
import main.services.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiCalendarController {

    @Autowired
    private CalendarService calendarService;

    @GetMapping("/api/calendar")
    public CalendarDTO getCalendar(
            @RequestParam(name="year", required = false) String year) {
        return calendarService.getCalendar(year);
    }
}
