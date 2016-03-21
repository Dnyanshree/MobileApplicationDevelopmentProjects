package example.com.listviewdemo;

public class Color {
    String colorName, colorHex;

    public Color(String colorName, String colorHex) {
        this.colorName = colorName;
        this.colorHex = colorHex;
    }

    @Override
    public String toString() {
        return this.colorName;
    }
}
