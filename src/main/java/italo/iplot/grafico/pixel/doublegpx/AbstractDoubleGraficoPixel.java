package italo.iplot.grafico.pixel.doublegpx;

import italo.iplot.gui.grafico.GraficoPixel;
import italo.iplot.gui.grafico.DoubleGraficoPixel;

public abstract class AbstractDoubleGraficoPixel implements DoubleGraficoPixel {
    
    protected final GraficoPixel gpx;
    
    public AbstractDoubleGraficoPixel( GraficoPixel gpx ) {
        this.gpx = gpx;
    }
    
    @Override
    public GraficoPixel getGraficoPixel() {
        return gpx;
    }
    
}
