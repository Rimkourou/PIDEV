package InterfaceReservation;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailTransaction {

    public static void sendMail(String recepient) throws Exception{
        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "tunishow.tn@gmail.com";
        //Your gmail password
        String password = "tunishow1234";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        //Prepare email message
        Message message = prepareMessage(session, myAccountEmail, recepient);

        //Send mail
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("TuniShow");
            String htmlCode = "<h2> Congrats! </h2> <br/> <h3><b>Hello, <br>We are delighted to announce that your payment has been confirmed. <br> Best Regards </b></h3>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception ex) {
            Logger.getLogger(JavaMailTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;}
}
