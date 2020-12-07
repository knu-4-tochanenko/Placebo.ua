package scrapper;

import config.Config;
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

public class Apteka911Scrapper implements Scrapper {

    private static final String WEBSITE_URL = "https://apteka911.com.ua/";
    private static final int MAXIMUM_PAGES = Config.PAGES;

    @Override
    public void analyze() throws SQLException, ClassNotFoundException {
        for (int i = 0; i < MAXIMUM_PAGES; i++) {
            ArrayList<String> drugUrls = findDrugs("https://apteka911.com.ua/shop/lekarstvennyie-preparatyi/prostuda_i_gripp/" + (i > 0 ? "page=" + (i + 1) : ""));

            for (String url : drugUrls) {
                try {
                    DrugDAO.insert(parseDrug(url));
                } catch (IOException e) {
                    System.err.println("ERROR WHILE PARSING " + url);
                    System.out.println("PARSING SKIPPED\n");
                }
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

        Elements drugUrlElements = doc.select(".b-prod__thumb");

        for (Element url : drugUrlElements) {
            drugUrls.add(url.attr("href"));
        }

        return drugUrls;
    }

    private Drug parseDrug(String url) throws IOException {
        Document doc = null;
        doc = Jsoup.connect(url).get();

        String priceStr = doc.select(".card-price .price-new").html();
        double price = Double.parseDouble(priceStr.substring(0, priceStr.indexOf(" ")));


        Elements propertiesInDom = doc.select(".parameter-table tr");
        Map<String, String> params = new HashMap<>();

        String title = "";
        for (Element property : propertiesInDom) {
            if (!property.child(0).children().isEmpty()) {
                title = property.child(0).child(0).html();
            } else {
                title = property.child(0).html();
            }

            if (title.equals("Категория")) {
                params.put(title, property.child(1).select("p a").html());
            } else {
                params.put(title, property.child(1).html());
            }
        }

        String pageTitle = doc.title();
        Drug drug = new Drug();
        if (!pageTitle.contains(" -")) {
            drug.setName(pageTitle);
        } else {
            drug.setName(pageTitle.substring(0, pageTitle.indexOf(" -")));
        }
        drug.setDescription(params.get("Категория"));
        drug.setPrice(price);
        drug.setType(params.get("Вид упаковки") == null ? "Стандартно" : params.get("Вид упаковки"));
        drug.setImageUrl(doc.select(".cboxElement").attr("href"));
        drug.setStoreUrl(url);

        return drug;
    }
}
