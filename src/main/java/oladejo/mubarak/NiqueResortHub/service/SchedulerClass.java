package oladejo.mubarak.NiqueResortHub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerClass {
    private final GuestServiceImpl guestService;

    @Scheduled(cron = "0 0 0 * * *")
    public void changeRoomStatusToBooked(){
        guestService.changeRoomStatusToBooked();
        System.out.println("Changing room to booked scheduler called");

    }
    @Scheduled(cron = "0 0 12 * * *")
    public void changeRoomStatusToUnBooked(){
        guestService.changeRoomStatusToUnBooked();
        System.out.println("Changing room to unbooked scheduler called");
    }
}
