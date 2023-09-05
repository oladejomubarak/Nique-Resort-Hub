package oladejo.mubarak.NiqueResortHub.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.NiqueResortHub.data.model.Booking;
import oladejo.mubarak.NiqueResortHub.data.model.PaymentStatus;
import oladejo.mubarak.NiqueResortHub.data.model.Room;
import oladejo.mubarak.NiqueResortHub.data.model.RoomType;
import oladejo.mubarak.NiqueResortHub.data.repository.BookingRepository;
import oladejo.mubarak.NiqueResortHub.dtos.request.BookingDto;
import oladejo.mubarak.NiqueResortHub.exception.NiqueResortHubException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService{
    private final BookingRepository bookingRepository;
   private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final RoomServiceImpl roomService;
    @Override
    public Booking bookRoom(Long roomId, BookingDto bookingDto) {
        Room foundRoom = roomService.getRoomById(roomId);

        for (int i = 0; i < bookingDto.getTelephoneNumber().length(); i++) {
            if(!Character.isDigit(bookingDto.getTelephoneNumber().charAt(i))){
                throw new NiqueResortHubException("Phone numbers can only be digits");
            }

        }
        Booking booking = new Booking();
        booking.setEmailAddress(bookingDto.getEmailAddress());
        booking.setFirstName(bookingDto.getFirstName());
        booking.setLastName(bookingDto.getLastName());
        booking.setTelephoneNumber(bookingDto.getTelephoneNumber());
        booking.setRoomNumber(foundRoom.getRoomNumber());
        booking.setPaymentStatus(PaymentStatus.PENDING);
        booking.setCheckinDate(LocalDate.parse(bookingDto.getCheckinDate(), dateFormatter));
        booking.setCheckoutDate(LocalDate.parse(bookingDto.getCheckinDate(), dateFormatter)
                .plusDays(bookingDto.getNumberOfNightsToBeSent()));
        booking.setTotalPrice(foundRoom.getRoomPrice().multiply(BigDecimal.valueOf(bookingDto.getNumberOfNightsToBeSent())));
        return bookingRepository.save(booking);
    }

    @Override
    public Booking findBooking(Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(()-> new NiqueResortHubException("booking not found"));
    }

    @Override
    public Booking extendStay(Long bookingId, int numberOfDays) {
        Booking foundBooking
        return null;
    }

    @Override
    public void cancelBooking(Long bookingId) {

    }
}
