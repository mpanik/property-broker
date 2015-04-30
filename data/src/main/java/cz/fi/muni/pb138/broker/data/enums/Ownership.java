package cz.fi.muni.pb138.broker.data.enums;

public enum Ownership {

    PERSONAL("Osobní"),
    COOPERATIVE("Družstevní"),
    OTHER("Jiné");       //sreality = Státní/obecní

    private String text;

    Ownership(String text) {
        this.text=text;
    }


    public String getText() {
        return this.text;
    }

    public static Ownership fromString(String text) {
        if (text != null) {
            for (Ownership o : Ownership.values()) {
                if (text.equalsIgnoreCase(o.text)) {
                    return o;
                }
            }
        }
        return null;
    }

}
