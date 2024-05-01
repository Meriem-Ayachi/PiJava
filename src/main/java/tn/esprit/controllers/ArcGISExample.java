//import com.esri.arcgisruntime.geometry.Point;
//import com.esri.arcgisruntime.tasks.geocode.GeocodeParameters;
//import com.esri.arcgisruntime.tasks.geocode.GeocodeResult;
//import com.esri.arcgisruntime.tasks.geocode.GeocodeTask;
//import com.esri.arcgisruntime.tasks.geocode.LocatorTask;
//
//public class ArcGISExample {
//
//    public static void main(String[] args) {
//        // Remplacez "YOUR_API_KEY" par votre clé API ArcGIS
//        String apiKey = "AAPKd3525cf8176e46708c8c2d35fb7d3869-akb7tnScC3mxUd88igFranOnpw1p-aQBPSQaGDIdYiXncEDQCzprFELpxKUxnG9";
//
//        // Créer un objet de service de géocodage avec votre clé API
//        String geocodeServiceUrl = "https://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer";
//        GeocodeTask geocodeTask = new GeocodeTask(geocodeServiceUrl);
//        geocodeTask.setApiKey(apiKey);
//
//        // Définir l'adresse de destination pour la géocodage
//        String destinationAddress = "YOUR_DESTINATION_ADDRESS";
//
//        // Créer les paramètres de géocodage avec l'adresse de destination
//        GeocodeParameters parameters = new GeocodeParameters();
//        parameters.setAddress(destinationAddress);
//
//        try {
//            // Effectuer la requête de géocodage
//            GeocodeResult result = geocodeTask.geocode(parameters).get();
//
//            // Récupérer les coordonnées géographiques de la destination
//            Point destinationPoint = result.getDisplayLocation();
//
//            // Utiliser les coordonnées géographiques de la destination
//            System.out.println("Destination Latitude: " + destinationPoint.getY());
//            System.out.println("Destination Longitude: " + destinationPoint.getX());
//
//            // Utilisez ces coordonnées pour effectuer d'autres opérations dans votre application
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
