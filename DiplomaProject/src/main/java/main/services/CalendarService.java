package main.services;

import main.model.dto.CalendarDTO;
import main.repositories.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;

    public CalendarDTO getCalendar(String year) {
        List<Integer> years = calendarRepository.findAllYears(year);
        Map<String, Long> posts = calendarRepository.findAllPosts(year);

        return new CalendarDTO(years, posts);
    }
    }

