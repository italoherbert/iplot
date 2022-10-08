package italo.iplot.grafico.filtro;

import italo.iplot.gui.grafico.FiltroPixel;
import italo.iplot.gui.grafico.CoresUtil;
import italo.iplot.gui.grafico.GraficoPixel;

public class FiltroPixelMax implements FiltroPixel {

    private final GraficoPixel buffer;
    private final CoresUtil coresUtil;
    private final int d;

    public FiltroPixelMax( GraficoPixel buffer, CoresUtil coresUtil, int d) {
        this.buffer = buffer;
        this.coresUtil = coresUtil;
        this.d = d;
    }
    
    @Override
    public void pintaPixels( GraficoPixel gpx, int x, int y ) {          
        int maxI = 0;
        int maxJ = 0;
        int max = Integer.MIN_VALUE;
        for( int i = -d; i <= d; i++ ) {
            for( int j = -d; j <= d; j++ ) {
                if ( x+j >= 0 && x+j < buffer.getLarg() && y+i >= 0 && y+i < buffer.getAlt() ) {
                    int rgb = buffer.getRGB( x+j, y+i );
                    int sinza = coresUtil.getSinza( rgb );
                    if ( sinza > max ) {
                        maxI = y+i;
                        maxJ = x+j;
                        max = sinza;
                    }
                }
            }
        }
        gpx.pintaPixel( x, y, gpx.getRGB( maxI, maxJ ) ); 
    }
    
}
