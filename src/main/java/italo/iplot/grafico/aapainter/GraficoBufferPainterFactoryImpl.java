package italo.iplot.grafico.aapainter;

import italo.iplot.grafico.linha.LinhaDrawer;
import italo.iplot.gui.grafico.CoresUtil;
import italo.iplot.gui.grafico.GraficoBufferPainter;
import italo.iplot.gui.grafico.GraficoBufferPainterFactory;
import italo.iplot.gui.grafico.GraficoPixel;

public class GraficoBufferPainterFactoryImpl implements GraficoBufferPainterFactory {
    
    private final LinhaDrawer linhaDrawer;
    private final CoresUtil coresUtil;

    public GraficoBufferPainterFactoryImpl( LinhaDrawer linhaDrawer, CoresUtil coresUtil ) {
        this.linhaDrawer = linhaDrawer;
        this.coresUtil = coresUtil;
    }
      
    @Override
    public GraficoBufferPainter criaGraficoBufferPainterSSAA( GraficoPixel gpx ) {
        return new GraficoBufferPainterSSAA( gpx, linhaDrawer, coresUtil);
    }

    @Override
    public GraficoBufferPainter criaGraficoBufferPainterNormal( GraficoPixel gpx ) {
        return new GraficoBufferPainterNormal( gpx, linhaDrawer, coresUtil);
    }
    
}
