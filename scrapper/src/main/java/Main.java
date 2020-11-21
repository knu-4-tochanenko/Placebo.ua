import scrapper.AddScrapper;
import scrapper.PigulkaScrapper;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        AddScrapper scrapper = new AddScrapper();
        try {
            scrapper.analyze();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
