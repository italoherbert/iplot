package italo.iplot.grafico;

import italo.iplot.grafico.aapainter.GraficoBufferPainterFactoryImpl;
import italo.iplot.grafico.util.CoresUtilitario;
import italo.iplot.grafico.texto.Texto2D;
import italo.iplot.grafico.linha.NormalLinhaDrawer;
import italo.iplot.grafico.filtro.FiltroManagerImpl;
import italo.iplot.gui.GPintura;
import italo.iplot.gui.grafico.FaceGeom;
import italo.iplot.gui.grafico.ArestaGeom;
import italo.iplot.gui.grafico.CoresUtil;
import italo.iplot.gui.grafico.Grafico;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import italo.iplot.gui.grafico.FiltroManager;
import italo.iplot.gui.grafico.DoubleGraficoPixel;
import italo.iplot.grafico.linha.LinhaDrawer;
import italo.iplot.gui.grafico.GraficoBufferPainterFactory;
import java.awt.font.FontRenderContext;

public abstract class Java2DGrafico implements Grafico {

    protected GPintura pintura;
    protected Color bgCor = Color.BLACK;
    protected final List<Texto2D> textos = new ArrayList();
    
    protected CoresUtil coresUtil = new CoresUtilitario();
               
    protected LinhaDrawer linhaDrawer = new NormalLinhaDrawer(); 
    
    protected GraficoBufferPainterFactory gBufferPainterFactory = new GraficoBufferPainterFactoryImpl( linhaDrawer, coresUtil );    
    protected FiltroManager filtroManager = new FiltroManagerImpl( coresUtil );
                
    protected void addTexto2D( Texto2D t ) {
        textos.add( t );
    }
        
    @Override
    public void completaPintura() {       
        this.java2DDesenhaTextosELimpaLista();
    }
    
    protected void java2DDesenhaTextosELimpaLista() {
        for( Texto2D t : textos )
            this.java2DDesenhaTexto( t ); 
        textos.clear();
    }
                
    protected void java2DDesenhaTexto( Texto2D t ) {
        Graphics2D g2d = (Graphics2D)this.getGraphics();
        
        AffineTransform transform = g2d.getTransform();
        if ( t.getRotAng() != 0 )
            g2d.rotate( t.getRotAng(), t.getX(), t.getY() ); 

        g2d.setFont( t.getFont() );
        g2d.drawString( t.getTexto(), t.getX(), t.getY() );

        if ( t.getRotAng() != 0 )
            g2d.setTransform( transform );
    }
    
