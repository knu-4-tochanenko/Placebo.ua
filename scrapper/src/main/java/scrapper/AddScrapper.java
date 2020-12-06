package scrapper;

import config.Config;
import database.Drug;
import database.DrugDAO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddScrapper implements Scrapper {

    private static final String WEBSITE_URL = "https://www.add.ua/";
    private static final int MAXIMUM_PAGES = Config.PAGES;

    public static boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void analyze() throws SQLException, ClassNotFoundException {
        for (int i = 0; i < MAXIMUM_PAGES; i++) {
            ArrayList<String> drugUrls = findDrugs("https://www.add.ua/medicamenti/?dir=asc&order=name" + (i > 0 ? "&p=" + (i + 1) : ""));

            for (String url : drugUrls) {
                DrugDAO.insert(parseDrug(url));
            }
        }
    }

    private ArrayList<String> findDrugs(String pageUrl) {
        ArrayList<String> drugUrls = new ArrayList<>();

        Document doc = null;
        try {
            doc = Jsoup.connect(pageUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements drugUrlElements = doc.select(".amlabel-div a");
        String singleUrl = "";
        for (Element url : drugUrlElements) {
            singleUrl = url.attr("href");
            if (isValid(singleUrl) && singleUrl.endsWith(".html"))
                drugUrls.add(url.attr("href"));
        }

        return drugUrls;
    }

    private Drug parseDrug(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements propertiesInDom = doc.select("#product-attribute-specs-table tbody tr");
        Map<String, String> params = new HashMap<>();

        String value = "";
        for (Element property : propertiesInDom) {
            if (property.child(1).children().hasAttr("href")) {
                value = property.child(1).child(0).html();
            } else {
                value = property.child(1).html();
            }
            params.put(property.child(0).html(), value);
        }

        String title = doc.title();
        Drug drug = new Drug();
        drug.setName(title.substring(0, title.indexOf(" -")));
        drug.setDescription(params.get("Назначение") == null ? "" : params.get("Назначение"));
        drug.setPrice(parseDouble(doc.select(".regular-price span").html()));
        drug.setType(params.get("Форма товара"));
        drug.setImageUrl(doc.select("#image-main").attr("data-src"));
        drug.setStoreUrl(url);

        return drug;
    }

    private double parseDouble(String number) {
        if (number.equals("")) {
            number = "0.0";
        } else {
            number = number.substring(0, number.indexOf("&"));
        }

        number = number.replaceAll(",", ".");
        return Double.parseDouble(number);
    }
}
