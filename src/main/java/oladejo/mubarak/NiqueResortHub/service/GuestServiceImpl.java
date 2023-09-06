package oladejo.mubarak.NiqueResortHub.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.NiqueResortHub.config.email.EmailServiceImpl;
import oladejo.mubarak.NiqueResortHub.data.model.*;
import oladejo.mubarak.NiqueResortHub.data.repository.BookingRepository;
import oladejo.mubarak.NiqueResortHub.data.repository.CanCelledBookingRepo;
import oladejo.mubarak.NiqueResortHub.dtos.request.BookingDto;
import oladejo.mubarak.NiqueResortHub.exception.NiqueResortHubException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService{
    private final EmailServiceImpl emailService;
    private final BookingRepository bookingRepository;
   private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final RoomServiceImpl roomService;
    private final CanCelledBookingRepo canCelledBookingRepo;
    @Override
    public Booking bookRoom(Long roomId, BookingDto bookingDto) throws MessagingException {
        Room foundRoom = roomService.getRoomById(roomId);
        LocalDate checkInDate = LocalDate.parse(bookingDto.getCheckinDate(), dateFormatter);

        for (int i = 0; i < bookingDto.getTelephoneNumber().length(); i++) {
            if(!Character.isDigit(bookingDto.getTelephoneNumber().charAt(i))){
                throw new NiqueResortHubException("Phone numbers can only be digits");
            }

        }
        if(checkInDate.isBefore(LocalDate.now())){
            throw new NiqueResortHubException("You can't choose past date for booking");
        }
        Booking booking = new Booking();
        booking.setEmailAddress(bookingDto.getEmailAddress());
        booking.setFirstName(bookingDto.getFirstName());
        booking.setLastName(bookingDto.getLastName());
        booking.setTelephoneNumber(bookingDto.getTelephoneNumber());
        booking.setRoomNumber(foundRoom.getRoomNumber());
        booking.setPaymentStatus(PaymentStatus.PENDING);
        booking.setCheckinDate(checkInDate);
        booking.setCheckoutDate(checkInDate
                .plusDays(bookingDto.getNumberOfNightsToBeSent()));
        booking.setTotalPrice(foundRoom.getRoomPrice().multiply(BigDecimal.valueOf(bookingDto.getNumberOfNightsToBeSent())));
        emailService.sendEmailForBooking(bookingDto.getEmailAddress(), buildBookingReservationEmail(
                bookingDto.getFirstName(),
                booking.getId().toString()));
        return bookingRepository.save(booking);
    }

    @Override
    public Booking findBooking(Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(()-> new NiqueResortHubException("booking not found"));
    }

    @Override
    public Booking extendStay(Long bookingId, int numberOfDays) throws MessagingException {
        Booking foundBooking = findBooking(bookingId);
        Room room = roomService.getRoomByRoomNumber(foundBooking.getRoomNumber());
        foundBooking.setExtendStayDays(numberOfDays);
        foundBooking.setExtendStayPaymentStatus(PaymentStatus.PENDING);
        foundBooking.setExtendStayPrice(room.getRoomPrice().multiply(BigDecimal.valueOf(numberOfDays)));
        //foundBooking.setCheckoutDate(foundBooking.getCheckoutDate().plusDays(numberOfDays));
        emailService.sendEmailForBooking(foundBooking.getEmailAddress(), buildExtendStayEmail(foundBooking.getFirstName()));
        return bookingRepository.save(foundBooking);
    }

    @Override
    public void cancelBooking(Long bookingId) throws MessagingException {
        Booking foundBooking = findBooking(bookingId);
        CancelledBooking cancelledBooking = new CancelledBooking();
        cancelledBooking.setBooking(foundBooking);
        canCelledBookingRepo.save(cancelledBooking);
        emailService.sendEmailForBookingCancellation(foundBooking.getEmailAddress(),
                foundBooking.getFirstName(),
                foundBooking.getId().toString());
        bookingRepository.delete(foundBooking);
    }

    @Override
    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    private String buildBookingReservationEmail(String firstname, String bookingId){
        return "Hello" + " " + firstname + ","
                + "<p>Thank you for making a reservation with us!!!<p/>"
                + "<p>Your room reservation request has been received. "
                + "Here is the bookingId: <p/>" + bookingId
                + "<p>Kindly keep this carefully as it will be required for payment, "
                + "also if you want to checkin at the reception, if you want to cancel your reservation or extend your stay";
    }

    private String buildExtendStayEmail(String firstname){
        return "Hello" + " " + firstname + ","
                + "<p>Your request for extending your stay with us has been received. "
                + "<p>Kindly make your payment in order to complete your request";
    }
}
