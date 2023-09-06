package oladejo.mubarak.NiqueResortHub.service;

import com.squareup.okhttp.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.NiqueResortHub.config.email.EmailServiceImpl;
import oladejo.mubarak.NiqueResortHub.data.model.Booking;
import oladejo.mubarak.NiqueResortHub.data.model.PaymentStatus;
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
    public String payForBookReservation(Long bookingId) throws IOException, MessagingException {
        Booking foundBooking = guestService.findBooking(bookingId);

        RequestBody paymentBody = RequestBody.create(mediaType,
                "{\"amount\":" + foundBooking.getTotalPrice() + "," +
                        "\"email\":\"" + foundBooking.getEmailAddress() + "\"," +
                        "\"reference\":\"" + foundBooking.getId().toString() + "\"}");

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
        foundBooking.setPaymentStatus(PaymentStatus.SUCCESSFUL);
        guestService.saveBooking(foundBooking);
        emailService.sendEmailForPayment(foundBooking.getEmailAddress(),
                buildPaymentEmail(foundBooking.getFirstName(),
                        paymentDetails, foundBooking.getCheckoutDate()));
        return "Payment initiated, check your email to complete your payment";
    }

    @Override
    public String payForExtendingStay(Long bookingId) throws IOException, MessagingException {
        Booking foundBooking = guestService.findBooking(bookingId);

        RequestBody paymentBody = RequestBody.create(mediaType,
                "{\"amount\":" + foundBooking.getExtendStayPrice() + "," +
                        "\"email\":\"" + foundBooking.getEmailAddress() + "\"," +
                        "\"reference\":\"" + foundBooking.getId() + "\"}");

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
        foundBooking.setTotalPrice(foundBooking.getTotalPrice().add(foundBooking.getExtendStayPrice()));
        foundBooking.setExtendStayPaymentStatus(PaymentStatus.SUCCESSFUL);
        foundBooking.setCheckoutDate(foundBooking.getCheckoutDate().plusDays(foundBooking.getExtendStayDays()));
        guestService.saveBooking(foundBooking);
        emailService.sendEmailForPayment(foundBooking.getEmailAddress(),
                buildPaymentEmailForExtendingStay(foundBooking.getFirstName(),
                        paymentDetails,
                        foundBooking.getCheckoutDate()));
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
                "<p>Your check-out date will be extended till \"" + checkoutDate + "\" after payment completion</p>" +
                "<p>Thank You!</p>";
    }
}
