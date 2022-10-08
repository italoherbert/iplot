package italo.iplot.grafico.pixel.gpx;

import italo.iplot.gui.grafico.GraficoPixel;

public abstract class AbstractGraficoPixel implements GraficoPixel {
 
    protected final GraficoPixel gpx;

    public AbstractGraficoPixel(GraficoPixel gpx) {
        this.gpx = gpx;
    }

    @Override
    public int getRGB(int x, int y) {
        return gpx.getRGB( x, y );
    }

    @Override
    public int getLarg() {
        return gpx.getLarg();
    }

    @Override
    public int getAlt() {
        return gpx.getAlt();
    }
    
}
