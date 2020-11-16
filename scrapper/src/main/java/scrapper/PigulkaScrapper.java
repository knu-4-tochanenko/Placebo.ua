package scrapper;

import database.Drug;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PigulkaScrapper implements Scrapper {

    @Override
    public void analyze() {
        parseDrug("https://pigulka.com.ua/lz/l-glutaminova-kislota-0");
    }

    private String[] findDrugs(String pageUrl) {
        return null;
    }

    private Drug parseDrug(String url) {

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(doc.title());
        Elements newsHeadlines = doc.select(".field--label-inline");
        Map<String, String> params = new HashMap<>();
        for (Element headline : newsHeadlines) {
//            System.out.println(headline.child(0).html() + " - " + headline.child(1).html());
            params.put(headline.child(0).html(), headline.child(1).html());
        }

        Drug drug = new Drug();
        drug.setName(params.get("Міжнародне непатентоване найменування"));
        drug.setDescription(params.get("Форма випуску"));
        drug.setPrice(0.0);
        drug.setType(params.get("Фармакотерапевтична група"));
        drug.setImageUrl("");
        drug.setStoreUrl(url);

        System.out.println(drug);
        return drug;
    }
}
