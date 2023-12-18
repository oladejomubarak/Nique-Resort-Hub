package oladejo.mubarak.NiqueResortHub.data.repository;

import oladejo.mubarak.NiqueResortHub.data.model.Booking;
import oladejo.mubarak.NiqueResortHub.data.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCheckoutDateAndPaymentStatus(LocalDate today, PaymentStatus successfulPayment);
    List<Booking> findByBookingDateAndPaymentStatus(LocalDate bookingDate, PaymentStatus successfulPayment);
    List<Booking> findBookingsByCheckinDate(LocalDate date);
    Optional<Booking> findBookingByGeneratedBookingId(String generateBookingId);
}
