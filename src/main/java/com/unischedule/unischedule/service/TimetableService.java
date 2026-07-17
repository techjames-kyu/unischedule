package com.unischedule.unischedule.service;

import com.unischedule.unischedule.model.TimetableEntry;
import com.unischedule.unischedule.repository.TimetableEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TimetableService {

    private final TimetableEntryRepository timetableEntryRepository;

    @Autowired
    public TimetableService(TimetableEntryRepository timetableEntryRepository) {
        this.timetableEntryRepository = timetableEntryRepository;
    }

    public List<TimetableEntry> getEntriesForStudent(Long studentId) {
        return timetableEntryRepository.findByStudentIdOrderByDayOfWeekAscStartTimeAsc(studentId);
    }

    public TimetableEntry saveEntry(TimetableEntry entry) {
        validateNoOverlap(entry);
        return timetableEntryRepository.save(entry);
    }

    public void deleteEntry(Long id) {
        timetableEntryRepository.deleteById(id);
    }

    // Business rule: prevent a student from having two overlapping classes on the same day
    private void validateNoOverlap(TimetableEntry newEntry) {
        List<TimetableEntry> existing = timetableEntryRepository
                .findByStudentIdOrderByDayOfWeekAscStartTimeAsc(newEntry.getStudent().getId());

        for (TimetableEntry entry : existing) {
            if (entry.getId() != null && entry.getId().equals(newEntry.getId())) {
                continue; // skip comparing to itself when editing
            }
            if (entry.getDayOfWeek() == newEntry.getDayOfWeek()) {
                boolean overlaps = newEntry.getStartTime().isBefore(entry.getEndTime())
                        && entry.getStartTime().isBefore(newEntry.getEndTime());
                if (overlaps) {
                    throw new IllegalArgumentException(
                        "This entry overlaps with an existing class: " + entry.getSubject()
                        + " (" + entry.getStartTime() + " - " + entry.getEndTime() + ")"
                    );
                }
            }
        }
    }
}