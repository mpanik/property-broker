package cz.fi.muni.pb138.broker.business.util;

import cz.fi.muni.pb138.broker.data.model.Property;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Set;

/**
 * This class contains methods for finding and assigning coords to property
 * @author Viki
 */
public class CoordsEnquirer {


    /**
     * Add coords to properties
     * @param properties set of properties
     * @return property with coords
     */
    public Set<Property> addCoords(Set<Property> properties) {

        for (Property property : properties) {
            String streetForUrl = property.getAddress().getStreet();
            if (streetForUrl != null) {
                if (streetForUrl.contains(" ")) {
                    String[] split = property.getAddress().getStreet().split(" ");
                    streetForUrl = split[0] + "+" + split[1];
                }
                String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                        streetForUrl + ",+" + property.getAddress().getCity();
                Point2D.Double streetLocation = getCoords(url);
                property.setCoords(streetLocation);
            }
        }
        fillBlanks(properties, Type.STREET);
        for (Property property : properties) {
            if (property.getAddress().getStreet() == null || (property.getCoords().getX() == 0.0 && property.getCoords().getY() == 0)) {
                String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                        property.getAddress().getDistrict() + ",+" + property.getAddress().getCity();
                Point2D.Double streetLocation = getCoords(url);
                property.setCoords(streetLocation);
            }
        }
        fillBlanks(properties, Type.DISTRICT);
        return properties;
    }

    /**
     * Get coords of property by url
     * @param url  maps.googleapis url with address parameter
     * @return coord of property
     */
    private Point2D.Double getCoords(String url) {

        Point2D.Double streetLocation;
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


    private void fillBlanks(Set<Property> properties, Type type) {

        for (Property property : properties) {
            if (property.getCoords() != null && property.getCoords().getX() == 0.0 && property.getCoords().getY() == 0.0) {
                for (Property propToCompareWith : properties) {
                    if (propToCompareWith.getCoords() != null) {
                        String thisPropValue = null;
                        String otherPropValue = null;
                        if (type == Type.STREET) {
                            thisPropValue = property.getAddress().getStreet();
                            otherPropValue = propToCompareWith.getAddress().getStreet();
                        } else if (type == Type.DISTRICT) {
                            thisPropValue = property.getAddress().getDistrict();
                            otherPropValue = propToCompareWith.getAddress().getDistrict();
                        }
                        double otherPropX = propToCompareWith.getCoords().getX();
                        double otherPropY = propToCompareWith.getCoords().getY();
                        if (thisPropValue != null && otherPropValue != null && thisPropValue.equals(otherPropValue)
                                && otherPropX != 0.0 && otherPropY != 0.0) {
                            Point2D.Double newCoords = new Point2D.Double(otherPropX, otherPropY);
                            property.setCoords(newCoords);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Read data from url
     * @param urlString server url
     * @param encoding type of encoding
     * @return string data
     * @throws Exception exception
     */
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

    private enum Type {STREET, DISTRICT}

}
