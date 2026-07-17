package com.unischedule.unischedule.service;

import com.unischedule.unischedule.model.Reminder;
import com.unischedule.unischedule.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmailReminderService {

    private final ReminderRepository reminderRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public EmailReminderService(ReminderRepository reminderRepository, JavaMailSender mailSender) {
        this.reminderRepository = reminderRepository;
        this.mailSender = mailSender;
    }

    // Runs every 5 minutes (300000 milliseconds) to check for due reminders
    @Scheduled(fixedRate = 300000)
    public void sendDueReminders() {
        List<Reminder> dueReminders = reminderRepository
                .findByCompletedFalseAndNotifiedFalseAndDueDateTimeLessThanEqual(LocalDateTime.now());

        for (Reminder reminder : dueReminders) {
            String studentEmail = reminder.getStudent().getEmail();
            if (studentEmail == null || studentEmail.isBlank()) {
                continue; // skip if no email on file
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(studentEmail);
            message.setSubject("UniSchedule Reminder: " + reminder.getMessage());
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a");
           String formattedDueDate = reminder.getDueDateTime().format(formatter);
           message.setText(
            "Hi " + reminder.getStudent().getName() + ",\n\n"
            + "This is a reminder for: " + reminder.getMessage() + "\n"
            + "Due: " + formattedDueDate + "\n\n"
            + "— UniSchedule"
           );
           



            mailSender.send(message);
            reminder.setNotified(true);
            reminderRepository.save(reminder);
        }
    }
}