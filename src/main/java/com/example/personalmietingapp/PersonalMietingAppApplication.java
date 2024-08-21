package com.example.personalmietingapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.Month;

@SpringBootApplication
public class PersonalMietingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalMietingAppApplication.class, args);
    }
    @Bean
    public CommandLineRunner run() {
        return args -> {
            MeetingManager manager = new MeetingManager();
            MeetingNotifier notifier = new MeetingNotifier(manager);

            try {
                Meeting meeting1 = new Meeting("Team Meeting", LocalDateTime.of(2024, Month.AUGUST, 21, 10, 0),
                        LocalDateTime.of(2024, Month.AUGUST, 21, 11, 0), 15);
                manager.addMeeting(meeting1);

                Meeting meeting2 = new Meeting("Client Call", LocalDateTime.of(2024, Month.AUGUST, 21, 14, 0),
                        LocalDateTime.of(2024, Month.AUGUST, 21, 15, 0), 10);
                manager.addMeeting(meeting2);

                //  manager.exportMeetingsToFile(LocalDateTime.of(2024, Month.AUGUST, 21, 0, 0));

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
