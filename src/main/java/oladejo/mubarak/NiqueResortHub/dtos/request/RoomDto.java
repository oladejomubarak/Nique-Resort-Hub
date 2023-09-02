package oladejo.mubarak.NiqueResortHub.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import oladejo.mubarak.NiqueResortHub.data.model.RoomStatus;
import oladejo.mubarak.NiqueResortHub.data.model.RoomType;

import java.math.BigDecimal;

@Data
public class RoomDto {
    @NotBlank(message = "This field must be filled")
    private String roomNumber;
    @NotBlank(message = "This field must be filled")
    private RoomType roomType;
    @NotBlank(message = "This field must be filled")
    private BigDecimal roomPrice;
    @NotBlank(message = "This field must be filled")
    private RoomStatus roomStatus;
}
