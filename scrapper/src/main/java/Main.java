import scrapper.AddScrapper;
import scrapper.Apteka911Scrapper;
import scrapper.PigulkaScrapper;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Apteka911Scrapper scrapper = new Apteka911Scrapper();
        try {
            scrapper.analyze();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
