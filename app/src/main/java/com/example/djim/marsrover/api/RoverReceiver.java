package com.example.djim.marsrover.api;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Djim on 13-3-2018.
 */

public class RoverReceiver extends AsyncTask<String, Void, String> {

    private static final String TAG = "RoverReceiver";

    public RoverReceiver(){

    }

    @Override
    protected String doInBackground(String... params) {
        InputStream inputStream =null;
        int responsCode = -1;
        //De URL die we via de .execute() meegeleverd krijgen
        String roverApi = params[0];
        //Het resultaat
        String response = "";

        Log.i(TAG, "doInBackground - " + roverApi);
        try{
            URL url = new URL(roverApi);
            URLConnection urlConnection = url.openConnection();

            if(!(urlConnection instanceof HttpURLConnection)){
                return null;
            }
            // Initialiseer een HTTP connectie
            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestMethod("GET");

            // Voer het request uit via de HTTP connectie op de URL
            httpConnection.connect();

            // Kijk of het gelukt is door de response code te checken
            responsCode = httpConnection.getResponseCode();
            if (responsCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.getInputStream();
                response = getStringFromInputStream(inputStream);
                // Log.i(TAG, "doInBackground response = " + response);
            } else {
                Log.e(TAG, "Error, invalid response");
            }
        }catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground MalformedURLEx " + e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e("TAG", "doInBackground IOException " + e.getLocalizedMessage());
            return null;
        }

        // Hier eindigt deze methode.
        // Het resultaat gaat naar de onPostExecute methode.
        return response;
    }

    /**
     * onPostExecute verwerkt het resultaat uit de doInBackground methode.
     *
     * @param response
     */
    protected void onPostExecute(String response) {

        Log.i(TAG, "onPostExecute " + response);

        // Check of er een response is
        if(response == null || response == "") {
            Log.e(TAG, "onPostExecute kreeg een lege response!");
            return;
        }

        // Het resultaat is in ons geval een stuk tekst in JSON formaat.
        // Daar moeten we de info die we willen tonen uit filteren (parsen).
        // Dat kan met een JSONObject.
        JSONObject jsonObject;
        try {
            // Top level json object
            jsonObject = new JSONObject(response);

            // Get all users and start looping
            JSONArray roverData = jsonObject.getJSONArray("photos");
            for(int idx = 0; idx < roverData.length(); idx++) {
                // array level objects and get user
                JSONObject roverObject = roverData.getJSONObject(idx);

                // Get id and camera name
                String id = roverObject.getString("id");
                String cameraName = roverObject.getString("full_name");

                Log.i(TAG, "Got camera " + id + " " + cameraName);

                // Get image url
                String imageThumbURL = roverObject.getString("img_src");
                String imageURL = roverObject.getString("img_src");
                Log.i(TAG, imageURL);

                // Create new Product object
                // Builder Design Pattern


                //
                // call back with new product data
                //

                //listener.OnProductAvailable(product);
            }
        } catch( JSONException ex) {
            Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
    }

    private static String getStringFromInputStream(InputStream is){
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

}
