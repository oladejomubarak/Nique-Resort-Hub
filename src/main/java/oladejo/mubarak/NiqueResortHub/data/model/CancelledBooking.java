package oladejo.mubarak.NiqueResortHub.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
public class CancelledBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    @OneToOne
    private Booking booking;
}
