package andrewrimpici.knowyournotes;

public enum EnumLetterType {

    C("c"),
    D("d"),
    E("e"),
    F("f"),
    G("g"),
    A("a"),
    B("b");

    private String letterType;
    private EnumLetterType(String type) {
        letterType = type;
    }

    public String getLetterType() {
        return letterType;
    }
}
