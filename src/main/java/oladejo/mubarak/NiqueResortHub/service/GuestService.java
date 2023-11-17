package oladejo.mubarak.NiqueResortHub.service;

import jakarta.mail.MessagingException;
import oladejo.mubarak.NiqueResortHub.data.model.Booking;
import oladejo.mubarak.NiqueResortHub.dtos.request.BookingDto;

import java.util.List;

public interface GuestService {
    Booking bookRoom(Long roomId, BookingDto bookingDto) throws MessagingException;
    Booking findBooking(Long bookingId);

    Booking extendStay(Long bookingId, int numberOfDays) throws MessagingException;
    void cancelBooking(Long bookingId) throws MessagingException;
    void saveBooking(Booking booking);
    void changeRoomStatusToBooked();
    void changeRoomStatusToUnBooked();
    List<Booking> findAllBookings();

    List<Booking> findAllSuccessfulBookingByDate(String date);
    void sendMessageToAllCustomers();
}
