package andrewrimpici.knowyournotes.util;

public enum EnumClefType {

    TREBLE("treble"),
    BASS("bass");


    private String type;
    private EnumClefType(String t) {
        type = t;
    }

    public String getType() {
        return type;
    }
}
