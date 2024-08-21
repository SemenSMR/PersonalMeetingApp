package com.example.personalmietingapp;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MeetingNotifier {
    private final MeetingManager meetingManager;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public MeetingNotifier(MeetingManager meetingManager) {
        this.meetingManager = meetingManager;
        startScheduler();
    }
    // Проверка на необходимость уведомлений
    private void startScheduler() {
        scheduler.scheduleAtFixedRate(this::checkAndNotify, 0, 1, TimeUnit.MINUTES);
    }

    // Получаем текущее время,  Получение встречи на текущий день , расчитывает время и отправляет пользователю
    private void checkAndNotify() {
        LocalDateTime now = LocalDateTime.now();
        for (Meeting meeting : meetingManager.getMeetingsByDate(now)) {
            LocalDateTime reminderTime = meeting.getStartTime().minusMinutes(meeting.getReminderMinutesBefore());
            if (now.isAfter(reminderTime) && now.isBefore(meeting.getStartTime())) {
                notifyUser(meeting);
            }
        }
    }
// уведомление о встрече
    private void notifyUser(Meeting meeting) {
        System.out.println("Reminder: " + meeting.getTitle() + " starts at " + meeting.getStartTime());
    }
}
