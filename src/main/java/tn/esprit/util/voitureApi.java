package tn.esprit.util;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class voitureApi {
    
    public static List<String> getMarqueOfYear(int year){
        List<String> makeDisplayList = new ArrayList<>();
        try {
            String urlString = "https://www.carqueryapi.com/api/0.3/?callback=?&cmd=getMakes&year=" + year;
        
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "PostmanRuntime/7.26.10");
            connection.setRequestMethod("GET");
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();
        
            // Remove the callback function wrapper
            String jsonResponse = response.toString().replace("?(", "").replace(");", "");

            // Parse JSON
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray makesArray = jsonObject.getJSONArray("Makes");

            // Iterate through the makes and print their display names
            for (int i = 0; i < makesArray.length(); i++) {
                JSONObject make = makesArray.getJSONObject(i);
                String makeName = make.getString("make_display");

                makeDisplayList.add(makeName);
            }
        } catch (IOException e) {
            // Handle I/O error
            System.err.println(e);
        }
        return makeDisplayList;
    }

    public static List<String> getModelFromMarque(String marque, int year){
        List<String> makeDisplayList = new ArrayList<>();
        try {
            String urlString = "https://www.carqueryapi.com/api/0.3/?callback=?&cmd=getModels&make=" + marque + "&year=" + year;
        
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "PostmanRuntime/7.26.10");
            connection.setRequestMethod("GET");
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();
        
            // Remove the callback function wrapper
            String jsonResponse = response.toString().replace("?(", "").replace(");", "");

            // Parse JSON
        // Parse the JSON string
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray modelsArray = jsonObject.getJSONArray("Models");

        // Extract and print the model names
        for (int i = 0; i < modelsArray.length(); i++) {
            JSONObject modelObject = modelsArray.getJSONObject(i);
            String modelName = modelObject.getString("model_name");
            makeDisplayList.add(modelName);
        }
        } catch (IOException e) {
            // Handle I/O error
            System.err.println(e);
        }
        return makeDisplayList;
    }

    public static List<String> getFuelAndSeats(String marque, String model, int year){
        List<String> fuelAndSeatsList = new ArrayList<>();
        String fuelType = null;
        String seats = null;
        try {
            String urlString = "https://www.carqueryapi.com/api/0.3/?cmd=getTrims&make=" + marque + "&model=" + model + "&year=" + year;
        
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "PostmanRuntime/7.26.10");
            connection.setRequestMethod("GET");
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();
        
            // Remove the callback function wrapper
            String jsonResponse = response.toString().replace("?(", "").replace(");", "");

            // Parse JSON
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray trimsArray = jsonObject.getJSONArray("Trims");

            // Extract fuel type and seating capacity
            for (int i = 0; i < trimsArray.length(); i++) {
                JSONObject trimObject = trimsArray.getJSONObject(i);
                if (!trimObject.isNull("model_engine_fuel") && fuelType == null) {
                    String potentialFuelType = trimObject.getString("model_engine_fuel");
                    if (potentialFuelType.equals("Electric") || potentialFuelType.equals("Gasoline") || potentialFuelType.equals("Diesel")) {
                        fuelType = potentialFuelType;
                    }
                }
                if (!trimObject.isNull("model_seats") && seats == null) {
                    seats = trimObject.getString("model_seats");
                }
            }
            
            if (seats != null){
                fuelAndSeatsList.add(seats);
            }else{
                fuelAndSeatsList.add("4");
            }
            if (fuelType != null){
                fuelAndSeatsList.add(fuelType);
            }else{
                fuelAndSeatsList.add("Gasoline");
            }
        } catch (IOException e) {
            // Handle I/O error
            System.err.println(e);
        }

        return fuelAndSeatsList;
    }

}
