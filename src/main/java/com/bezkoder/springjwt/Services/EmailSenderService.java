package com.bezkoder.springjwt.Services;



import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class EmailSenderService {

    @Autowired
    private final JavaMailSender javaMailSender;


    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendcodevalidation(String code , String email ,String api) throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(message, true);
        mailMessage.setFrom("housssembenmoussa@gmail.com");
        mailMessage.setTo(email);
        mailMessage.setSubject("Notification de code validation");
        String htmlMsg = "<h2>code</h2>"+
                "<h4> code :"+code+"</h4>"+
                "<a href='"+api+"'> Valider votre Compte </a>"
                ;

        message.setContent(htmlMsg, "text/html");

        javaMailSender.send(message);
    }
    public void sendConfirmationEmail(){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("housssembenmoussa@gmail.com");
        message.setTo("housssembenmoussa@gmail.com");
        message.setSubject("fsd");
        message.setText("fwdsfsdffvdsv");
        javaMailSender.send(message);

    }
    public void sendEmailWithImage(String toEmail, String subject, String body, String imagePath) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);
            // Ajouter l'image comme une ressource
            ClassPathResource image = new ClassPathResource("digid.png");

            helper.addInline("image", image);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Gérer les erreurs de messagerie
            e.printStackTrace();
        }
    }
    public void sendpassword(String email , String api ) throws Exception{

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(message, true);
        mailMessage.setFrom("housssembenmoussa@gmail.com");
        mailMessage.setTo(email);
        mailMessage.setSubject("Notification de réinitialisation du mot de passe");
        String htmlMsg = "<h2>Nous vous avons envoyé par email le lien de réinitialisation du mot de passe !</h2>"+
                "<h4> Pour réinitialisation votre mot de passe :</h4>"+
                "<a href='"+api+"'> réinitialiser mot de passe </a>" +
                "<p>Ce lien de réinitialisation du mot de passe expirera dans 30 minutes.</p> ";

        message.setContent(htmlMsg, "text/html");

        javaMailSender.send(message);



    }
    public void senddemand(String email , String nameevent , String[] nameclub, String[] nameresp ) throws Exception{

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(message, true);
        mailMessage.setFrom("housssembenmoussa@gmail.com");
        mailMessage.setTo(email);
        mailMessage.setSubject("Demande de l'evenemnet "+nameevent);
        String htmlMsg = "<h2>  Madame,Monsieur  </h2>"+
                "<h4> Une demande d un evenement "+nameevent+" a été deposé par le responsable du club "+nameclub[0]+" "+nameresp[0]+"." +

                "<p>Merci de consulter et repondre à cette demande</p> ";

        message.setContent(htmlMsg, "text/html");

        javaMailSender.send(message);



    }
    public void sendAcceptation(String email , String nameevent ,String nameresp, String nameclub ) throws Exception{

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(message, true);
        mailMessage.setFrom("housssembenmoussa@gmail.com");
        mailMessage.setTo(email);
        mailMessage.setSubject("Reponse à la demande de l'evenment "+nameevent);
        String htmlMsg = "<h2> Bonjour Mr "+nameresp+" </h2>"+
                "<h4> Nous vous informons que votre demande de l'evenement "+nameevent+" de votre club  "+nameclub+" a été accepter ." ;

        message.setContent(htmlMsg, "text/html");

        javaMailSender.send(message);



    }
    public void sendRefus(String email , String nameevent ,String nameresp, String nameclub) throws Exception{

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(message, true);
        mailMessage.setFrom("housssembenmoussa@gmail.com");
        mailMessage.setTo(email);
        mailMessage.setSubject("Reponse à la demande de l'evenement "+nameevent);
        String htmlMsg = "<h2> Bonjour Mr "+nameresp+" </h2>"+
                "<h4> Nous avons le regret de vous inforer que votre demande pour l'evenment "+nameevent+" de votre club  "+nameclub+" n'a pas été accepter ."

                ;

        message.setContent(htmlMsg, "text/html");

        javaMailSender.send(message);



    }

}