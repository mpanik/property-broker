package cz.fi.muni.pb138.broker.business.parser;

import cz.fi.muni.pb138.broker.data.model.Property;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Viki
 */
@Named
public class BrnenskeRealityParser extends AbstractPropertyParser {

    final private int ADS_PER_PAGE = 15;

    @Override
    public List<Property> parse() throws Exception {
        return extractFromBrnenskeReality();
    }

    public List<Property> extractFromBrnenskeReality() throws Exception {

        List<Property> properties = new ArrayList<>();

        String url = "http://www.brnenske-reality.cz/byty-prodej/brno-okres";
        Integer pageCounter = ADS_PER_PAGE + 1;

        Elements onePageAds = extractDataFromPage(url);
        while(onePageAds.size() > 0) {
            //extracting advertisements one by one for parsing
            for(Element ad : onePageAds) {
                Element estateDataElements= ad.select("p:has(span)").first();
                Elements estateData = estateDataElements.select("span");
                //first span contains type of estate
                String type = estateData.get(0).text();
                //second span contains area
                String area = estateData.get(1).text();
                //third span contains city and district
                String district = estateData.get(2).text();
                //anchor tag with "title" attribute contains the estate general description
                String description = ad.select("a[title]").first().attr("title");
                //paragraph tag with "cena" attribute contains price info
                String price = ad.select("p.cena").first().select("strong").first().text();
                try {
                    Property property = parseAndBuild(type, area, district, description, price, BRNENSKE_REALITY);
                    properties.add(property);
                }
                catch(IllegalArgumentException ex) {
                    continue;
                }
            }
            //extract data from the next page
            onePageAds = extractDataFromPage(url + "/" + pageCounter);
            pageCounter += ADS_PER_PAGE;
        }
        return properties;
    }

    private Elements extractDataFromPage(String url) throws Exception {

        String data = readUrl(url, "iso-8859-2");
        Document doc = Jsoup.parse(data);
        Elements resultSet = doc.select("article");

        return resultSet;
    }

}
