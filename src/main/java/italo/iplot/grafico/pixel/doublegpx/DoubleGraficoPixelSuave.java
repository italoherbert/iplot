package italo.iplot.grafico.pixel.doublegpx;

import italo.iplot.gui.grafico.CoresUtil;
import italo.iplot.gui.grafico.GraficoPixel;

public class DoubleGraficoPixelSuave extends AbstractDoubleGraficoPixel {

    private final CoresUtil coresUtil;

    public DoubleGraficoPixelSuave( GraficoPixel gpx, CoresUtil coresUtil ) {
        super( gpx );
        this.coresUtil = coresUtil;
    }
    
    @Override
    public void pintaPixel( double x, double y, int rgb ) {
        double roundx = Math.round( x );
        double roundy = Math.round( y );
        double porcentX = 1.0d - Math.abs( x - roundx );
        double porcentY = 1.0d - Math.abs( y - roundy );
        double porcent = porcentX * porcentY;

        int bgcor = gpx.getRGB( (int)roundx, (int)roundy );                                                                                                                                                                                
        int c = coresUtil.misturaCores( rgb, bgcor, porcent );
                
        gpx.pintaPixel( (int)roundx, (int)roundy, c );
    }
            
}
