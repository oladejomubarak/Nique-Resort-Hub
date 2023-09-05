package oladejo.mubarak.NiqueResortHub.service;

import oladejo.mubarak.NiqueResortHub.data.model.Booking;
import oladejo.mubarak.NiqueResortHub.dtos.request.BookingDto;

public interface GuestService {
    Booking bookRoom(Long roomId, BookingDto bookingDto);
    Booking findBooking(Long bookingId);

    Booking extendStay(Long bookingId, int numberOfDays);
    void cancelBooking(Long bookingId);
}
