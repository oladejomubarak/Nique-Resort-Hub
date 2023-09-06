package oladejo.mubarak.NiqueResortHub.config.email;


import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmailForBooking(String receiverEmail, String message) throws MessagingException;
    void sendEmailForPayment(String receiverEmail, String message) throws MessagingException;
    void sendEmailForBookingCancellation(String receiverEmail, String name, String bookingId) throws MessagingException;

}
