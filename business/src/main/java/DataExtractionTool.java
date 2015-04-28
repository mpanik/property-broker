import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.fi.muni.pb138.broker.data.model.Property;
import org.json.*;

/**
 * Created by viki on 26.4.15.
 */
public class DataExtractionTool {


    public static List<Property> extractFromSReality() throws Exception {

        List<Property> properties = new ArrayList<>();

        String countData = readUrl("http://www.sreality.cz/api/cs/v1/estates/count?category_main_cb=1&category_type_cb=1&locality_country_id=112&region=okres+Brno-m%C4%9Bsto&region_entity_id=72&region_entity_type=district");
        JSONObject countDataJsonObject = new JSONObject(countData);
        int estateCount = countDataJsonObject.getInt("result_size");

        int estateCounter = 0;
        int pageNumber  = 1;

        while(estateCounter < estateCount) {
            String estateData = readUrl("http://www.sreality.cz/api/cs/v1/estates?category_main_cb=1&category_type_cb=1&page=" + pageNumber + "&per_page=100&region=okres+Brno-m%C4%9Bsto&region_entity_id=72&region_entity_type=district&tms=1430150466326");
            pageNumber++;
            JSONObject estateDataJsonObject = new JSONObject(estateData);
            JSONArray estates = estateDataJsonObject.getJSONObject("_embedded").getJSONArray("estates");
            estateCounter += estates.length();
            for(int i = 0; i < estates.length(); i++) {

                JSONObject apartment = estates.getJSONObject(i);
                String typeAndArea = apartment.getString("name");

                String type;
                Pattern pattern = Pattern.compile("\\d+[+]\\d+|\\d+[+]kk");
                Matcher matcher = pattern.matcher(typeAndArea);
                if (matcher.find()) {
                    type = matcher.group();
                } else {
                    type = null;
                }

                String area;
                pattern = Pattern.compile("\\d+.m");
                matcher = pattern.matcher(typeAndArea);
                if (matcher.find()) {
                    area = matcher.group();
                    int index = area.indexOf('m');
                    area = area.substring(0, index - 1);
                } else {
                    area = null;
                }
                int intArea = -1;
                if (area != null) {
                    intArea = Integer.parseInt(area);
                }

                String locality = apartment.getString("locality");

                String price = apartment.getString("price");
                int intPrice = Integer.parseInt(price);
                //Whenever price is not present in the advertisement, the data file contains 1 for price.
                // Changing it to -1 to make it clear price value is not valid
                if(intPrice == 1) {
                    intPrice = -1;
                }

                Property property = new Property(type, intArea, locality, intPrice);
                properties.add(property);
            }
        }
        return properties;
    }


    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
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
