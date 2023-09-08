package oladejo.mubarak.NiqueResortHub.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.NiqueResortHub.data.model.CheckIn;
import oladejo.mubarak.NiqueResortHub.data.repository.CheckInRepository;
import oladejo.mubarak.NiqueResortHub.dtos.request.CheckInDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public CheckIn checkGuestIn(Long bookingId, CheckInDto checkInDto) {

        return null;
    }

    @Override
    public CheckIn updateCheckIn(Long checkInId, CheckInDto checkInDto) {
        return null;
    }

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
