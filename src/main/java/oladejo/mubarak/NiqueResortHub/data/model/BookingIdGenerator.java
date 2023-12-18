package oladejo.mubarak.NiqueResortHub.data.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class BookingIdGenerator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String generatedBookingId;

    public BookingIdGenerator(String generatedBookingId){
        this.generatedBookingId = generatedBookingId;
    }
}
