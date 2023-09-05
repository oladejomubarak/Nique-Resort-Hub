package oladejo.mubarak.NiqueResortHub.config.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
    private final JavaMailSender javaMailSender;
    @Override
    public void sendEmailForBooking(String receiverEmail, String message) throws MessagingException {
        try{
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, "Utf-8");
            mimeMessageHelper.setSubject("Booking Reservation");
            mimeMessageHelper.setTo(receiverEmail);
            mimeMessageHelper.setFrom("oladejomubarakade@gmail.com", "Nique Resort Hub");
            mimeMessageHelper.setText(message, true);
            javaMailSender.send(mailMessage);
        } catch (MessagingException e){
            log.info("Problem 1: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (MailException e){
            log.info("Problem 2: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            log.info("problem 3: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendEmailForPayment(String receiverEmail, String message) throws MessagingException {
        try{
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, "Utf-8");
            mimeMessageHelper.setSubject("Booking Reservation");
            mimeMessageHelper.setTo(receiverEmail);
            mimeMessageHelper.setFrom("oladejomubarakade@gmail.com", "Nique Resort Hub");
            mimeMessageHelper.setText(message, true);
            javaMailSender.send(mailMessage);
        } catch (MessagingException e){
            log.info("Problem 1: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (MailException e){
            log.info("Problem 2: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            log.info("problem 3: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

}


