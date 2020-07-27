package com.example.quakereport;

import android.text.TextUtils;
import android.widget.Toast;

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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class QuakeQuery {

    public static List<Quake> fetchQuakeData(String requestUrl){

        URL url= createUrl(requestUrl);

        String jsonResponse;

        jsonResponse=makeHttpRequest(url);

        return extractFromjson(jsonResponse);
    }

    private static URL createUrl(String requestUrl){

        URL url=null;

        try {
            url=new URL(requestUrl);
        } catch (MalformedURLException e) {
            Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return url;
    }

    private static String makeHttpRequest(URL url) {

        String jsonResponse="";

        if (url==null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;

        try {
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                inputStream=urlConnection.getInputStream();
                jsonResponse =readFromStream(inputStream);
            } else {
                Toast.makeText(null, "Can't Connect to the Server", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {

            if (urlConnection !=null){
                urlConnection.disconnect();
            }
            if (inputStream !=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream){

        StringBuilder sb=new StringBuilder();

        if(inputStream != null){

            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

            String line= null;
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            while (line != null){
                sb.append(line);
                try {
                    line=bufferedReader.readLine();
                } catch (IOException e) {
                    Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        return sb.toString();
    }


    private static List<Quake> extractFromjson(String jsonResponse){

        if(TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        List<Quake> quakes=new ArrayList<>();

        try {
            JSONObject jsonObject= new JSONObject(jsonResponse);
            JSONArray jsonArray=jsonObject.getJSONArray("features");

            for (int i=0;i<jsonArray.length();i++){

                JSONObject currentQuake=jsonArray.getJSONObject(i);
                JSONObject properties=currentQuake.getJSONObject("properties");
                double magnitude = properties.getDouble("mag");
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");

                Quake quake=new Quake(magnitude,location,time,url);
                quakes.add(quake);
            }
        } catch (JSONException e) {
            Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return quakes;
    }

}
