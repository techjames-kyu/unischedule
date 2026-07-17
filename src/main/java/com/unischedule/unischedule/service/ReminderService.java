package com.unischedule.unischedule.service;

import com.unischedule.unischedule.model.Reminder;
import com.unischedule.unischedule.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReminderService {

    private final ReminderRepository reminderRepository;

    @Autowired
    public ReminderService(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public List<Reminder> getActiveRemindersForStudent(Long studentId) {
        return reminderRepository.findByStudentIdAndCompletedFalseOrderByDueDateTimeAsc(studentId);
    }

    public Reminder saveReminder(Reminder reminder) {
        return reminderRepository.save(reminder);
    }

    public void markComplete(Long id) {
        reminderRepository.findById(id).ifPresent(reminder -> {
            reminder.setCompleted(true);
            reminderRepository.save(reminder);
        });
    }

    public void deleteReminder(Long id) {
        reminderRepository.deleteById(id);
    }
}