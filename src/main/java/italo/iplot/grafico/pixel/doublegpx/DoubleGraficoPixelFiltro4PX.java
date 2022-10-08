package italo.iplot.grafico.pixel.doublegpx;

import italo.iplot.gui.grafico.FiltroPixel;
import italo.iplot.gui.grafico.GraficoPixel;

public class DoubleGraficoPixelFiltro4PX extends AbstractDoubleGraficoPixel {
    
    private final FiltroPixel fpx;

    public DoubleGraficoPixelFiltro4PX( GraficoPixel gpx, FiltroPixel fpx ) {
        super( gpx );
        this.fpx = fpx;
    }
    
    @Override
    public void pintaPixel( double x, double y, int rgb ) {        
        int rx = (int)Math.round( x );
        int ry = (int)Math.round( y );
                                        
        if ( y-ry < 0.5 ) {
            if ( x-rx < 0.5 ) {                
                fpx.pintaPixels( gpx, rx-1, ry-1 );
                fpx.pintaPixels( gpx, rx  , ry-1 );
                fpx.pintaPixels( gpx, rx-1, ry   );
                fpx.pintaPixels( gpx, rx  , ry   );                                
            } else {                
                fpx.pintaPixels( gpx, rx  , ry-1 );
                fpx.pintaPixels( gpx, rx+1, ry-1 );
                fpx.pintaPixels( gpx, rx  , ry   );
                fpx.pintaPixels( gpx, rx+1, ry   );
            }
        } else {
            if ( x-rx < 0.5 ) {                
                fpx.pintaPixels( gpx, rx-1, ry   );
                fpx.pintaPixels( gpx, rx  , ry   );
                fpx.pintaPixels( gpx, rx-1, ry+1 );
                fpx.pintaPixels( gpx, rx  , ry+1 );                
            } else {               
                fpx.pintaPixels( gpx, rx  , ry   );
                fpx.pintaPixels( gpx, rx+1, ry   );
                fpx.pintaPixels( gpx, rx  , ry+1 );
                fpx.pintaPixels( gpx, rx+1, ry+1 );                
            }
        }                
    }
    
}
