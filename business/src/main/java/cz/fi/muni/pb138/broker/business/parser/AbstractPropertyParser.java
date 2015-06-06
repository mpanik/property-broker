package cz.fi.muni.pb138.broker.business.parser;

import cz.fi.muni.pb138.broker.data.enums.Type;
import cz.fi.muni.pb138.broker.data.model.Address;
import cz.fi.muni.pb138.broker.data.model.Property;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains methods to extract and parse properties data from reality web server
 * @author Viki
 */
public abstract class AbstractPropertyParser implements PropertyParser {

    protected final String SREALITY = "SReality";
    protected final String BRNENSKE_REALITY = "BrnenskeReality";

    public abstract Set<Property> parse() throws Exception;


    /**
     * Parse inserted data and insert them into property object
     * @param type property type
     * @param area property area
     * @param district district where is property located
     * @param street street where is property located
     * @param price property price
     * @param realityServer name of reality server
     * @return property object filled with data
     */
    protected Property parseAndBuild(String type, String area, String district, String street, String price, String realityServer) {

        BigDecimal numPrice;
        try {
            numPrice = parsePrice(price);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unable to build a property object, not enough data");
        }

        try {
            type = parseType(type);
        } catch (IllegalArgumentException ex) {
            type = null;
        }

        Address address = parseAddress(street, district, realityServer);

        Integer numArea;
        try {
            numArea = parseArea(area);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unable to build a property object, not enough data");
        }

        Property property = buildProperty(type, numArea, numPrice, address);

        return property;
    }

    /**
     * Read data from url and save them into string
     * @param urlString property server url
     * @param encoding type of encoding
     * @return data from url
     * @throws Exception cannot read data from url
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

    /**
     * Create new property object filled with data
     * @param type property type
     * @param area property area
     * @param price property price
     * @param address property adddress
     * @return property filled data
     */
    protected Property buildProperty(String type, Integer area, BigDecimal price, Address address) {
        return new Property.Builder()
                .type(Type.fromString(type))
                .address(address)
                .area(area)
                .price(price)
                .build();
    }

    /**
     * Create new address object filled with data
     * @param street street where is property located
     * @param district district where is property located
     * @param city city where is property located
     * @return address filled with data
     */
    protected Address buildAddress(String street, String district, String city) {
        return new Address.Builder()
                .city(city)
                .district(district)
                .street(street)
                .build();
    }

    /**
     * Parse property area into integer
     * @param areaData property area
     * @return property area
     */
    protected Integer parseArea(String areaData) {

        String area;
        //finding numeric values followed by a blank and m (for square meters)
        Pattern pattern = Pattern.compile("\\d+.m");
        try {
            area = parseRegex(areaData, pattern);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("No area value in the data provided");
        }
        //extracting just the numeric value from the matched data
        int index = area.indexOf('m');
        area = area.substring(0, index - 1);

        return Integer.parseInt(area);
    }

    /**
     * Parse property type into specific format
     * @param typeData property type
     * @return formatted property type
     */
    protected String parseType(String typeData) {

        String type;
        //extracting "3+1" or "2+kk" types
        Pattern pattern = Pattern.compile("\\d+[+]\\d+|\\d+[+]kk");
        try {
            type = parseRegex(typeData, pattern);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("No estate type in data");
        }

        return type;
    }

    /**
     * Parse property type into bigDecimal
     * @param priceData property price
     * @return property price
     */
    protected BigDecimal parsePrice(String priceData) {

        //replacing blanks in the string representing price
        String price = priceData.replaceAll("\\xA0", "");

        //extracting only the number part of the price
        Pattern pattern = Pattern.compile("\\d+");
        try {
            price = parseRegex(price, pattern);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Argument not a number");
        }
        if (Integer.parseInt(price) == 1) {
            throw new IllegalArgumentException("Argument not a number");
        }
        BigDecimal numPrice = new BigDecimal(price);

        return numPrice;
    }

    /**
     * Parse string by regular expression pattern
     * @param string string data
     * @param pattern expression patten
     * @return parsed string
     */
    private String parseRegex(String string, Pattern pattern) {

        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            string = matcher.group();
        } else {
            throw new IllegalArgumentException("Unable to find matches");
        }

        return string;
    }

    /**
     * Parse address of property
     * @param estateData estate of property
     * @param districtData district where is property located
     * @param realityServer name of reality server
     * @return property address
     */
    private Address parseAddress(String estateData, String districtData, String realityServer) {

        String city = "Brno";
        Pattern pattern;
        if (realityServer.equals(BRNENSKE_REALITY)) {
            pattern = Pattern.compile("(?<=ul[.].)\\w+", Pattern.UNICODE_CHARACTER_CLASS);
        } else {
            pattern = Pattern.compile("\\w+\\s*\\w*(?=,.+)", Pattern.UNICODE_CHARACTER_CLASS);
        }
        String street;
        try {
            street = parseRegex(estateData, pattern);
        } catch (IllegalArgumentException ex) {
            street = null;
        }

        if (realityServer.equals(BRNENSKE_REALITY)) {
            pattern = Pattern.compile("(?<=Brno, Brno - )\\w+\\s*\\w*|(?<=Brno, BRNO-)\\w+\\s*\\w*", Pattern.UNICODE_CHARACTER_CLASS);
        } else {
            pattern = Pattern.compile("(?<=Brno - )(?!Brno)\\w+\\s*\\w*|(?<=Brno - Brno-)\\w+", Pattern.UNICODE_CHARACTER_CLASS);
        }
        String district;
        try {
            district = parseRegex(districtData, pattern);
            district = district.substring(0, 1).toUpperCase() + district.substring(1, district.length()).toLowerCase();
        } catch (IllegalArgumentException ex) {
            district = "Brno-město";
        }

        Address address = buildAddress(street, district, city);
        return address;
    }

}
