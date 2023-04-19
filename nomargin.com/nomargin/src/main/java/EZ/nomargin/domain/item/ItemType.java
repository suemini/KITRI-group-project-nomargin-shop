package EZ.nomargin.domain.item;

public enum ItemType {

    Top("상의"),
    Bottom("하의"),
    Outer("아우터");

    
    private final String description;
    ItemType(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

}
