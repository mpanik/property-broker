package cz.fi.muni.pb138.broker.business.util;

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

    private enum Type {STREET, DISTRICT}

    public List<Property> addCoords(List<Property> properties) {

        for (Property property : properties) {
            String streetForUrl = property.getAddress().getStreet();
            if (streetForUrl != null) {
                if (streetForUrl.contains(" ")) {
                    String[] split = property.getAddress().getStreet().split(" ");
                    streetForUrl = split[0] + "+" + split[1];
                }
                String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                        streetForUrl + ",+" + property.getAddress().getCity();
                Point2D streetLocation = getCoords(url);
                property.setStreetCoords(streetLocation);
            }
        }
        fillBlanks(properties, Type.STREET);
        for (Property property : properties) {
            if (property.getAddress().getStreet() == null || (property.getStreetCoords().getX() == 0.0 && property.getStreetCoords().getY() == 0)) {
                String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                        property.getAddress().getDistrict() + ",+" + property.getAddress().getCity();
                Point2D streetLocation = getCoords(url);
                property.setStreetCoords(streetLocation);
            }
        }
        fillBlanks(properties, Type.DISTRICT);
        return properties;
    }

    private Point2D getCoords(String url) {

        Point2D streetLocation;
        try {
            String response = readUrl(url, "utf-8");
            JSONObject responseJSON = new JSONObject(response);
            JSONArray resultsArray = responseJSON.getJSONArray("results");
            JSONObject locationObject = resultsArray.getJSONObject(0);
            JSONObject geometry = locationObject.getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            String latitude = location.getString("lat");
            String longitude = location.getString("lng");
            streetLocation = new Point2D.Double(Double.parseDouble(latitude),
                    Double.parseDouble(longitude));
        } catch (Exception ex) {
            streetLocation = new Point2D.Double(0.0, 0.0);
        }

        return streetLocation;
    }

    private void fillBlanks(List<Property> properties, Type type) {

        for (Property property : properties) {
            if (property.getStreetCoords() != null && property.getStreetCoords().getX() == 0.0 && property.getStreetCoords().getY() == 0.0) {
                for (Property propToCompareWith : properties) {
                    if (propToCompareWith.getStreetCoords() != null) {
                        String thisPropValue = null;
                        String otherPropValue = null;
                        if (type == Type.STREET) {
                            thisPropValue = property.getAddress().getStreet();
                            otherPropValue = propToCompareWith.getAddress().getStreet();
                        } else if (type == Type.DISTRICT) {
                            thisPropValue = property.getAddress().getDistrict();
                            otherPropValue = propToCompareWith.getAddress().getDistrict();
                        }
                        double otherPropX = propToCompareWith.getStreetCoords().getX();
                        double otherPropY = propToCompareWith.getStreetCoords().getY();
                        if (thisPropValue != null && otherPropValue != null && thisPropValue.equals(otherPropValue)
                                && otherPropX != 0.0 && otherPropY != 0.0) {
                            Point2D newCoords = new Point2D.Double(otherPropX, otherPropY);
                            property.setStreetCoords(newCoords);
                            break;
                        }
                    }
                }
            }
        }
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
