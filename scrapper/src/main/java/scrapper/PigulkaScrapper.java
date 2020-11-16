package scrapper;

import database.Drug;
import database.DrugDAO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PigulkaScrapper implements Scrapper {

    private static final String WEBSITE_URL = "https://pigulka.com.ua/";
    private static final int MAXIMUM_PAGES = 2;

    @Override
    public void analyze() throws SQLException, ClassNotFoundException {
        ArrayList<Drug> drugs = new ArrayList<>();

        for (int i = 0; i < MAXIMUM_PAGES; i++) {
            ArrayList<String> drugUrls = findDrugs("https://pigulka.com.ua/ukraina" + (i > 0 ? "?page=" + (i - 1) : ""));

            for (String url : drugUrls) {
                drugs.add(parseDrug(url));
            }
        }

        for (Drug drug : drugs) {
            DrugDAO.insert(drug);
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

        Elements drugUrlElements = doc.select(".pagereloadhref");
        for (Element url : drugUrlElements) {
            drugUrls.add(WEBSITE_URL + url.attr("href"));
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
        Elements propertiesInDom = doc.select(".field--label-inline");
        Map<String, String> params = new HashMap<>();
        for (Element property : propertiesInDom) {
            params.put(property.child(0).html(), property.child(1).html());
        }

        Drug drug = new Drug();
        drug.setName(params.get("Міжнародне непатентоване найменування"));
        drug.setDescription(params.get("Форма випуску"));
        drug.setPrice(0.0);
        drug.setType(params.get("Фармакотерапевтична група"));
        drug.setImageUrl("");
        drug.setStoreUrl(url);

        return drug;
    }
}
