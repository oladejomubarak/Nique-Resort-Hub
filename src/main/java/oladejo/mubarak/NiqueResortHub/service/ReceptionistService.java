package oladejo.mubarak.NiqueResortHub.service;

import oladejo.mubarak.NiqueResortHub.data.model.Booking;
import oladejo.mubarak.NiqueResortHub.data.model.CheckIn;
import oladejo.mubarak.NiqueResortHub.dtos.request.CheckInDto;

import java.util.List;

public interface ReceptionistService {
    String checkGuestIn(String generatedBookingId);
    //CheckIn updateCheckIn(Long checkInId, CheckInDto checkInDto);
    CheckIn findCheckIn(Long checkInId);
    List<CheckIn> findCheckInByDate(String checkInDate);
    void deleteCheckIn(Long checkInId);
    List<Booking> findAllBookingsWhenCheckoutDue();
}
