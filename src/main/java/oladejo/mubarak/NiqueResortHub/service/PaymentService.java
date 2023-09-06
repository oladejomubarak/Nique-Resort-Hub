package oladejo.mubarak.NiqueResortHub.service;

import jakarta.mail.MessagingException;

import java.io.IOException;

//@FunctionalInterface
public interface PaymentService {
    String payForBookReservation(Long bookingId) throws IOException, MessagingException;
    String payForExtendingStay(Long bookingId) throws IOException, MessagingException;
}
