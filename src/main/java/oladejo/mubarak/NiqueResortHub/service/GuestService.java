package oladejo.mubarak.NiqueResortHub.service;

import jakarta.mail.MessagingException;
import oladejo.mubarak.NiqueResortHub.data.model.Booking;
import oladejo.mubarak.NiqueResortHub.data.model.Guest;
import oladejo.mubarak.NiqueResortHub.dtos.request.BookingDto;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;

public interface GuestService {
    Booking bookRoom(Long roomId, BookingDto bookingDto) throws MessagingException;
    Booking findBooking(Long bookingId);

    Booking extendStay(String bookingId, int numberOfDays) throws MessagingException;
    void cancelBooking(String generatedBookingId) throws MessagingException;
    void saveBooking(Booking booking);
    void changeRoomStatusToBooked();
    void changeRoomStatusToUnBooked();
    List<Booking> findAllBookings();

    List<Booking> findAllSuccessfulBookingByDate(String date);
    void sendMessageToAllCustomers() throws MessagingException, UnsupportedEncodingException;
    List<Guest> findAllCustomers();
    List<Booking> findBookingsByCheckinDate(LocalDate date);
    void sendEmailToAllCustomers() throws MessagingException, UnsupportedEncodingException;
    String generateBookingId();
    Booking findBookingByGeneratedBookingId(String generatedBookingId);
}
