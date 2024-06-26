package tn.esprit.util;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import io.github.cdimascio.dotenv.Dotenv;
import tn.esprit.models.User;

public class EmailsUtils {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String SMTP_HOST = dotenv.get("SMTP_HOST");
    private static final String SMTP_PORT = dotenv.get("SMTP_PORT");
    private static final String USERNAME = dotenv.get("USERNAMES");
    private static final String PASSWORD = dotenv.get("PASSWORD");

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public static void envoyerEmailConfirmation(User user) {


        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Créer une session de messagerie avec l'authentification
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            // Créer le message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject("Confirmation d'inscription");
            message.setText("");
            Multipart multipart = new MimeMultipart();

            // Part 1: Text content
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("");

            // Part 2: HTML content
            MimeBodyPart htmlPart = new MimeBodyPart();


            // Part 2: HTML co
            String htmlContent1 = "<!DOCTYPE html>" +
                    "<html lang=\"fr\">" +
                    "<head>" +
                    "    <meta charset=\"UTF-8\">" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                    "    <title>Confirmation</title>" +
                    "    <style>" +
                    "        body {" +
                    "            font-family: Arial, sans-serif;" +
                    "            text-align: center;" +
                    "            margin: 0;" +
                    "            padding: 0;" +
                    "            background-color: #f5f5f5;" +
                    "        }" +
                    "        .container {" +
                    "            max-width: 600px;" +
                    "            margin: 100px auto;" +
                    "            background-color: #fff;" +
                    "            border-radius: 8px;" +
                    "            padding: 40px;" +
                    "            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);" +
                    "        }" +
                    "        h1 {" +
                    "            color: #333;" +
                    "        }" +
                    "        p {" +
                    "            color: #666;" +
                    "            line-height: 1.6;" +
                    "        }" +
                    "    </style>" +
                    "</head>" +
                    "<body>" +
                    "    <div class=\"container\">" +
                    "        <h1>Confirmation</h1>" +
                    "        <p>Bienvenue!</p>" +
                    "        <p>Merci beaucoup d'avoir rejoint notre communaute. Nous sommes ravis de vous accueillir.</p>" +
                    "        <p>N'hesitez pas à explorer notre site et a nous contacter si vous avez des questions ou des suggestions.</p>" +
                    "        <p>A bientot!</p>" +
                    "    </div>" +
                    "</body>" +
                    "</html>";

            htmlPart.setContent(htmlContent1, "text/html");

            // Add the parts to the multipart
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlPart);
            // Set the content of the message
            message.setContent(multipart);

            Transport.send(message);

