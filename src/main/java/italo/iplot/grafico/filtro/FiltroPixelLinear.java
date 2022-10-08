package italo.iplot.grafico.filtro;

import italo.iplot.gui.grafico.FiltroPixel;
import italo.iplot.gui.grafico.FiltroManager;
import italo.iplot.gui.grafico.GraficoPixel;

public class FiltroPixelLinear implements FiltroPixel {

    private final FiltroManager filtroManager;  
    private final FiltroConfig cfg;
    private final GraficoPixel buffer;
    private final double[][] mascara;

    public FiltroPixelLinear( FiltroManager filtroManager, GraficoPixel buffer, double[][] mascara, FiltroConfig cfg ) {
        this.filtroManager = filtroManager;
        this.buffer = buffer;
        this.mascara = mascara;
        this.cfg = cfg;
    }
    
    @Override
    public void pintaPixels( GraficoPixel gpx, int x, int y ) {
        int rgb = filtroManager.corAplicaMascara( buffer, x, y, cfg.getX1(), cfg.getY1(), cfg.getX2(), cfg.getY2(), mascara );
        gpx.pintaPixel( x, y, rgb ); 
    }
    
}