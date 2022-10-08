package italo.iplot.grafico.filtro;

import italo.iplot.gui.grafico.DoubleFiltroPixel;
import italo.iplot.gui.grafico.CoresUtil;
import italo.iplot.gui.grafico.GraficoPixel;

public class DoubleFiltroPixelAntialiasing implements DoubleFiltroPixel {
    
    private final GraficoPixel buffer;
    private final CoresUtil coresUtil;

    public DoubleFiltroPixelAntialiasing( GraficoPixel buffer, CoresUtil coresUtil ) {
        this.buffer = buffer;
        this.coresUtil = coresUtil;
    }

    @Override
    public void pintaPixels( GraficoPixel gpx, double x, double y ) {
        int rx = (int)Math.round( x );
        int ry = (int)Math.round( y );
                                
        double xx = Math.abs( x-rx );
        double yy = Math.abs( y-ry );
        
        double f1 = xx+yy;
        double f11 = 1.0d - f1;
        double f12 = 2*(xx+yy);
        
        double f2 = f11 * ( xx / f12 );
        double f3 = f11 * ( yy / f12 );
        double f4 = f11 * ( f1 / f12 );
                
        double af, bf, cf, df;
                
        int rgb1, rgb2, rgb3, rgb4;       
        if ( y-ry < 0.5 ) {
            if ( x-rx < 0.5 ) {                
                rgb1 = buffer.getRGB( rx-1, ry-1 );
                rgb2 = buffer.getRGB( rx  , ry-1 );
                rgb3 = buffer.getRGB( rx-1, ry   );
                rgb4 = buffer.getRGB( rx  , ry   );                
                af = f1;
                bf = f2;
                cf = f3;
                df = f4;
            } else {                
                rgb1 = buffer.getRGB( rx  , ry-1 );
                rgb2 = buffer.getRGB( rx+1, ry-1 );
                rgb3 = buffer.getRGB( rx  , ry   );
                rgb4 = buffer.getRGB( rx+1, ry   );
                af = f2;
                bf = f1;
                cf = f4;
                df = f3;
            }
        } else {
            if ( x-rx < 0.5 ) {                
                rgb1 = buffer.getRGB( rx-1, ry   );
                rgb2 = buffer.getRGB( rx  , ry   );
                rgb3 = buffer.getRGB( rx-1, ry+1 );
                rgb4 = buffer.getRGB( rx  , ry+1 );
                af = f3;
                bf = f4;
                cf = f1;
                df = f2;
            } else {               
                rgb1 = buffer.getRGB( rx  , ry   );
                rgb2 = buffer.getRGB( rx+1, ry   );
                rgb3 = buffer.getRGB( rx  , ry+1 );
                rgb4 = buffer.getRGB( rx+1, ry+1 );
                af = f4;
                bf = f3;
                cf = f2;
                df = f1;
            }
        }
                
        int r1 = (int)Math.round( af * coresUtil.getR( rgb1 ) );
        int g1 = (int)Math.round( af * coresUtil.getG( rgb1 ) );
        int b1 = (int)Math.round( af * coresUtil.getB( rgb1 ) );
                                
        int r2 = (int)Math.round( bf * coresUtil.getR( rgb2 ) );
        int g2 = (int)Math.round( bf * coresUtil.getG( rgb2 ) );
        int b2 = (int)Math.round( bf * coresUtil.getB( rgb2 ) );
        
        int r3 = (int)Math.round( cf * coresUtil.getR( rgb3 ) );
        int g3 = (int)Math.round( cf * coresUtil.getG( rgb3 ) );
        int b3 = (int)Math.round( cf * coresUtil.getB( rgb3 ) );
        
        int r4 = (int)Math.round( df * coresUtil.getR( rgb4 ) );
        int g4 = (int)Math.round( df * coresUtil.getG( rgb4 ) );
        int b4 = (int)Math.round( df * coresUtil.getB( rgb4 ) );
        
        int r = r1 + r2 + r3 + r4;
        int g = g1 + g2 + g3 + g4;
        int b = b1 + b2 + b3 + b4;
                        
        gpx.pintaPixel( rx, ry, coresUtil.getRGB( r, g, b ) );
    }    
    
}
