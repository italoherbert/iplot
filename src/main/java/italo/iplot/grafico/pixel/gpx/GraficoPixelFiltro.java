package italo.iplot.grafico.pixel.gpx;

import italo.iplot.grafico.pixel.condicao.GPintaPixelCondicao;
import italo.iplot.gui.grafico.GraficoPixel;
import italo.iplot.gui.grafico.FiltroPixel;

public class GraficoPixelFiltro extends AbstractGraficoPixel {
    
    private final FiltroPixel fpx;
    private final GPintaPixelCondicao c;
    private final int d;

    public GraficoPixelFiltro( GraficoPixel gpx, FiltroPixel fpx, GPintaPixelCondicao cond, int d ) {
        super( gpx );
        this.fpx = fpx;
        this.c = cond;
        this.d = d;
    }
    
    @Override
    public void pintaPixel(int x, int y, int rgb) {  
        for( int i = -d; i <= d; i++ )
            for( int j = -d; j <= d; j++ )
                if ( c.condicao( x+j, y+i ) )
                    fpx.pintaPixels( gpx, x+j, y+i ); 
    }
    
}
