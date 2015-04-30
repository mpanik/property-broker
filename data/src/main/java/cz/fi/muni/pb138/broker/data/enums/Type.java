package cz.fi.muni.pb138.broker.data.enums;


public enum Type {

    ONE_ZERO("1+0"),
    ONE_KK("1+kk"),
    ONE_ONE("1+1"),
    TWO_KK("2+kk"),
    TWO_ONE("2+1"),
    THREE_KK("3+kk"),
    THREE_ONE("3+1"),
    FOUR_KK("4+kk"),
    FOUR_ONE("4+1"),
    FIVE_KK("5+kk"),
    FIVE_ONE("5+1"),
    FIVE_PLUS("5+1 a více"); //pre sreality = 5+kk, 5+1 , 6 a více , Atypický

    private String text;

     Type(String text) {
         this.text=text;
    }

    public String getText() {
        return this.text;
    }

    public static Type fromString(String text) {
        if (text != null) {
            for (Type t : Type.values()) {
                if (text.equalsIgnoreCase(t.text)) {
                    return t;
                }
            }
        }
        return null;
    }

}
