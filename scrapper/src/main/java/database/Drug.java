package database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drug {
    private int id;
    private String name;
    private String type;
    private String description;
    private double price;
    private String storeUrl;
    private String imageUrl;

    public String toString() {
        return name + " : {\n\ttype:\t" + type + "\n\tdesc:\t" + description + "\n\tprice:\t" + price + "\n\tstore:\t" + storeUrl + "\n\timage:\t" + imageUrl + "\n}\n\n";
    }
}
