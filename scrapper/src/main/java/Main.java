import scrapper.AddScrapper;
import scrapper.Apteka911Scrapper;
import scrapper.PigulkaScrapper;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        AddScrapper addScrapper = new AddScrapper();
        Apteka911Scrapper apteka911Scrapper = new Apteka911Scrapper();
        PigulkaScrapper pigulkaScrapper = new PigulkaScrapper();
        try {
            addScrapper.analyze();
            apteka911Scrapper.analyze();
            pigulkaScrapper.analyze();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
