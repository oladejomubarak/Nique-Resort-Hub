package oladejo.mubarak.NiqueResortHub.data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private String roomNumber;
    private String emailAddress;
    private LocalDate bookingDate;
    private LocalDate checkinDate;
    private LocalDate  checkoutDate;
    private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private BigDecimal extendStayPrice;
    private int extendStayDays;
    @Enumerated(EnumType.STRING)
    private PaymentStatus extendStayPaymentStatus;

}
