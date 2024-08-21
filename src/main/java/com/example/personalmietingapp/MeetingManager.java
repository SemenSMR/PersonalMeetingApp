package com.example.personalmietingapp;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingManager {
    private List<Meeting> meetings = new ArrayList<>();
    private static final String FILE_PATH = "meeting.dat";

    public MeetingManager() {
        loadMeetingsFromFile();
    }

    // Добавить встречу
    public void addMeeting(Meeting meeting) throws Exception {
        if (isOverlapping(meeting)) {
            throw new Exception("Встреча уже назначена на это время");

        }
        meetings.add(meeting);
        saveMeetingToFile();
    }

    // Обновить встречу
    public void updateMeeting(Meeting oldMeeting, Meeting newMeeting) throws Exception {
        if (isOverlapping(newMeeting)) {
            throw new Exception("Встреча уже назначена на это время");
        }
        meetings.remove(oldMeeting);
        meetings.add(newMeeting);
        saveMeetingToFile();
    }

    // Удалить встречу
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
        saveMeetingToFile();
    }

    // Показать все встречи на переданную дату
    public List<Meeting> getMeetingsByDate(LocalDateTime date) {
        List<Meeting> result = new ArrayList<>();
        for (Meeting meeting : meetings) {
            if (meeting.getStartTime().toLocalDate().equals(date.toLocalDate())) {
                result.add(meeting);
            }
        }
        return result;
    }

    // Сохрнанение встречи в файл
    private void saveMeetingToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(meetings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // Проверка на пересечение встреч
    private boolean isOverlapping(Meeting newMeeting) {
        for (Meeting existing : meetings) {
            if (newMeeting.getStartTime().isBefore(existing.getEndTime()) &&
                    newMeeting.getEndTime().isAfter(existing.getStartTime())) {
                return true;
            }
        }
        return false;

    }

    // Экспорт списка встреч запланированных на переданную дату
    public void exportMeetingsToFile(LocalDateTime date) throws Exception {
        List<Meeting> dallyMeeting = getMeetingsByDate(date);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("schedule_" + date.toLocalDate() + ".txt"))) {
            for (Meeting meeting : dallyMeeting) {
                writer.write(meeting.toString());
                writer.newLine();
            }
        }
    }

    // Загрузка список встреч из файла
    @SuppressWarnings("unchecked")
    private void loadMeetingsFromFile() {
        File file = new File(FILE_PATH);
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                meetings = (List<Meeting>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            // Если файл не существует или пустой, инициализируем пустой список
            meetings = new ArrayList<>();
            System.out.println("Файл не существует или пуст. Создан пустой список встреч.");
        }

    }
}
