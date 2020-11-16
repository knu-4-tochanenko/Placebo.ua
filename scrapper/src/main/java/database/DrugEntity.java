package database;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DrugEntity {
    private int id;
    private String name;
    private String type;
    private String description;
    private double price;
    private String storeUrl;
    private String imageUrl;
}
