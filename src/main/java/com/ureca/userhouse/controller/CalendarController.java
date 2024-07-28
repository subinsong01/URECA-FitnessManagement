package com.ureca.userhouse.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ureca.userhouse.entity.Calendar;
import com.ureca.userhouse.entity.Manager;
import com.ureca.userhouse.repository.CalendarRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/events")
public class CalendarController {

    @Autowired
    private CalendarRepository calendarRepo;

    @GetMapping
    public List<Calendar> getAllEvents(HttpSession session) {
        Manager manager = (Manager) session.getAttribute("member");
        if (manager != null) {
            return calendarRepo.findByManager(manager);
        } else {
            return List.of(); // No events if not logged in
        }
    }

    @PostMapping
    public Calendar createEvent(@RequestBody Calendar calendar, HttpSession session) {
        Manager manager = (Manager) session.getAttribute("member");
        if (manager != null) {
            calendar.setManager(manager);
            return calendarRepo.save(calendar);
        } else {
            throw new IllegalStateException("You need to be logged in to create an event");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        Optional<Calendar> event = calendarRepo.findById(id);
        event.ifPresent(calendarRepo::delete);
    }
}
