package oladejo.mubarak.NiqueResortHub.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.NiqueResortHub.config.email.EmailServiceImpl;
import oladejo.mubarak.NiqueResortHub.data.model.*;
import oladejo.mubarak.NiqueResortHub.data.repository.BookingIdGeneratorRepo;
import oladejo.mubarak.NiqueResortHub.data.repository.BookingRepository;
import oladejo.mubarak.NiqueResortHub.data.repository.CanCelledBookingRepo;
import oladejo.mubarak.NiqueResortHub.data.repository.GuestRepository;
import oladejo.mubarak.NiqueResortHub.dtos.request.BookingDto;
import oladejo.mubarak.NiqueResortHub.exception.NiqueResortHubException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService{
    private final GuestRepository guestRepository;
    private final EmailServiceImpl emailService;
    private final BookingRepository bookingRepository;
   private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final RoomServiceImpl roomService;
    private final CanCelledBookingRepo canCelledBookingRepo;
    private final BookingIdGeneratorRepo bookingIdGeneratorRepo;
    private final JavaMailSender javaMailSender;
    @Override
    public Booking bookRoom(Long roomId, BookingDto bookingDto) throws MessagingException {
        Room foundRoom = roomService.getRoomById(roomId);
        String generatedBookingId = generateBookingId();
        BookingIdGenerator bookingIdGenerator = new BookingIdGenerator();
        bookingIdGenerator.setGeneratedBookingId(generatedBookingId);
        bookingIdGeneratorRepo.save(bookingIdGenerator);

        boolean existByEmail = guestRepository.existsByEmailIgnoreCase(bookingDto.getEmailAddress());
        if(!existByEmail){
            Guest guest = new Guest();
            guest.setEmail(bookingDto.getEmailAddress());
            guest.setFirstName(bookingDto.getFirstName());
            guest.setLastName(bookingDto.getLastName());
            guest.setTelephoneNumber(bookingDto.getTelephoneNumber());
            guestRepository.save(guest);
        }

        LocalDate checkInDate = LocalDate.parse(bookingDto.getCheckinDate(), dateFormatter);

        validatePhoneNumber(bookingDto.getTelephoneNumber());
        if(checkInDate.isBefore(LocalDate.now())){
            throw new NiqueResortHubException("You can't choose past date for booking");
        }
        if(Objects.equals(checkIfCheckInDateExists(checkInDate,foundRoom.getRoomNumber()), true)
                && foundRoom.getRoomStatus().equals(RoomStatus.BOOKED)){
            throw new NiqueResortHubException("The room has been booked");

        }

        Booking booking = new Booking();
        booking.setGeneratedBookingId(bookingIdGenerator.getGeneratedBookingId());
        booking.setEmailAddress(bookingDto.getEmailAddress());
        booking.setFirstName(bookingDto.getFirstName());
        booking.setLastName(bookingDto.getLastName());
        booking.setTelephoneNumber(bookingDto.getTelephoneNumber());
        booking.setRoomNumber(foundRoom.getRoomNumber());
        booking.setPaymentStatus(PaymentStatus.PENDING);
        booking.setBookingDate(LocalDate.now());
        booking.setCheckinDate(checkInDate);
        booking.setCheckoutDate(checkInDate
                .plusDays(bookingDto.getNumberOfNightsToBeSpent()));
        booking.setTotalPrice(foundRoom.getRoomPrice().multiply(BigDecimal.valueOf(bookingDto.getNumberOfNightsToBeSpent())));
        bookingRepository.save(booking);
        emailService.sendEmailForBooking(bookingDto.getEmailAddress(), buildBookingReservationEmail(
                bookingDto.getFirstName(),
                booking.getGeneratedBookingId()));
        return booking;
    }

    private void validatePhoneNumber(String phoneNumber) {
        for (int i = 0; i < phoneNumber.length(); i++) {
            if(!Character.isDigit(phoneNumber.charAt(i))){
                throw new NiqueResortHubException("Phone number can only be digits");
            }

        }
    }

    @Override
    public Booking findBooking(Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(()-> new NiqueResortHubException("booking not found"));
    }

    @Override
    public Booking extendStay(String bookingId, int numberOfDays) throws MessagingException {
        //Booking foundBooking = findBooking(bookingId);
        Booking foundBooking = findBookingByGeneratedBookingId(bookingId);
        Room room = roomService.getRoomByRoomNumber(foundBooking.getRoomNumber());
        foundBooking.setExtendStayDays(numberOfDays);
        foundBooking.setExtendStayPaymentStatus(PaymentStatus.PENDING);
        foundBooking.setExtendStayPrice(room.getRoomPrice().multiply(BigDecimal.valueOf(numberOfDays)));
        //foundBooking.setCheckoutDate(foundBooking.getCheckoutDate().plusDays(numberOfDays));
        emailService.sendEmailForBooking(foundBooking.getEmailAddress(), buildExtendStayEmail(foundBooking.getFirstName()));
        return bookingRepository.save(foundBooking);
    }

    @Override
    public void cancelBooking(String generatedBookingId) throws MessagingException {
        Booking foundBooking = findBookingByGeneratedBookingId(generatedBookingId);
        CancelledBooking cancelledBooking = new CancelledBooking();
        cancelledBooking.setBooking(foundBooking);
        canCelledBookingRepo.save(cancelledBooking);
        emailService.sendEmailForBookingCancellation(foundBooking.getEmailAddress(),
                foundBooking.getFirstName(),
                foundBooking.getGeneratedBookingId());
        bookingRepository.delete(foundBooking);
    }

    @Override
    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public void changeRoomStatusToBooked() {
        findAllBookings().forEach(booking -> {
            if(booking.getCheckinDate().equals(LocalDate.now()) &&
                    booking.getPaymentStatus().equals(PaymentStatus.SUCCESSFUL)){
                Room foundRoom = roomService.getRoomByRoomNumber(booking.getRoomNumber());
                foundRoom.setRoomStatus(RoomStatus.BOOKED);
                roomService.saveRoom(foundRoom);
            }
        });
    }

    @Override
    public void changeRoomStatusToUnBooked() {
        findAllBookings().forEach(booking -> {
            if(booking.getCheckoutDate().equals(LocalDate.now()) &&
                    booking.getPaymentStatus().equals(PaymentStatus.SUCCESSFUL)){
                Room foundRoom = roomService.getRoomByRoomNumber(booking.getRoomNumber());
                foundRoom.setRoomStatus(RoomStatus.UNBOOKED);
                roomService.saveRoom(foundRoom);
            }
        });
    }

    @Override
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }
    @Override
    public List<Booking> findAllSuccessfulBookingByDate(String date) {
        LocalDate bookingDate = LocalDate.parse(date, dateFormatter);
        return bookingRepository.findByBookingDateAndPaymentStatus(bookingDate, PaymentStatus.SUCCESSFUL);
    }

    @Override
    public void sendMessageToAllCustomers() throws MessagingException, UnsupportedEncodingException {
        sendEmailToAllCustomers();
    }

    @Override
    public List<Guest> findAllCustomers() {
        return guestRepository.findAll();
    }

    @Override
    public List<Booking> findBookingsByCheckinDate(LocalDate date) {
        return bookingRepository.findBookingsByCheckinDate(date);
    }
    private boolean checkIfCheckInDateExists(LocalDate date, String roomNumber){
     boolean isDateBooked = false;
        for (Booking booking: findBookingsByCheckinDate(date)) {
            if(booking.getCheckinDate().equals(date) && booking.getRoomNumber().equals(roomNumber)) {
                isDateBooked = true;
                break;
            }
        }
        return isDateBooked;
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

    @Override
    public void sendEmailToAllCustomers() throws MessagingException, UnsupportedEncodingException {
        List<Guest> allGuests = findAllCustomers();
        for (Guest guest: allGuests) {
            MimeMessage message =javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom("oladejomubarakade@gmail.com", "Nique Resort Hub");
            messageHelper.setTo(guest.getEmail());
            String subject = "Booking Cancellation";
            String content = "Dear" + " " + guest.getFirstName() + ","
                    + "<p>We are pleased to inform you that our pool service is now working properly<p/>";
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            javaMailSender.send(message);

        }

    }

    @Override
    public String generateBookingId() {
       // String bookingId ="";
       String bookingId = UUID.randomUUID().toString();

        boolean existBookingIdGenerator = bookingIdGeneratorRepo.existsByGeneratedBookingId(bookingId);
        if(existBookingIdGenerator){
            bookingId = generateBookingId();
        }
        return bookingId;
    }

    @Override
    public Booking findBookingByGeneratedBookingId(String generatedBookingId) {

        return bookingRepository.findBookingByGeneratedBookingId(generatedBookingId).orElseThrow(()-> new NiqueResortHubException("" +
                "Booking not found"));
    }
}
