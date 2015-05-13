package cz.fi.muni.pb138.broker.business.util;

import cz.fi.muni.pb138.broker.data.model.Property;
import java.io.*;
import java.util.List;

/**
 * @author Viki
 */
public class PreparsedDataImporter {

    public void importData(List<Property> propertyList) throws Exception {

        addCoordinatesToProperties(propertyList);
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("data/src/main/resources/META-INF/sql/sql-insert.SQL"), "utf-8"))) {
            for(Property property : propertyList) {
                String insertStatement = prepareInsert(property);
                writer.write(insertStatement);
                writer.write("\n");
            }
        }
        catch(IOException ex) {
            throw new IOException("Unable to write to file" + ex);
        }
    }

    private String prepareInsert(Property property) {

        String area = property.getArea().toString();
        String price = property.getPrice().toString();

        String street = property.getAddress().getStreet();
        if(street == null) {
            street = "null";
        }
        else {
            street = "'" + street + "'";
        }

        String district = "'" + property.getAddress().getDistrict() + "'";
        String city = "'" + property.getAddress().getCity() + "'";

        String type;
        if(property.getType() == null) {
            type = "null";
        }
        else {
            type = "'" + property.getType().getText() + "'";
        }

        String streetXCoord = Double.toString(property.getStreetCoords().getX());
        String streetYCoord = Double.toString(property.getStreetCoords().getY());


        String insertStatement = "INSERT INTO Property (area, price, street, district, city, type, x, y) VALUES (" + area
                + ", " + price + ", "  + street
                + ", " + district + ", " + city + ", "
                + type + ", " + streetXCoord + ", " + streetYCoord + ");";

        return insertStatement;
    }

    private void addCoordinatesToProperties(List<Property> properties) {
        CoordsEnquirer enquirer = new CoordsEnquirer();
        enquirer.addCoords(properties);
    }


}
