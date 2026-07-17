package com.unischedule.unischedule.repository;

import com.unischedule.unischedule.model.TimetableEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TimetableEntryRepository extends JpaRepository<TimetableEntry, Long> {
    List<TimetableEntry> findByStudentIdOrderByDayOfWeekAscStartTimeAsc(Long studentId);
}