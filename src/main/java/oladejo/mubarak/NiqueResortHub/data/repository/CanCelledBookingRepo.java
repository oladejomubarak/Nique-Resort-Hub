package oladejo.mubarak.NiqueResortHub.data.repository;

import oladejo.mubarak.NiqueResortHub.data.model.CancelledBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CanCelledBookingRepo extends JpaRepository<CancelledBooking, Long> {

}
