package italo.iplot.grafico.filtro;

import italo.iplot.gui.grafico.FiltroPixel;
import italo.iplot.gui.grafico.CoresUtil;
import italo.iplot.gui.grafico.DoubleFiltroPixel;
import italo.iplot.gui.grafico.FiltroManager;
import italo.iplot.gui.grafico.GraficoPixel;

public class FiltroManagerImpl implements FiltroManager {
    
    private final CoresUtil coresUtil;
        
    public FiltroManagerImpl( CoresUtil coresUtil ) {        
        this.coresUtil = coresUtil;
    }
     
    @Override
    public DoubleFiltroPixel criaFiltroPixelAntialiasing( GraficoPixel buffer ) {
        return new DoubleFiltroPixelAntialiasing( buffer, coresUtil );
    }
    
    @Override
    public FiltroPixel criaFiltroPixelEscurece( GraficoPixel buffer, int reduzirEm ) {
        return new FiltroPixelEscurece( coresUtil, reduzirEm );
    }
    
    @Override
    public FiltroPixel criaFiltroPixelSigma( GraficoPixel buffer, int d, int tolerancia ) {
        return new FiltroPixelSigma( buffer, coresUtil, d, tolerancia );
    }
    
    @Override
    public FiltroPixel criaFiltroPixelMax( GraficoPixel buffer, int d ) {
        return new FiltroPixelMax( buffer, coresUtil, d );
    }
    
    @Override
    public FiltroPixel criaFiltroPixelGaussiana3x3( GraficoPixel buffer, FiltroConfig cfg ) {
        double[][] mascara = {
            { 1, 2, 1 },
            { 2, 5, 2 },
            { 1, 2, 1 }
        };
        
        for( int i = 0; i < mascara.length; i++ )
            for( int j = 0; j < mascara[i].length; j++ )
                mascara[i][j] /= 17.0d;
        
        return this.criaFiltroPixelLinear( buffer, mascara, cfg );
    }
        
    @Override
    public FiltroPixel criaFiltroPixelMediaPassaAlta3x3( GraficoPixel buffer, FiltroConfig cfg ) {
        double[][] mascara = {
            { -1,  0, -1 },
            {  0,  5,  0 },
            { -1,  0, -1 }
        };
        
        return this.criaFiltroPixelLinear( buffer, mascara, cfg );
    }
    
    @Override
    public FiltroPixel criaFiltroPixelMediaPassaBaixa3x3( GraficoPixel buffer, FiltroConfig cfg ) {
        double[][] mascara = {
            { 1, 1, 1 },
            { 1, 1, 1 },
            { 1, 1, 1 }
        };
        for( int i = 0; i < mascara.length; i++ )
            for( int j = 0; j < mascara[i].length; j++ )
                mascara[i][j] /= 9.0d;
        
        return this.criaFiltroPixelLinear( buffer, mascara, cfg );
    }
    
    @Override
    public FiltroPixel criaFiltroPixelLinear( GraficoPixel buffer, double[][] mascara, FiltroConfig cfg ) {
        return new FiltroPixelLinear( this, buffer, mascara, cfg );
    }        

    @Override
    public void aplicaFiltroMediaPassaAlta3x3( GraficoPixel gpx, GraficoPixel buffer, int x1, int y1, int x2, int y2 ) {
        double[][] mascara = {
            { 0, -1, 0 },
            {-1, 5, -1 },
            { 0, -1, 0 }
        };
        
        this.aplicaFiltro( gpx, buffer, x1, y1, x2, y2, mascara );
    }
    
    @Override
    public void aplicaFiltroMediaPassaBaixa( GraficoPixel gpx, GraficoPixel buffer, int x1, int y1, int x2, int y2, int d ) {        
        int dim = 2*d+1;
        double[][] mascara = new double[ dim ][ dim ];                        
        
        double f = 1.0d / Math.pow( dim, 2 );
        
        for( int i = -d; i <= d; i++ ) {
            for( int j = -d; j <= d; j++ ) {
                mascara[ i+d ][ j+d ] = f;
            }
        }
        
        this.aplicaFiltro( gpx, buffer, x1, y1, x2, y2, mascara );
    }        
        
    @Override
    public void aplicaFiltroGaussiana3x3( GraficoPixel gpx, GraficoPixel buffer, int x1, int y1, int x2, int y2 ) {
        double[][] mascara = {
            { 1, 2, 1 },
            { 2, 5, 2 },
            { 1, 2, 1 }
        };
        
        for( int i = 0; i < mascara.length; i++ )
            for( int j = 0; j < mascara[i].length; j++ )
                mascara[i][j] /= 17.0d;
        
        this.aplicaFiltro( gpx, buffer, x1, y1, x2, y2, mascara );
    }

