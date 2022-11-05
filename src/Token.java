public class Token {
    String type;
    String value;

    public Token() {
    }

    public Token( String value,String type) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return "(" + value + ", " + type + ")";
//                (,, -1)
    }
}
