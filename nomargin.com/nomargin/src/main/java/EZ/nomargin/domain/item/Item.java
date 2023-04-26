package EZ.nomargin.domain.item;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.GenerationType;
import java.util.List;


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
    private String mainImg;
    private String detailImg1;
    private String detailImg2;
    private String detailImg3;
    private String sizeImg;


    public Item() {
    }

    public Item(String itemName, String itemStore, Integer price, Integer stock, ItemType itemType , String mainImg,
                String detailImg1, String detailImg2, String detailImg3, String sizeImg ) {
        this.itemName = itemName;
        this.itemStore = itemStore;
        this.price = price;
        this.stock = stock;
        this.itemType = itemType;
        this.mainImg = mainImg;
        this.detailImg1 = detailImg1;
        this.detailImg2 = detailImg2;
        this.detailImg3 = detailImg3;
        this.sizeImg = sizeImg;
    }
}