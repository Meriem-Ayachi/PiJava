package tn.esprit.controllers;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class reservationMail {

    public static void sendEmail(String recipient, String subject, String content) {
        // Paramètres de configuration du serveur SMTP
        String host = "smtp.gmail.com";
        String username = "malekabdelkader.bensdira@esprit.tn";
        String password = "sdira123";
        int port = 587;

        // Propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Créer une session de messagerie avec authentification
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Créer un objet MimeMessage représentant l'e-mail
            Message message = new MimeMessage(session);
            // Définir l'expéditeur
            message.setFrom(new InternetAddress(username));
            // Ajouter le destinataire
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            // Définir le sujet
            message.setSubject(subject);
            // Définir le contenu de l'e-mail
            message.setText(content);

            // Envoyer l'e-mail
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès !");
        } catch (MessagingException e) {
            System.err.println("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Utilisation de la méthode sendEmail pour envoyer un e-mail
        sendEmail("maloukabensdira3@gmail.com", "Test d'envoi d'e-mail", "Ceci est un e-mail de test envoyé depuis Java.");
    }
}
