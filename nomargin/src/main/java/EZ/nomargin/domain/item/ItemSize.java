package EZ.nomargin.domain.item;

public enum ItemSize {
    S("90"),
    M("95"),
    L("100"),
    XL("105");

    private final String description;
    ItemSize(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

}
