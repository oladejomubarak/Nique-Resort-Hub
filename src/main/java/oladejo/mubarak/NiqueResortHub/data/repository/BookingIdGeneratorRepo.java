package oladejo.mubarak.NiqueResortHub.data.repository;

import oladejo.mubarak.NiqueResortHub.data.model.BookingIdGenerator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingIdGeneratorRepo extends JpaRepository<BookingIdGenerator,Long> {
    BookingIdGenerator findBookingIdGeneratorByGeneratedBookingId(String bookingId);
    boolean existsByGeneratedBookingId(String generatedBookingId);
}