    @Override
    public void aplicaFiltroGaussiana5x5( GraficoPixel gpx, GraficoPixel buffer, int x1, int y1, int x2, int y2 ) {
        double[][] mascara = {
            { 1, 4 , 7 , 4 , 1 },
            { 4, 16, 26, 16, 4 },
            { 7, 26, 41, 26, 7 },
            { 4, 16, 26, 16, 4 },
            { 1, 4 , 7 , 4 , 1 }
        };
        
        for( int i = 0; i < mascara.length; i++ )
            for( int j = 0; j < mascara[i].length; j++ )
                mascara[i][j] /= 273.0d;
        
        this.aplicaFiltro( gpx, buffer, x1, y1, x2, y2, mascara );
    }
    
    @Override
    public void aplicaFiltroGaussiana( GraficoPixel gpx, GraficoPixel buffer, int x1, int y1, int x2, int y2, int d, double desvio ) {
        double[][] mascara = new double[ 2*d+1 ][ 2*d+1 ];                
        
        double soma = 0;
        for( int i = -d; i <= d; i++ ) {
            for( int j = -d; j <= d; j++ ) {
                mascara[ i+d ][ j+d ] = ( 1.0d / ( 2*Math.PI*Math.pow( desvio, 2 ) ) ) * Math.exp( - ( Math.pow( i, 2 ) + Math.pow( j, 2 ) ) / ( 2*Math.pow( desvio, 2 ) ) );
                soma += mascara[ i+d ][ j+d ];
            }
        }
        
        double f = 1.0d / soma;
        for( int i = 0; i < mascara.length; i++ ) 
            for( int j = 0; j < mascara[i].length; j++ )
                mascara[i][j] *= f;
        
        this.aplicaFiltro( gpx, buffer, mascara );
    }

    @Override
    public void aplicaFiltro( GraficoPixel gpx, GraficoPixel buffer, double[][] mascara ) {
        this.aplicaFiltro( gpx, buffer, 0, 0, gpx.getLarg()-1, gpx.getAlt()-1, mascara );
    }
            
    @Override
    public void aplicaFiltro(GraficoPixel gpx, GraficoPixel buffer, int x1, int y1, int x2, int y2, double[][] mascara ) {                                
        for( int x = x1; x <= x2; x++ ) {
            for( int y = y1; y <= y2; y++ ) {
                int rgb = this.corAplicaMascara( gpx, buffer, x, y, mascara );                
                gpx.pintaPixel( x, y, rgb ); 
            }
        }
    }
    
    @Override
    public int corAplicaMascara( GraficoPixel gpx, GraficoPixel buffer, int x, int y, double[][] mascara ) {
        return this.corAplicaMascara( buffer, x, y, 0, 0, gpx.getLarg()-1, gpx.getAlt()-1, mascara );
    }
    
    @Override
    public int corAplicaMascara( GraficoPixel buffer, int x, int y, double[][] mascara, FiltroConf cfg ) {
        return this.corAplicaMascara( buffer, x, y, cfg.getX1(), cfg.getY1(), cfg.getX2(), cfg.getY2(), mascara );
    }
    
    @Override
    public int corAplicaMascara( GraficoPixel buffer, int x, int y, int x1, int y1, int x2, int y2, double[][] mascara ) {
        int d = mascara.length / 2;
        
        int r = 0;
        int g = 0;
        int b = 0;
        for( int i = -d; i <= d; i++ ) {                    
            for( int j = -d; j <= d; j++ ) {
                if ( x+j >= x1 && x+j <= x2 && y+i >= y1 && y+i <= y2 ) { 
                    double masc = mascara[ i+d ][ j+d ];                                                
                    r += (int)Math.round( ( ( 0x00FF0000 & buffer.getRGB( x+j, y+i ) ) >> 16 ) * masc );
                    g += (int)Math.round( ( ( 0x0000FF00 & buffer.getRGB( x+j, y+i ) ) >> 8 ) * masc );
                    b += (int)Math.round( ( 0x000000FF & buffer.getRGB( x+j, y+i ) ) * masc );       
                }
            }
        }
        
        if ( r < 0 )
            r = 0;
        if ( g < 0 )
            g = 0;
        if ( b < 0 )
            b = 0;                
        if ( r > 255 )
            r = 255;
        if ( g > 255 )
            g = 255;
        if ( b > 255 )
            b = 255;

        return 0xFF000000 + ( r << 16 ) + ( g << 8 ) + b;
    }
    
    @Override
    public CoresUtil getCoresUtil() {
        return coresUtil;
    }
          
}
