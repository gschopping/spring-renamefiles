package nl.schoepping.spring_renamefiles.action;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.extern.java.Log;
import nl.schoepping.spring_renamefiles.domain.Address;
import nl.schoepping.spring_renamefiles.domain.OSMLocation;
import tools.jackson.databind.json.JsonMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

@Log
public class ReadAddress {

    public ReadAddress() {

    }

    private String getRequest(String url) {

        String result = "";
        try {
            final URL obj = new URL(url);
            final HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            con.setRequestProperty("Accept-Language", "nl,en-US;q=0.7,en;q=0.3");
            con.setRequestProperty("Cache-Control", "max-age=0");
            con.setRequestProperty("Connection", "keep-alive");
            con.setRequestProperty("Host", "nominatim.openstreetmap.org");
            con.setRequestProperty("TE", "Trailers");
            con.setRequestProperty("Upgrade-Insecure-Requests", "1");
            con.setRequestProperty("User-Agent", "Rename mediafiles");

            if (con.getResponseCode() != 200) {
                return null;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            result = response.toString();

        }
        catch (Exception ex) {
            log.log(Level.WARNING, "Could not get URL: " + url, ex);
        }
        return result;
    }

    public Address getAddress(Double latitude, Double longitude) {

        if ((latitude == 0) || (longitude ==0)) {
            return null;
        }
        Address address = new Address();
        StringBuilder query;
        String queryResult = null;

        query = new StringBuilder();

        query.append("https://nominatim.openstreetmap.org/reverse?format=jsonv2");

        query.append("&lat=");
        query.append(latitude);
        query.append("&lon=");
        query.append(longitude);
        query.append("&layer=address&zoom=18");

//        log.debug("Query:" + query);

        // don't request api for testcases in order to avoid blocking of the site
        if (latitude.equals(51.454183) && longitude.equals(3.653545)) {
            queryResult = "{\"place_id\":261870388,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright\",\"osm_type\":\"way\",\"osm_id\":168908204,\"lat\":\"51.4541747279983\",\"lon\":\"3.65353658637693\",\"display_name\":\"Rammekensweg, Ritthem, Vlissingen, Zeeland, Nederland, 4389TZ, Nederland\",\"address\":{\"footway\":\"Rammekensweg\",\"suburb\":\"Ritthem\",\"town\":\"Vlissingen\",\"state\":\"Zeeland\",\"postcode\":\"4389TZ\",\"country\":\"Nederland\",\"country_code\":\"nl\"},\"boundingbox\":[\"51.4528958\",\"51.4549397\",\"3.6533426\",\"3.6540872\"]}";
        } else if (latitude.equals(51.679494) && longitude.equals(4.138041)) {
            queryResult = "{\"place_id\":137959627,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright\",\"osm_type\":\"way\",\"osm_id\":262113993,\"lat\":\"51.67877565\",\"lon\":\"4.13843211722729\",\"display_name\":\"Strandweg, Bruinisse, Schouwen-Duiveland, Zeeland, Nederland, 4311NE, Nederland\",\"address\":{\"parking\":\"Strandweg\",\"road\":\"Strandweg\",\"suburb\":\"Bruinisse\",\"city\":\"Schouwen-Duiveland\",\"state\":\"Zeeland\",\"postcode\":\"4311NE\",\"country\":\"Nederland\",\"country_code\":\"nl\"},\"boundingbox\":[\"51.678328\",\"51.6792308\",\"4.1379332\",\"4.1389456\"]}";
        } else if (latitude.equals(51.616227) && longitude.equals(3.685589)) {
            queryResult = "{\"place_id\":70656193,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright\",\"osm_type\":\"way\",\"osm_id\":7594894,\"lat\":\"51.6163822677409\",\"lon\":\"3.68442277437964\",\"display_name\":\"Rijksweg 57, Vrouwenpolder, Veere, Zeeland, Nederland, 4354RB, Nederland\",\"address\":{\"road\":\"Rijksweg 57\",\"suburb\":\"Vrouwenpolder\",\"village\":\"Veere\",\"state\":\"Zeeland\",\"postcode\":\"4354RB\",\"country\":\"Nederland\",\"country_code\":\"nl\"},\"boundingbox\":[\"51.6150515\",\"51.618107\",\"3.6842456\",\"3.6846524\"]}";
        } else if (latitude.equals(51.616253) && longitude.equals(3.685654)) {
            queryResult = "{\"place_id\":70656193,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright\",\"osm_type\":\"way\",\"osm_id\":7594894,\"lat\":\"51.6163822677409\",\"lon\":\"3.68442277437964\",\"display_name\":\"Rijksweg 57, Vrouwenpolder, Veere, Zeeland, Nederland, 4354RB, Nederland\",\"address\":{\"road\":\"Rijksweg 57\",\"suburb\":\"Vrouwenpolder\",\"village\":\"Veere\",\"state\":\"Zeeland\",\"postcode\":\"4354RB\",\"country\":\"Nederland\",\"country_code\":\"nl\"},\"boundingbox\":[\"51.6150515\",\"51.618107\",\"3.6842456\",\"3.6846524\"]}";
        } else {
            try {
                queryResult = getRequest(query.toString());
            } catch (Exception e) {
                //            log.error("Error when trying to get data with the following query " + query);
            }
        }

        if (queryResult == null) {
            return null;
        }

//        OSMLocation location =  JsonMapper.builder(queryResult, OSMLocation.class);
        String result;


        return address;
    }


}
