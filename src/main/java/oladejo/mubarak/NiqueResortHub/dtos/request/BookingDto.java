package oladejo.mubarak.NiqueResortHub.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookingDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String telephoneNumber;
    @NotBlank
    private String emailAddress;
    @NotBlank
    private String checkinDate;
    private int numberOfNightsToBeSpent;
}
