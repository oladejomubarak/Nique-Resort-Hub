package oladejo.mubarak.NiqueResortHub.service;

import java.io.IOException;

//@FunctionalInterface
public interface PaymentService {
    String payForBookReservation(Long bookingId) throws IOException;
    String payForExtendingStay(Long bookingId) throws IOException;
}
