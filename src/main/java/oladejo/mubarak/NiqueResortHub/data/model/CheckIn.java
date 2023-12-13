package oladejo.mubarak.NiqueResortHub.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@RequiredArgsConstructor
@Data
public class CheckIn{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private LocalDate checkInDate;
    private LocalTime checkInTime;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String roomNumber;

    public CheckIn(LocalDate checkInDate, LocalTime checkInTime, String email, String firstName,
                   String lastName, String phoneNumber, String roomNumber) {
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.roomNumber= roomNumber;
    }
}
