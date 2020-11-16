import scrapper.PigulkaScrapper;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        PigulkaScrapper scrapper = new PigulkaScrapper();
        try {
            scrapper.analyze();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
