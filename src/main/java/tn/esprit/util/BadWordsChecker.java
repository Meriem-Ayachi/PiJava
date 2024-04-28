package tn.esprit.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class BadWordsChecker {

    public boolean containsBadWords(String text) {
        try {
            String urlString = "http://www.purgomalum.com/service/containsprofanity?text=" + text;

            // Créer une URL à partir de l'URL de l'API
            URL url = new URL(urlString);

            // Ouvrir une connexion HTTP avec l'API
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Lire la réponse de l'API
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            // Vérifier si la réponse contient "true"
            return response.toString().equals("true");
        } catch (IOException e) {
            // Gérer l'erreur d'entrée-sortie
            e.printStackTrace(); // Afficher l'erreur pour le débogage
            return false; // Retourner false en cas d'erreur
        }
    }

}
