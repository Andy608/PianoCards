package andrewrimpici.knowyournotes;

public enum EnumLetterType {

    A("a"),
    B("b"),
    C("c"),
    D("d"),
    E("e"),
    F("f"),
    G("g");

    private String letterType;
    private EnumLetterType(String type) {
        letterType = type;
    }

    public String getLetterType() {
        return letterType;
    }
}
