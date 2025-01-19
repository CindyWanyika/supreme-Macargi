import com.google.gson.JsonArray;
import okhttp3.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.lang.Math;

import static java.lang.Math.PI;

public class LocationAPI {
    //key gotten from the access token on locationIQ
    private static final String API_KEY;
    private static final String BASE_URL = "https://us1.locationiq.com/v1/search.php";
    // forward geocoding, getting coordinates for an address
    public String[] getCoordinates(String address) {
        OkHttpClient client = new OkHttpClient();
        String[] coordinates = new String[2];

        HttpUrl url = HttpUrl.parse(BASE_URL).newBuilder()
                .addQueryParameter("key", API_KEY)
                .addQueryParameter("q", address)
                .addQueryParameter("format", "json")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                // Parse response using Gson
                JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
                JsonObject firstResult = jsonArray.get(0).getAsJsonObject();

                coordinates[0] = firstResult.get("lat").getAsString();
                coordinates[1] = firstResult.get("lon").getAsString();
            } else {
                System.out.println("Error: " + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coordinates;
    }

    //forward geocoding,,getting coordinates for an address

    public String getAddress(String lat, String lon) {
        OkHttpClient client = new OkHttpClient();
        String address = "";

        HttpUrl url = HttpUrl.parse("https://us1.locationiq.com/v1/reverse.php").newBuilder()
                .addQueryParameter("key", API_KEY)
                .addQueryParameter("lat", lat)
                .addQueryParameter("lon", lon)
                .addQueryParameter("format", "json")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                // Parse response using Gson
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                address = jsonObject.get("display_name").getAsString();
            } else {
                System.out.println("Error: " + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }
    public static double calculateDistance(Location start, Location end){
        // distance between latitudes
        // and longitudes
        double dLat = (start.getLatitude() - end.getLatitude()) *
                PI / 180.0;
        double dLon = (start.getLongitude() - end.getLongitude()) *
                PI / 180.0;

        // convert to radians
        double rad_lat1 = (start.getLatitude()) * PI / 180.0;
        double rad_lat2 = (end.getLatitude()) * PI / 180.0;

        // apply Haversine formula
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(rad_lat1) * Math.cos(rad_lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;

    }
    public static void main(String[] args) {
        LocationAPI api = new LocationAPI();

        // Forward Geocoding Example
        String[] coordinates = api.getCoordinates("Berekuso");
        System.out.println("Latitude: " + coordinates[0]);
        System.out.println("Longitude: " + coordinates[1]);

        // Reverse Geocoding Example
        String address = api.getAddress(coordinates[0], coordinates[1]);
        System.out.println("Address: " + address);

        // Distance Calculation
        String[] coA = api.getCoordinates("Ashesi");
        Location loc1 = new Location("Ashesi University", coA[0], coA[1]);
        String[] coB = api.getCoordinates("kumasi");
        Location loc2 = new Location("Kumasi", coB[0], coB[1]);

        double distance = LocationAPI.calculateDistance(loc1, loc2);
        System.out.println("Distance: " + distance + " km");
    }
}
