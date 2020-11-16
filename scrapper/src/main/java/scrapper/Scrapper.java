package scrapper;

import java.sql.SQLException;

public interface Scrapper {
    void analyze() throws SQLException, ClassNotFoundException;
}
