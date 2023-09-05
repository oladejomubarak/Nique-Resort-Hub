package oladejo.mubarak.NiqueResortHub.dtos.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import oladejo.mubarak.NiqueResortHub.data.model.PaymentStatus;
import oladejo.mubarak.NiqueResortHub.data.model.RoomType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BookingDto {
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private String emailAddress;
    private String checkinDate;
    private int numberOfNightsToBeSent;
}
