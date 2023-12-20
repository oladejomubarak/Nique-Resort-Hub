package oladejo.mubarak.NiqueResortHub.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import oladejo.mubarak.NiqueResortHub.dtos.request.BookingDto;
import oladejo.mubarak.NiqueResortHub.dtos.request.RoomDto;
import oladejo.mubarak.NiqueResortHub.service.GuestServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/guest/")
@CrossOrigin("*")
@RequiredArgsConstructor
public class GuestController {
    private final GuestServiceImpl guestService;

    @PostMapping("book/{roomId}")
    public ResponseEntity<?> bookRoom(@PathVariable Long roomId, @RequestBody BookingDto bookingDto) {
        try {
            return ResponseEntity.ok(guestService.bookRoom(roomId, bookingDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("booking/find/{roomId}")
    public ResponseEntity<?> findRoom(@PathVariable Long roomId) {
        try {
            return ResponseEntity.ok(guestService.findBooking(roomId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("booking/extend")
    public ResponseEntity<?> extendStay(@RequestParam String bookingId, @RequestParam int numberOfDays) {
        try {
            return ResponseEntity.ok(guestService.extendStay(bookingId, numberOfDays));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("booking/cancel")
    public ResponseEntity<?> cancelBooking(@RequestParam String bookingId) {
        try {
            guestService.cancelBooking(bookingId);
            return ResponseEntity.ok("Booking has been cancelled successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("booking/successful")
    public ResponseEntity<?> findAllSuccessfulBookingByDate(@RequestParam String bookingDate) {
        return ResponseEntity.ok(guestService.findAllSuccessfulBookingByDate(bookingDate));
    }

    @PostMapping("send")
    public ResponseEntity<?> sendEmailToCustomers() {
        try {
            guestService.sendMessageToAllCustomers();
            return ResponseEntity.ok("Successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
