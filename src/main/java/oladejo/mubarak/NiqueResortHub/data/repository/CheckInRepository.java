package oladejo.mubarak.NiqueResortHub.data.repository;

import oladejo.mubarak.NiqueResortHub.data.model.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    List<CheckIn> findByCheckInDate(LocalDate checkInDate);


}
