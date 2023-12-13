package oladejo.mubarak.NiqueResortHub.controller;

import lombok.RequiredArgsConstructor;
import oladejo.mubarak.NiqueResortHub.service.PaymentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment/")
@CrossOrigin("*")
public class PaymentController {
    private final PaymentServiceImpl paymentService;
    @PostMapping("booking")
    public ResponseEntity<?> payForRoomBooking(@RequestParam Long bookingId) {
        try {
            return ResponseEntity.ok(paymentService.payForBookReservation(bookingId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("extend-stay")
    public ResponseEntity<?> payForExtendStay(@RequestParam Long bookingId) {
        try {
            return ResponseEntity.ok(paymentService.payForExtendingStay(bookingId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
