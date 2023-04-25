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
    private String imgName;
    private String imgDetail1;
    private String imgDetail2;
    private String imgDetail3;
    private String sizeImg;

    @Embedded
    private UploadFile attachFile;
    @ElementCollection
    private List<UploadFile> imageFiles;


    public Item() {
    }

    public Item(String itemName, String itemStore, Integer price, Integer stock, ItemType itemType ,String imgName,
                String imgDetail1, String imgDetail2, String imgDetail3, String sizeImg ) {
        this.itemName = itemName;
        this.itemStore = itemStore;
        this.price = price;
        this.stock = stock;
        this.itemType = itemType;
        this.imgName = imgName;
        this.imgDetail1 = imgDetail1;
        this.imgDetail2 = imgDetail2;
        this.imgDetail3 = imgDetail3;
        this.sizeImg = sizeImg;

    }
}