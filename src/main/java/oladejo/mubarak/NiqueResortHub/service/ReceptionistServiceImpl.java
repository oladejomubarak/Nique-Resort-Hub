package oladejo.mubarak.NiqueResortHub.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.NiqueResortHub.data.model.CheckIn;
import oladejo.mubarak.NiqueResortHub.data.model.PaymentStatus;
import oladejo.mubarak.NiqueResortHub.data.model.RoomStatus;
import oladejo.mubarak.NiqueResortHub.data.repository.CheckInRepository;
import oladejo.mubarak.NiqueResortHub.dtos.request.CheckInDto;
import oladejo.mubarak.NiqueResortHub.exception.NiqueResortHubException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ReceptionistServiceImpl implements ReceptionistService{
    private final CheckInRepository checkInRepo;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final GuestServiceImpl guestService;
    private final RoomServiceImpl roomService;


    @Override
    public String checkGuestIn(Long bookingId) {
        var foundBooking = guestService.findBooking(bookingId);
        var foundRoom = roomService.getRoomByRoomNumber(foundBooking.getRoomNumber());
//        LocalDate checkInDate = LocalDate.parse(checkInDto.getCheckInDate(), dateFormatter);
//        LocalTime checkInTime = LocalTime.parse(checkInDto.getCheckInTime(), timeFormatter);
        LocalDate checkInDate = LocalDate.parse(LocalDate.now().toString(), dateFormatter);
        LocalTime checkInTime = LocalTime.parse(LocalTime.now().toString(), timeFormatter);
        CheckIn checkIn = new CheckIn(
                checkInDate,
                checkInTime,
                foundBooking.getEmailAddress(),
                foundBooking.getFirstName(),
                foundBooking.getLastName(),
                foundBooking.getTelephoneNumber(),
                foundBooking.getRoomNumber()
        );
//        if(foundRoom.getRoomStatus().equals(RoomStatus.BOOKED)) throw new NiqueResortHubException("room with " +
//                ""+foundBooking.getRoomNumber()+" is not available for check-in");
        if(!foundBooking.getPaymentStatus().equals(PaymentStatus.SUCCESSFUL)){
            throw new NiqueResortHubException("payment has not been made for this booking yet, pls make payment");
        } else {
            foundRoom.setRoomStatus(RoomStatus.BOOKED);
            roomService.saveRoom(foundRoom);
        }
        checkInRepo.save(checkIn);
        return "Guest is successfully checked in";
    }


//    public CheckIn updateCheckIn(Long checkInId, CheckInDto checkInDto) {
//        return null;
//    }

    @Override
    public CheckIn findCheckIn(Long checkInId) {
        return null;
    }

    @Override
    public List<CheckIn> findCheckInByDate(String date) {
        LocalDate searchedDate = LocalDate.parse(date, dateFormatter);
        return checkInRepo.findByCheckInDate(searchedDate);

    }

    @Override
    public void deleteCheckIn(Long checkInId) {

    }
}
