package italo.iplot.grafico.aapainter;

import italo.iplot.grafico.linha.LinhaDrawer;
import italo.iplot.grafico.pixel.BufferGraficoPixel;
import italo.iplot.gui.grafico.CoresUtil;
import italo.iplot.gui.grafico.DoubleGraficoPixel;
import italo.iplot.gui.grafico.GraficoBufferPainter;
import italo.iplot.gui.grafico.GraficoPixel;

public abstract class AbstractGraficoBufferPainter implements GraficoBufferPainter {

    public final static int DEFAULT_BG_COR = 0x00FFFFFF;
    public final static int DEFAULT_MULT_FATOR = 1;
    
    protected final CoresUtil coresUtil;
    protected final LinhaDrawer drawer;
    protected final GraficoPixel gpx;

    protected final BufferGraficoPixel buffer = new BufferGraficoPixel();
    protected int bgcor = DEFAULT_BG_COR;
    protected int d;
    
    public AbstractGraficoBufferPainter( GraficoPixel gpx, LinhaDrawer drawer, CoresUtil coresUtil ) {        
        this.gpx = gpx;
        this.drawer = drawer;           
        this.coresUtil = coresUtil;
    }   
    
    public abstract void pintaPixel( int x, int y );

    @Override
    public void iniciaBuffer() {
        this.iniciaBuffer( DEFAULT_MULT_FATOR );
    }

    @Override
    public void iniciaBuffer( int d ) {
        this.iniciaBuffer( bgcor, d );
    }
        
    @Override
    public void iniciaBuffer( int bgcor, int d ) {
        this.d = d;
        this.bgcor = bgcor;
        
        buffer.criaBuffer( gpx.getLarg()*d, gpx.getAlt()*d );
        buffer.iniciaBuffer( bgcor ); 
    }
            
    @Override
    public void desenhaLinha( DoubleGraficoPixel dgpx, double[] p0, double[] p1, int cor ) {                                       
        drawer.desenhaLinha( dgpx, p0, p1, cor );
    }                    
    
    @Override
    public void pintaBuffer() {                
        int larg = gpx.getLarg();
        int alt = gpx.getAlt();
        
        for( int y = 0; y < alt; y++ )
            for( int x = 0; x < larg; x++ )
                this.pintaPixel( x, y );                    
    }

    @Override
    public GraficoPixel getGraficoPixel() {
        return gpx;
    }
    
    @Override
    public GraficoPixel getBufferGraficoPixel() {
        return buffer;
    }
    
    @Override
    public int getMultFator() {
        return d;
    }

    
}
