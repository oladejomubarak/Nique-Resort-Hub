package oladejo.mubarak.NiqueResortHub.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long guestId;
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    //private RoomType roomType;
    private String email;
//    private String checkinDate;
//    private String checkoutDate;
    //private BigDecimal roomPrice;
    //private PaymentStatus paymentStatus;
}