            System.out.println("E-mail de confirmation envoye avec succès.");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'envoi de l'e-mail de confirmation.");
        }
    }

    public static void sendVerificationEmail(String toEmail, String verificationCode) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Verification de l'e-mail");
            message.setText("");
            Multipart multipart = new MimeMultipart();

            // Part 1: Text content
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(verificationCode);

            // Part 2: HTML content
            MimeBodyPart htmlPart = new MimeBodyPart();
            String htmlContent1 = "<!DOCTYPE html>" +
                    "<html lang=\"fr\">" +
                    "<head>" +
                    "  <meta charset=\"UTF-8\">" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                    "  <title>Code de verification</title>" +
                    "  <style>" +
                    "    body {" +
                    "      font-family: Arial, sans-serif;" +
                    "    }" +
                    "    .verification-code {" +
                    "      background-color: #F9F4FF;" +
                    "      padding: 20px;" +
                    "    }" +
                    "    .verification-code h1 {" +
                    "      color: #666666;" +
                    "      font-size: 24px;" +
                    "    }" +
                    "    .verification-code p {" +
                    "      color: #666666;" +
                    "      font-size: 16px;" +
                    "    }" +
                    "    .verification-code strong {" +
                    "      color: #4ca2f8;" +
                    "    }" +
                    "  </style>" +
                    "</head>" +
                    "<body>" +
                    "  <div class=\"verification-code\">" +
                    "    <h1>Code de vérification</h1>" +
                    "    <p>Nous vous avons envoyé un code de verification unique.</p>" +
                    "    <p>Votre code de verification est : <strong>" + verificationCode  + " </strong></p>" +
                    "    <p>Assurez-vous de ne pas partager ce code avec quiconque, car il est destine a des fins de securite personnelles.</p>" +
                    "  </div>" +
                    "</body>" +
                    "</html>";

            htmlPart.setContent(htmlContent1, "text/html");

            // Add the parts to the multipart
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlPart);

            // Set the content of the message
            message.setContent(multipart);

            Transport.send(message);

            System.out.println("E-mail de vérification envoye avec succes."+verificationCode);

        } catch (MessagingException e) {
            e.printStackTrace();
            // Gérer les erreurs d'envoi d'e-mail
        }
    }


    public static void sendVerificationEmail_ForgotPassword(String toEmail, String verificationCode) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Forgot Password");
            message.setText("");
            Multipart multipart = new MimeMultipart();

            // Part 1: Text content
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(verificationCode);

            // Part 2: HTML content
            MimeBodyPart htmlPart = new MimeBodyPart();
            String htmlContent1 = "<!DOCTYPE html>" +
                    "<html lang=\"fr\">" +
                    "<head>" +
                    "  <meta charset=\"UTF-8\">" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                    "  <title>Code de verification</title>" +
                    "  <style>" +
                    "    body {" +
                    "      font-family: Arial, sans-serif;" +
                    "    }" +
                    "    .verification-code {" +
                    "      background-color: #F9F4FF;" +
                    "      padding: 20px;" +
                    "    }" +
                    "    .verification-code h1 {" +
                    "      color: #666666;" +
                    "      font-size: 24px;" +
                    "    }" +
                    "    .verification-code p {" +
                    "      color: #666666;" +
                    "      font-size: 16px;" +
                    "    }" +
                    "    .verification-code strong {" +
                    "      color: #4ca2f8;" +
                    "    }" +
                    "  </style>" +
                    "</head>" +
                    "<body>" +
                    "  <div class=\"verification-code\">" +
                    "    <h1>Reset password verification code</h1>" +
                    "    <p>Nous vous avons envoye un code de verification unique.</p>" +
                    "    <p>Votre code de verification est : <strong>" + verificationCode  + " </strong></p>" +
                    "    <p>Assurez-vous de ne pas partager ce code avec quiconque, car il est destine a des fins de securite personnelles.</p>" +
                    "  </div>" +
                    "</body>" +
                    "</html>";

            htmlPart.setContent(htmlContent1, "text/html");

            // Add the parts to the multipart
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlPart);

            // Set the content of the message
            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Forgot password email sent."+verificationCode);

        } catch (MessagingException e) {
            e.printStackTrace();
            // Gérer les erreurs d'envoi d'e-mail
        }
    }

    public static void envoyerEmailConfirmation_ForgotPassword(User user) {


        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Créer une session de messagerie avec l'authentification
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            // Créer le message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject("Password Modifier");
            message.setText("");
            Multipart multipart = new MimeMultipart();

            // Part 1: Text content
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("");

            // Part 2: HTML content
            MimeBodyPart htmlPart = new MimeBodyPart();


            // Part 2: HTML co
            String htmlContent1 = "<!DOCTYPE html>" +
                    "<html lang=\"fr\">" +
                    "<head>" +
                    "    <meta charset=\"UTF-8\">" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                    "    <title>Confirmation</title>" +
                    "    <style>" +
                    "        body {" +
                    "            font-family: Arial, sans-serif;" +
                    "            text-align: center;" +
                    "            margin: 0;" +
                    "            padding: 0;" +
                    "            background-color: #f5f5f5;" +
                    "        }" +
                    "        .container {" +
                    "            max-width: 600px;" +
                    "            margin: 100px auto;" +
                    "            background-color: #fff;" +
                    "            border-radius: 8px;" +
                    "            padding: 40px;" +
                    "            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);" +
                    "        }" +
                    "        h1 {" +
                    "            color: #333;" +
                    "        }" +
                    "        p {" +
                    "            color: #666;" +
                    "            line-height: 1.6;" +
                    "        }" +
                    "    </style>" +
                    "</head>" +
                    "<body>" +
                    "    <div class=\"container\">" +
                    "        <h1>Confirmation</h1>" +
                    "        <p>Votre mot de passe a ete modifie avec succes!</p>" +
                    "        <p>Merci beaucoup d'avoir utilise notre service de reinitialisation de mot de passe.</p>" +
                    "        <p>N'hesitez pas a nous contacter si vous avez d'autres questions ou des preoccupations.</p>" +
                    "        <p>Meilleures salutations!</p>" +
                    "    </div>" +
                    "</body>" +
                    "</html>";

            htmlPart.setContent(htmlContent1, "text/html");

            // Add the parts to the multipart
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlPart);
            // Set the content of the message
            message.setContent(multipart);

            Transport.send(message);

            System.out.println("E-mail de confirmation envoyé avec succès.");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'envoi de l'e-mail de confirmation.");
        }
    }

}
