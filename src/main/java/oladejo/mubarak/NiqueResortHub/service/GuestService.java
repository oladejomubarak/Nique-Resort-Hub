package oladejo.mubarak.NiqueResortHub.service;

import jakarta.mail.MessagingException;
import oladejo.mubarak.NiqueResortHub.data.model.Booking;
import oladejo.mubarak.NiqueResortHub.dtos.request.BookingDto;

public interface GuestService {
    Booking bookRoom(Long roomId, BookingDto bookingDto) throws MessagingException;
    Booking findBooking(Long bookingId);

    Booking extendStay(Long bookingId, int numberOfDays) throws MessagingException;
    void cancelBooking(Long bookingId) throws MessagingException;
}
