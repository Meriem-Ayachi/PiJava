//package tn.esprit.controllers;
//
//import com.esri.arcgisruntime.geometry.Point;
//import com.esri.arcgisruntime.tasks.geocode.GeocodeParameters;
//import com.esri.arcgisruntime.tasks.geocode.GeocodeResult;
//import com.esri.arcgisruntime.tasks.geocode.LocatorTask;
//
//import java.util.List;
//
//public class ArcGISExample {
//
//    public static void main(String[] args) {
//        // Remplacez "YOUR_API_KEY" par votre clé API ArcGIS
//        String apiKey = "AAPKd3525cf8176e46708c8c2d35fb7d3869-akb7tnScC3mxUd88igFranOnpw1p-aQBPSQaGDIdYiXncEDQCzprFELpxKUxnG9";
//
//        // Créer un objet LocatorTask avec l'URL de service de géocodage
//        String locatorTaskUrl = "https://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer";
//        LocatorTask locatorTask = new LocatorTask(locatorTaskUrl);
//        locatorTask.setApiKey(apiKey);
//
//        // Définir l'adresse de destination pour la géocodage
//        String destinationAddress = "tunisia";
//
//        // Créer les paramètres de géocodage avec l'adresse de destination
//        GeocodeParameters parameters = new GeocodeParameters();
//        parameters.setMaxResults(Integer.parseInt(destinationAddress));
//
//        try {
//            // Effectuer la requête de géocodage
//            List<GeocodeResult> result = locatorTask.geocodeAsync(String.valueOf(parameters)).get();
//
//            // Récupérer les coordonnées géographiques de la destination
//            GeocodeResult destinationPoint = result.getLast();
//
//            // Utiliser les coordonnées géographiques de la destination
//            System.out.println("Destination Latitude: " + destinationPoint.getScore());
//            System.out.println("Destination Longitude: " + destinationPoint.getExtent());
//
//            // Utilisez ces coordonnées pour effectuer d'autres opérations dans votre application
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
