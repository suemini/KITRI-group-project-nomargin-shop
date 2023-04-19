package EZ.nomargin.domain.item;

public enum ItemType {

    Top("상의"),
    Bottom("하의"),
    Outer("외투");
<<<<<<<< HEAD:nomargin.com/nomargin/nomargin.com/nomargin/src/main/java/EZ/nomargin/domain/item/ItemType.java

    
========
>>>>>>>> kyungjins-patch:nomargin/src/main/java/EZ/nomargin/domain/item/ItemType.java
    private final String description;
    ItemType(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

}
