package italo.iplot.grafico.texto;

import java.awt.Font;

public class Texto2D {
    
    private final int x;
    private final int y;
    private final String texto;
    private final Font font;
    private final double rotAng;

    public Texto2D(int x, int y, String texto, Font font, double rotAng) {
        this.x = x;
        this.y = y;
        this.texto = texto;
        this.font = font;
        this.rotAng = rotAng;
    }

    public double getRotAng() {
        return rotAng;
    }

    public Font getFont() {
        return font;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getTexto() {
        return texto;
    }
    
}
