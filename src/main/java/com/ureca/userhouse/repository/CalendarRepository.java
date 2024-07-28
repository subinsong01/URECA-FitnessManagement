package com.ureca.userhouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ureca.userhouse.entity.Calendar;
import com.ureca.userhouse.entity.Manager;

public interface CalendarRepository extends JpaRepository<Calendar, Long>{
	List<Calendar> findByManager(Manager manager);
}
