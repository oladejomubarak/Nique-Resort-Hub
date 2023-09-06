package oladejo.mubarak.NiqueResortHub.service;

import com.squareup.okhttp.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.NiqueResortHub.config.email.EmailServiceImpl;
import oladejo.mubarak.NiqueResortHub.data.model.Booking;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final EmailServiceImpl emailService;

    private final GuestServiceImpl guestService;
    private final String pay_stack_key = System.getenv("PAY_STACK_SECRET_KEY");

    private final OkHttpClient client = new OkHttpClient();
    private final MediaType mediaType = MediaType.parse("application/json");

    @Override
    public String payForBookReservation(Long bookingId) throws IOException {
        Booking foundBooked = guestService.findBooking(bookingId);


        RequestBody paymentBody = RequestBody.create(mediaType,
                "{\"amount\":" + foundBooked.getTotalPrice() + "," +
                        "\"email\":\"" + foundBooked.getEmailAddress() + "\"," +
                        "\"reference\":\"" + foundBooked.getId() + "\"}");

        Request request = new Request.Builder()
                .url("https://api.paystack.co/transaction/initialize")
                .post(paymentBody)
                .addHeader("Authorization", "Bearer " + pay_stack_key)
                .addHeader("Content-Type", "application/json")
                .build();
        String paymentDetails = "";
        try (ResponseBody response = client.newCall(request).execute().body()) {
            paymentDetails += response.string();
        }
        return "Payment initiated, check your email to complete your payment";
    }

    @Override
    public String payForExtendingStay(Long bookingId) throws IOException {
        Booking foundBooked = guestService.findBooking(bookingId);


        RequestBody paymentBody = RequestBody.create(mediaType,
                "{\"amount\":" + foundBooked.getTotalPrice() + "," +
                        "\"email\":\"" + foundBooked.getEmailAddress() + "\"," +
                        "\"reference\":\"" + foundBooked.getId() + "\"}");

        Request request = new Request.Builder()
                .url("https://api.paystack.co/transaction/initialize")
                .post(paymentBody)
                .addHeader("Authorization", "Bearer " + pay_stack_key)
                .addHeader("Content-Type", "application/json")
                .build();
        String paymentDetails = "";
        try (ResponseBody response = client.newCall(request).execute().body()) {
            paymentDetails += response.string();
        }
        return "Payment initiated, check your email to complete your payment";
    }

    private String buildPaymentEmail(String firstname, String paymentDetails, LocalDate checkinDate){
        return "Here is your payment details" +
                "                                   " +
                "                                       " +
                "<p>Hello \"" + firstname + "\",</p>" +
                "<p>Your payment was successfully processed</p>" +
                "<p>Click the link provided below to complete your payment" +
                "<p>\"" + paymentDetails + "\"</p>" +
                "<br>" +
                "<p>Your check-in date is on  \"" + checkinDate + "\"</p>" +
                "<p>Thank You, see you soon!</p>";
    }

    private String buildPaymentEmailForExtendingStay(String firstname, String paymentDetails, LocalDate checkoutDate){
        return "Here is your payment details" +
                "                                   " +
                "                                       " +
                "<p>Hello \"" + firstname + "\",</p>" +
                "<p>Your payment was successfully processed</p>" +
                "<p>Click the link provided below to complete your payment" +
                "<p>\"" + paymentDetails + "\"</p>" +
                "<br>" +
                "<p>Your check-out date will extended till \"" + checkoutDate + "\" after payment completion</p>" +
                "<p>Thank You!</p>";
    }
}
