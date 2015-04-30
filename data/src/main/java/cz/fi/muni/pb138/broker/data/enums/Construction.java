package cz.fi.muni.pb138.broker.data.enums;


public enum Construction {
    BRICK("Cihla"),
    CONCRETE("Panel"),
    OTHER("Ostatní");

    private String text;

    Construction(String text) {
        this.text=text;
    }


    public String getText() {
        return this.text;
    }

    public static Construction fromString(String text) {
        if (text != null) {
            for (Construction c : Construction.values()) {
                if (text.equalsIgnoreCase(c.text)) {
                    return c;
                }
            }
        }
        return null;
    }


}
