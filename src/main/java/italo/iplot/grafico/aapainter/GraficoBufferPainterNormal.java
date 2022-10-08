package italo.iplot.grafico.aapainter;

import italo.iplot.grafico.linha.LinhaDrawer;
import italo.iplot.gui.grafico.CoresUtil;
import italo.iplot.gui.grafico.GraficoPixel;

public class GraficoBufferPainterNormal extends AbstractGraficoBufferPainter {

    public GraficoBufferPainterNormal( GraficoPixel gpx, LinhaDrawer drawer, CoresUtil coresUtil ) {
        super( gpx, drawer, coresUtil );
    }

    @Override
    public void pintaPixel( int x, int y ) {
        gpx.pintaPixel( x, y, buffer.getRGB( x, y ) );
    }
    
}
