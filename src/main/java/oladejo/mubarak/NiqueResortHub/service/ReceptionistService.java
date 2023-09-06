package oladejo.mubarak.NiqueResortHub.service;

import oladejo.mubarak.NiqueResortHub.data.model.CheckIn;
import oladejo.mubarak.NiqueResortHub.dtos.request.CheckInDto;

public interface ReceptionistService {
    CheckIn checkGuestIn(Long bookingId, CheckInDto checkInDto);
    CheckIn updateCheckIn(Long checkInId, CheckInDto checkInDto);
    CheckIn findCheckIn(Long checkInId);
    void deleteCheckIn(Long checkInId);
}
