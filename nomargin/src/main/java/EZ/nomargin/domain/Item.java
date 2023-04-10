package EZ.nomargin.domain;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.GenerationType;


@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String itemDetail;
    private String itemName;
    private String itemSellStatus;
    private Integer price;
    private Integer stock;

    private ItemType itemType;

    private String imgName;

    //photo

    public Item() {
    }

    public Item(String itemName, String itemDetail,  Integer price, Integer stock,ItemType itemType, String imgName) {
        this.itemDetail = itemDetail;
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
        this.itemType = itemType;
        this.imgName = imgName;
    }
}
