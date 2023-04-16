package EZ.nomargin.domain.item;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.GenerationType;


@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String itemName;
    private String itemStore;
    private Integer price;
    private Integer stock;
    private ItemType itemType;
    private String imgName;

    //photo

    public Item() {
    }

    public Item(String itemName, String itemStore,  Integer price, Integer stock,ItemType itemType, String imgName) {
        this.itemName = itemName;
        this.itemStore = itemStore;
        this.price = price;
        this.stock = stock;
        this.itemType = itemType;
        this.imgName = imgName;
    }
}
