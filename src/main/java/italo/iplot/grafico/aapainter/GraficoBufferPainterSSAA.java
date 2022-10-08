package italo.iplot.grafico.aapainter;

import italo.iplot.gui.grafico.CoresUtil;
import italo.iplot.grafico.linha.LinhaDrawer;
import italo.iplot.gui.grafico.GraficoPixel;

public class GraficoBufferPainterSSAA extends AbstractGraficoBufferPainter {    
    
    public GraficoBufferPainterSSAA( GraficoPixel gpx, LinhaDrawer drawer, CoresUtil coresUtil ) {        
        super( gpx, drawer, coresUtil );        
    }        

    @Override
    public void pintaPixel( int x, int y ) {
        int[] cores = new int[ d*d ];
        for( int k = 0; k < d; k++ )
            for( int l = 0; l < d; l++ )
                cores[ k*d + l ] = buffer.getRGB( (x*d)+l, (y*d)+k );

        int cor = coresUtil.corMedia( cores );
        if ( ( cor & 0x00FFFFFF ) != ( bgcor & 0x00FFFFFF ) )
            gpx.pintaPixel( x, y, cor ); 
    }        

}
