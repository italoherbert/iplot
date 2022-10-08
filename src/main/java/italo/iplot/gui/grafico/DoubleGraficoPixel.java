package italo.iplot.gui.grafico;

import italo.iplot.gui.grafico.GraficoPixel;

public interface DoubleGraficoPixel {
    
    public void pintaPixel( double x, double y, int rgb );
    
    public GraficoPixel getGraficoPixel();
    
}
