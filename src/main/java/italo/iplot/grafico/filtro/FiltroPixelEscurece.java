package italo.iplot.grafico.filtro;

import italo.iplot.gui.grafico.FiltroPixel;
import italo.iplot.gui.grafico.CoresUtil;
import italo.iplot.gui.grafico.GraficoPixel;

public class FiltroPixelEscurece implements FiltroPixel {

    private final CoresUtil coresUtil;
    private final int reduzirEm;

    public FiltroPixelEscurece( CoresUtil coresUtil, int reduzirEm ) {
        this.coresUtil = coresUtil;
        this.reduzirEm = reduzirEm;
    }
    
    @Override
    public void pintaPixels( GraficoPixel gpx, int x, int y ) { 
        int cor = gpx.getRGB( x, y );
        int rgb = coresUtil.escureceCor( cor, reduzirEm );
        gpx.pintaPixel( x, y, rgb );
    }
    
}
