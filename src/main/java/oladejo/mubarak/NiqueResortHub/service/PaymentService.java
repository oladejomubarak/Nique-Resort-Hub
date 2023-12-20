package oladejo.mubarak.NiqueResortHub.service;

import jakarta.mail.MessagingException;

import java.io.IOException;

//@FunctionalInterface
public interface PaymentService {
    String payForBookReservation(String generatedBookingId) throws IOException, MessagingException;
    String payForExtendingStay(String generatedBookingId) throws IOException, MessagingException;
}
