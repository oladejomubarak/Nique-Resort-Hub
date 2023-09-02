package oladejo.mubarak.NiqueResortHub.data.repository;

import oladejo.mubarak.NiqueResortHub.data.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
