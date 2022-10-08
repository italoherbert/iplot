package italo.iplot.gui.grafico;

public interface GraficoBufferPainter {

    public void iniciaBuffer();

    public void iniciaBuffer( int d );
        
    public void iniciaBuffer( int bgcor, int d );
            
    public void desenhaLinha( DoubleGraficoPixel dgpx, double[] p0, double[] p1, int cor );
    
    public void pintaBuffer();
    
    public GraficoPixel getGraficoPixel();
    
    public GraficoPixel getBufferGraficoPixel();
    
    public int getMultFator();    
    
}
