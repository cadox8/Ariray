package es.cadox8.ariray.datatype;

public enum Formula {
    LINEAR, EXPONENTIAL, UNKNOWN;

    public static Formula getFormulaType(String type) {
        try {
            return valueOf(type);
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }
}
