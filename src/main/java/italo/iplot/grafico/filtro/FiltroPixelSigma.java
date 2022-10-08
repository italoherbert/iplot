package italo.iplot.grafico.filtro;

import italo.iplot.gui.grafico.FiltroPixel;
import italo.iplot.gui.grafico.CoresUtil;
import italo.iplot.gui.grafico.GraficoPixel;

public class FiltroPixelSigma implements FiltroPixel {
    
    private final CoresUtil coresUtil;
    private final GraficoPixel buffer;
    private final int d;
    private final int tolerancia;

    public FiltroPixelSigma( GraficoPixel buffer, CoresUtil coresUtil, int d, int tolerancia ) {
        this.buffer = buffer;
        this.coresUtil = coresUtil;
        this.d = d;
        this.tolerancia = tolerancia;
    }

    @Override
    public void pintaPixels( GraficoPixel gpx, int x, int y ) {        
        int csinza = coresUtil.getSinza( gpx.getRGB( (int)x, (int)y ) );
                
        int r = 0;
        int g = 0;
        int b = 0;
        int cont = 0;
        for( int i = -d; i <= d; i++ ) {
            for( int j = -d; j <= d; j++ ) {
                if ( x+j >= 0 && x+j < buffer.getLarg() && y+i >= 0 && y+i < buffer.getAlt() ) {
                    int rgb = buffer.getRGB( x+j, y+i );
                    int sinza = coresUtil.getSinza( rgb );
                    if ( Math.abs( sinza - csinza ) <= tolerancia ) {
                        r += coresUtil.getR( rgb );
                        g += coresUtil.getG( rgb );
                        b += coresUtil.getB( rgb );
                        cont++;
                    }
                }
            }
        }
        if ( cont > 0 ) {
            r /= cont;
            g /= cont;
            b /= cont;
        }
        
        gpx.pintaPixel( x, y, coresUtil.getRGB( r, g, b ) );
    }        
    
}
