package oladejo.mubarak.NiqueResortHub.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.NiqueResortHub.data.model.CheckIn;
import oladejo.mubarak.NiqueResortHub.dtos.request.CheckInDto;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class ReceptionistServiceImpl implements ReceptionistService{
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
    public void deleteCheckIn(Long checkInId) {

    }
}
