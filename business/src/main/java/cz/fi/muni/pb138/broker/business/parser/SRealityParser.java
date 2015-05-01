package cz.fi.muni.pb138.broker.business.parser;

import cz.fi.muni.pb138.broker.data.model.Property;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Viki
 */
@Named
public class SRealityParser extends AbstractPropertyParser {

    @Override
    public List<Property> parse() throws Exception {
        return extractFromSReality();
    }

    private List<Property> extractFromSReality() throws Exception {

        List<Property> properties = new ArrayList<>();

        String url = "http://www.sreality.cz/api/cs/v1/estates/count?category_main_cb=1&category_type_cb=1&locality_country_id=112&region=okres+Brno-m%C4%9Bsto&region_entity_id=72&region_entity_type=district";

        JSONObject data = extractDataFromUrl(url);
        int totalEstateCount = data.getInt("result_size");

        int parsedEstateCounter = 0;
        int pageNumber = 1;
        while (parsedEstateCounter < totalEstateCount) {
            url = "http://www.sreality.cz/api/cs/v1/estates?category_main_cb=1&category_type_cb=1&page=" + pageNumber + "&per_page=100&region=okres+Brno-m%C4%9Bsto&region_entity_id=72&region_entity_type=district&tms=1430150466326";
            pageNumber++;
            JSONObject estateDataJsonObject = extractDataFromUrl(url);
            //get the array with apartment data for this page
            JSONArray estates = estateDataJsonObject.getJSONObject("_embedded").getJSONArray("estates");
            parsedEstateCounter += estates.length();
            for (int i = 0; i < estates.length(); i++) {
                JSONObject apartment = estates.getJSONObject(i);
                String typeAndArea = apartment.getString("name");
                String locality = apartment.getString("locality");
                String price = apartment.getString("price");
                Property property = parseAndBuild(typeAndArea, typeAndArea, locality, locality, price, SREALITY);
                properties.add(property);
            }
        }
        return properties;
    }

    private JSONObject extractDataFromUrl(String url) throws Exception {

        String data = readUrl(url, "utf-8");
        JSONObject jsonObjectData = new JSONObject(data);

        return jsonObjectData;
    }

}