    protected void java2DPreencheFace( FaceGeom geom ) { 
        int[][] pontos = geom.pontosPX();        
                        
        if ( pontos[0] != null ) {
            Graphics2D g2d = (Graphics2D)this.getGraphics();
            g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT ); 
            g2d.fillPolygon( pontos[0], pontos[1], pontos[0].length );                                  
            g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );                                     
        }
    }
    
    protected void java2DDesenhaLinhaPontilhada( ArestaGeom geom, int esp) {        
        int[] p0_2D = geom.calculaP0();
        int[] p_2D = geom.calculaP1();
        int x1 = p0_2D[0];
        int y1 = p0_2D[1];
        int x2 = p_2D[0];
        int y2 = p_2D[1];
                
        int c = (int)Math.sqrt( Math.pow( x2-x1, 2 ) + Math.pow( y2-y1, 2 ) );
        double a = Math.atan2( y2-y1, x2-x1 );
        int npontos = c / esp;
        for( int i = 0; i <= npontos; i++ ) {
            int x = x1 + (int)( Math.cos( a ) * ( i * esp ) );
            int y = y1 + (int)( Math.sin( a ) * ( i * esp ) );
            this.pintaPixel( x, y );
        }
        int mod = c % esp;
        if ( mod != 0 ) {
            int x = x1 + (int)( Math.cos( a ) * c );
            int y = y1 + (int)( Math.sin( a ) * c );
            this.pintaPixel( x, y ); 
        }
        
        /*
        Graphics2D g2d = (Graphics2D)this.getGraphics();
        float[] dash = { 1, esp };
        Stroke s = g2d.getStroke();
        g2d.setStroke( new BasicStroke( 1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10, dash, 0 ) ); 
        g2d.drawLine( x1, y1, x2, y2 );
        g2d.setStroke( s );
        */
    }
    
    protected void java2DDesenhaLinha( ArestaGeom geom ) {
        int[] p0_2D = geom.calculaP0();
        int[] p_2D = geom.calculaP1();
        
        Line2D.Float linha = new Line2D.Float( p0_2D[0], p0_2D[1], p_2D[0], p_2D[1] );
        Graphics2D g2d = (Graphics2D)this.getGraphics();
        g2d.draw( linha ); 
    }
            
    @Override
    public Rectangle2D stringLimites(String texto) {
        return this.getGraphics().getFontMetrics().getStringBounds( texto, this.getGraphics() );
    }

    @Override
    public Rectangle2D stringLimites( String texto, Font font ) {
        FontRenderContext frc = ((Graphics2D)this.getGraphics()).getFontRenderContext();
        if ( frc == null )
            return this.stringLimites( texto );
        
        return font.getStringBounds( texto, frc );
    }
    
    @Override
    public void desenhaTexto( String texto, int x, int y ) {                        
        this.desenhaTexto( texto, x, y, 0 ); 
    }             
    
    @Override
    public void desenhaLinha( int x1, int y1, int x2, int y2 ) {
        this.getGraphics().drawLine( x1, y1, x2, y2 );
    }
        
    @Override
    public void desenhaLinhaPontilhada( int x1, int y1, int x2, int y2, int esp ) {
        double d = Math.sqrt( Math.pow( y2-y1, 2 ) + Math.pow( x2-x1, 2 ) );
        double a = Math.atan2( y2-y1, x2-x1 );
        for( int r = 1; r < d; r+=esp ) {
            int x = (int)( r * Math.cos( a ) );
            int y = (int)( r * Math.sin( a ) );
            this.pintaPixel( x, y );
        }
    }

    @Override
    public void desenhaLinhaNormal( DoubleGraficoPixel dgpx, double[] p0, double[] p1 ) {
        linhaDrawer.desenhaLinha( dgpx, p0, p1, this.getCor().getRGB() );
    }
                    
    @Override
    public void desenhaRetangulo( int x, int y, int largura, int altura ) {
        this.getGraphics().drawRect( x, y, largura, altura ); 
    }
    
    @Override
    public void preencheRetangulo( int x, int y, int largura, int altura ) {
        this.getGraphics().fillRect( x, y, largura, altura ); 
    }
    
    @Override
    public void desenhaCirculo(int x, int y, int r) {
        this.getGraphics().drawArc( x - r, y - r, 2*r, 2*r, 0, 360 ); 
    }

    @Override
    public void preencheCirculo(int x, int y, int r) {
        this.getGraphics().fillArc( x - r, y - r, 2*r, 2*r, 0, 360 ); 
    }
    
    @Override
    public void pintaPixel( int x, int y, int rgb ) {
        if ( x < 0 )
            return;
        if ( y < 0 )
            return;
        if ( x >= pintura.getImagem().getWidth() )
            return;        
        if ( y >= pintura.getImagem().getHeight() )
            return;
                    
        pintura.getImagem().setRGB( x, y, rgb );             
    }

    @Override
    public int getRGB(int x, int y) {
        return pintura.getImagem().getRGB( x, y );
    }

    @Override
    public int getLarg() {
        return pintura.getImagem().getWidth();
    }

    @Override
    public int getAlt() {
        return pintura.getImagem().getHeight();
    }
                   
    @Override
    public void pintaPixel( int x, int y ) {
        this.pintaPixel( x, y, this.getGraphics().getColor().getRGB() ); 
    }
    
    @Override
    public Graphics getGraphics() {        
        return pintura.getAWTGraphics();
    }

    @Override
    public FiltroManager getFiltroManager() {
        return filtroManager;
    }

    @Override
    public GraficoBufferPainterFactory getGBufferPainterFactory() {
        return gBufferPainterFactory;
    }
        
    @Override
    public CoresUtil getCoresUtil() {
        return coresUtil;
    }
        
    @Override
    public void setCor(Color cor) {
        this.getGraphics().setColor( cor ); 
    }
    
    @Override
    public Color getCor() {
        return this.getGraphics().getColor();
    }
    
    @Override
    public Color getBGCor() {
        return bgCor;
    }

    @Override
    public void setBGCor(Color cor) {
        this.bgCor = cor;
    }

    @Override
    public GPintura getPintura() {
        return pintura;
    }

    @Override
    public void setPintura(GPintura pintura) {
        this.pintura = pintura;
    }

    
}
