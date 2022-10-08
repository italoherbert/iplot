package italo.iplot.grafico.pixel.doublegpx;

import italo.iplot.gui.grafico.GraficoPixel;

public class DoubleGraficoPixelRound extends AbstractDoubleGraficoPixel {
    
    public DoubleGraficoPixelRound( GraficoPixel gpx ) {
        super( gpx );
    }

    @Override
    public void pintaPixel( double x, double y, int rgb ) {
        gpx.pintaPixel( (int)Math.round( x ), (int)Math.round( y ), rgb );
    }
    
}
