package italo.iplot.gui.grafico;

import java.awt.Color;

public interface CoresUtil {
    
    public int misturaCores( int rgb1, int rgb2, double cf );
    
    public int escureceCor( int rgb, int redizirEm );
        
    public Color color( int rgb );
    
    public int getR( int rgb );
    
    public int getG( int rgb );
    
    public int getB( int rgb );
    
    public int getRGB( int r, int g, int b );
    
    public int getSinza( int rgb );
    
    public int corMedia( int... cores );
    
}
