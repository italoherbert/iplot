package italo.iplot.gui.grafico;

public interface GraficoPixel {
            
    public void pintaPixel( int x, int y, int rgb );
        
    public int getRGB( int x, int y );
    
    public int getLarg();
    
    public int getAlt();
    
}
