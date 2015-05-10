package cz.fi.muni.pb138.broker.business.util;

import cz.fi.muni.pb138.broker.business.parser.PropertyParser;
import cz.fi.muni.pb138.broker.business.parser.SRealityParser;
import cz.fi.muni.pb138.broker.data.model.Property;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

/**
 * Created by viki on 10.5.15.
 */
public class CoordsEnquirer {

  /*  public static void main(String[] args) throws Exception {
        try {
            PropertyParser parser = new SRealityParser();
            List<Property> properties = parser.parse();
            PreparsedDataImporter importer = new PreparsedDataImporter();
            importer.importData(properties);
        }
        catch(Exception ex) {}
    }*/

    public List<Property> addCoords(List<Property> properties) {

        for(Property property : properties) {
            String response;
            try {
                String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                        property.getAddress().getCity() + "+" + property.getAddress().getStreet();
                response = readUrl(url, "utf-8");
                JSONObject responseJSON = new JSONObject(response);
                JSONArray resultsArray = responseJSON.getJSONArray("results");
                JSONObject locationObject = resultsArray.getJSONObject(0);
                JSONObject geometry = locationObject.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");
                String latitude = location.getString("lat");
                String longitude = location.getString("lng");
                Point2D streetLocation = new Point2D.Double(Double.parseDouble(latitude),
                                                            Double.parseDouble(longitude));
                property.setStreetCoords(streetLocation);
            }
            catch(Exception ex) {
                Point2D streetLocation = new Point2D.Double(0.0, 0.0);
                property.setStreetCoords(streetLocation);
            }
        }
        return properties;
    }


    protected String readUrl(String urlString, String encoding) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream(), encoding));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

}
