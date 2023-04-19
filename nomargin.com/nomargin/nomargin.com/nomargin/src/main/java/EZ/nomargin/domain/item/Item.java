package EZ.nomargin.domain.item;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.GenerationType;
<<<<<<<< HEAD:nomargin.com/nomargin/nomargin.com/nomargin/src/main/java/EZ/nomargin/domain/item/Item.java
========
import java.util.ArrayList;
import java.util.List;
>>>>>>>> kyungjins-patch:nomargin/src/main/java/EZ/nomargin/domain/item/Item.java


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
<<<<<<<< HEAD:nomargin.com/nomargin/nomargin.com/nomargin/src/main/java/EZ/nomargin/domain/item/Item.java

    //photo
========
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemColor> color = new ArrayList<>();
    @ElementCollection
    private List<String> itemSize;


>>>>>>>> kyungjins-patch:nomargin/src/main/java/EZ/nomargin/domain/item/Item.java

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
