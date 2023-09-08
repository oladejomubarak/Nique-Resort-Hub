package oladejo.mubarak.NiqueResortHub.data.repository;

import oladejo.mubarak.NiqueResortHub.data.model.Booking;
import oladejo.mubarak.NiqueResortHub.data.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCheckoutDateAndPaymentStatus(LocalDate today, PaymentStatus successfulPayment);
}
