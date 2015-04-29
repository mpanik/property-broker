package cz.fi.muni.pb138.broker.business.parser;

import cz.fi.muni.pb138.broker.data.model.Address;
import cz.fi.muni.pb138.broker.data.model.Property;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Named;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Viki
 */
@Named
public class BrnenskeRealityParser implements PropertyParser {
    @Override
    public List<Property> parse() throws Exception {

        List<Property> properties = new ArrayList<>();
        final int ADS_PER_PAGE = 15;

        String url = "http://www.brnenske-reality.cz/byty-prodej/brno-okres";
        String data = readUrl(url, "iso-8859-2");
        Document doc = Jsoup.parse(data);

        Integer pageCounter = ADS_PER_PAGE + 1;
        Elements resultSet = doc.select("article");
        while(resultSet.size() > 0) {
            int intPrice = -1;
            int intArea = -1;
            Address address = null;
            String type = "";
            String area;
            String district;
            for(Element ad : resultSet) {
                Element estateDataElements = ad.select("p:has(span)").first();
                Elements estateData = estateDataElements.select("span");
                type = estateData.get(0).text();
                area = estateData.get(1).text();
                district = estateData.get(2).text();
                String description = ad.select("a[title]").first().attr("title");
                String price = ad.select("p.cena").first().select("strong").first().text();

                try {
                    intPrice = parsePrice(price);
                }
                catch(IllegalArgumentException ex) {
                    intPrice = -1;
                }
                try {
                    type = parseType(type);
                }
                catch(IllegalArgumentException ex) {
                    type = null;
                }
                try {
                    address = parseAddress(description, district);
                }
                catch(IllegalArgumentException ex) {
                    address = null;
                }
                try {
                    intArea = parseArea(area);
                }
                catch(IllegalArgumentException ex) {
                    intArea = -1;
                }
                BigDecimal priceBigDecimal = new BigDecimal(intPrice);
                Property property = buildProperty(type, intArea, priceBigDecimal, address);
                properties.add(property);
            }
            data = readUrl(url + "/" + pageCounter, "iso-8859-2");
            doc = Jsoup.parse(data);
            resultSet = doc.select("article");
            pageCounter += ADS_PER_PAGE;
        }
        return properties;
    }

    private Property buildProperty(String type, Integer area, BigDecimal price, Address address) {
        Property property = new Property();
        property.setType(type);
        property.setArea(area);
        property.setPrice(price);
        property.setAddress(address);
        return property;
    }

    private int parseArea(String initialArea) {
        String area;
        Pattern pattern = Pattern.compile("\\d+.m");
        Matcher matcher = pattern.matcher(initialArea);
        if (matcher.find()) {
            area = matcher.group();
            int index = area.indexOf('m');
            area = area.substring(0, index - 1);
        } else {
            throw new IllegalArgumentException("No suitable value for area");
        }
        int intArea = Integer.parseInt(area);
        return intArea;
    }

    private Address parseAddress(String estateData, String districtData) {
        String street;
        String district;
        String city = "Brno";
        Pattern pattern = Pattern.compile("ul[.].\\w+", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(estateData);
        if (matcher.find()) {
            int streetNameIndex = 4;
            street = matcher.group().substring(streetNameIndex);
        } else {
            street = null;
        }

        //TODO find proper regex for the situation like "Brno, Kohoutovice"
        //what to do with uppercase???
        pattern = Pattern.compile("Brno - .*|BRNO-.*", Pattern.UNICODE_CHARACTER_CLASS);
        matcher = pattern.matcher(districtData);
        if (matcher.find()) {
            district = matcher.group();
        } else {
            district = null;
        }

        Address address = buildAddress(street, district, city);
        return address;
    }

    private String parseType(String estateData) {

        String type;
        Pattern pattern = Pattern.compile("\\d+[+]\\d+|\\d+[+]kk");
        Matcher matcher = pattern.matcher(estateData);
        if (matcher.find()) {
            type = matcher.group();
        } else {
            throw new IllegalArgumentException("No estate type in estateData");
        }

        return type;
    }

    private int parsePrice(String price) {

        price = price.replaceAll("\\xA0", "");

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(price);
        if (matcher.find()) {
            price = matcher.group();
        } else {
            throw new IllegalArgumentException("Argument not a number");
        }
        int intPrice = Integer.parseInt(price);
        return intPrice;
    }

    private Address buildAddress(String street, String district, String city) {
        Address address = new Address();
        address.setCity(city);
        address.setDistrict(district);
        address.setStreet(street);
        return address;
    }

    private String readUrl(String urlString, String encoding) throws Exception {
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
