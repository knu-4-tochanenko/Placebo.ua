import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://pigulka.com.ua/lz/tivorel").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(doc.title());
        Elements newsHeadlines = doc.select(".field--label-inline");
        for (Element headline : newsHeadlines) {
//            System.out.println(headline.html() + "\n\n\n");
            System.out.println("\n\t" + headline.child(0).html() + " - " + headline.child(1).html());
        }
    }
}
