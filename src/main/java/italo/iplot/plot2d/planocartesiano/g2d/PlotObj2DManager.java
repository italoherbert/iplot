package italo.iplot.plot2d.planocartesiano.g2d;

import italo.iplot.planocartesiano.coordenada.PCCoordenadaCalc;
import italo.iplot.plot2d.g2d.ComponenteObjeto2D;
import italo.iplot.plot2d.g2d.ComponenteObjeto2DLimite;
import italo.iplot.plot2d.g2d.Objeto2DTO;
import italo.iplot.plot2d.planocartesiano.g2d.coordenada.XContainerCoordenada2D;
import italo.iplot.plot2d.planocartesiano.g2d.coordenada.YContainerCoordenada2D;
import italo.iplot.planocartesiano.regua.ReguaUtil;
import java.util.ArrayList;
import java.util.List;
import italo.iplot.planocartesiano.coordenada.PCContainerCoordenada;
import italo.iplot.planocartesiano.movesc.PCMovEscConfigurador;
import italo.iplot.planocartesiano.movesc.PCContainerMovEsc;
import italo.iplot.plot2d.planocartesiano.g2d.movesc.XContainerMovEsc2D;
import italo.iplot.plot2d.planocartesiano.g2d.movesc.YContainerMovEsc2D;
import italo.iplot.planocartesiano.telaajuste.PCPlotObjManagerTelaAjuste;

public class PlotObj2DManager implements PCPlotObjManagerTelaAjuste {
              
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;
    
    private double ix1;
    private double ix2;
    private double iy1;
    private double iy2;
    
    private double ixf;
    private double iyf;
        
    private double iincX = 0.5d;
    private double iincY = 0.5d;
    private double ideslocX = 0;
    private double ideslocY = 0;
    
    private int xNumRotulos = ReguaUtil.NUM_ROTULOS_PADRAO;
    private int yNumRotulos = ReguaUtil.NUM_ROTULOS_PADRAO;
    
    private final PCCoordenadaCalc calculadora = new PCCoordenadaCalc();
    private final PCContainerCoordenada xcalcula = new XContainerCoordenada2D( this );
    private final PCContainerCoordenada ycalcula = new YContainerCoordenada2D( this );
    
    private final PCMovEscConfigurador movescCFG = new PCMovEscConfigurador();
    private final PCContainerMovEsc xmovesc = new XContainerMovEsc2D( this );
    private final PCContainerMovEsc ymovesc = new YContainerMovEsc2D( this );
    
    private boolean calcularIntervalo = true;
    
    private final PlotObj2DContainer container;
    private final List<ComponenteObjeto2D> plotObjs = new ArrayList();
    
    public PlotObj2DManager( PlotObj2DContainer container ) {
        this.container = container;
    }
    
    public void removePlotObjs() {
        synchronized( plotObjs ) {
            plotObjs.clear();
        }
    }
    
    public void addPlotObj2D( ComponenteObjeto2D obj ) {
        synchronized( plotObjs ) {
            plotObjs.add( obj );
        }
    }
    
    public void constroi( Objeto2DTO to ) {
        synchronized( plotObjs ) {
            for( ComponenteObjeto2D obj : plotObjs )
                obj.constroi( to ); 
        }
    }
    
    public void configura( Objeto2DTO to ) {                
        this.minX = Double.MAX_VALUE;
        this.maxX = Double.MIN_VALUE;
        this.minY = Double.MAX_VALUE;
        this.maxY = Double.MIN_VALUE;
        
        if ( plotObjs.isEmpty() ) {
            this.minX = ComponenteObjeto2DLimite.MINX;
            this.maxX = ComponenteObjeto2DLimite.MAXX;
            this.minY = ComponenteObjeto2DLimite.MINY;
            this.maxY = ComponenteObjeto2DLimite.MAXY;
        } else {
            for( ComponenteObjeto2D obj : plotObjs ) {
                ComponenteObjeto2DLimite lims = obj.calculaLimites();
                if ( lims.getMinX() < minX )
                    minX = lims.getMinX();
                if ( lims.getMaxX() > maxX )
                    maxX = lims.getMaxX();            
                if ( lims.getMinY() < minY )
                    minY = lims.getMinY();
                if ( lims.getMaxY() > maxY )
                    maxY = lims.getMaxY();      
            }
        }                
        
        double[] xinter = container.calculaIntervalo( minX, maxX, xNumRotulos );
        double[] yinter = container.calculaIntervalo( minY, maxY, yNumRotulos );

        if ( calcularIntervalo ) {
            ix1 = xinter[0];
            ix2 = xinter[1];
            iy1 = yinter[0];
            iy2 = yinter[1];  

            iincX = xinter[2];
            iincY = yinter[2];
        } else {
            ix1 = minX;
            ix2 = maxX;
            iincX = Math.abs(ix2-ix1) / xNumRotulos;
            
            iy1 = minY;
            iy2 = maxY;
            iincY = Math.abs(iy2-iy1) / yNumRotulos;            
        }                
        
        ixf = Math.abs( maxX - minX ) / Math.abs( ix2 - ix1 );
        iyf = Math.abs( maxY - minY ) / Math.abs( iy2 - iy1 );
        
        ideslocX = ideslocY = 0;
    }        
    
    public void escalar( double escala, Objeto2DTO to ) {
        movescCFG.escalar( xmovesc, escala );
        movescCFG.escalar( ymovesc, escala );
        
        for( ComponenteObjeto2D obj : plotObjs )
            obj.escalar( 1.0d / escala, to );
    }
    
    public void mover( double xdesloc, double ydesloc, Objeto2DTO to ) {          
        double idx = Math.abs( ix2 - ix1 );
        double idy = Math.abs( iy2 - iy1 );
                                            
        double desX = xdesloc * ( idx / container.getDX() );
        double desY = ydesloc * ( idy / container.getDY() );
                          
        movescCFG.move( xmovesc, desX );
        movescCFG.move( ymovesc, desY );
    }
    
    public double calculaX( double x ) {
        return calculadora.calcula( xcalcula, x );
    }
    
    public double calculaY( double y ) {
        return calculadora.calcula( ycalcula, y );
    }
    
    public List<ComponenteObjeto2D> getPlotObjs() {
        return plotObjs;
    }
    
    public double getIMinX() {
        return Math.min( ix1, ix2 );
    }

    public double getIMaxX() {
        return Math.max( ix1, ix2 );
    }

    @Override
    public double getIMinY() {
        return Math.min( iy1, iy2 );
    }
    
    public double getIMaxY() {
        return Math.max( iy1, iy2 ); 
    }

    public double getIIncX() {
        return iincX;
    }

    @Override
    public double getIIncY() {
        return iincY;
    }
    
    public void setXYNumRotulos( int numRotulos ) {
        this.xNumRotulos = this.yNumRotulos = numRotulos;
    }

    public int getXNumRotulos() {
        return xNumRotulos;
    }

    @Override
    public int getYNumRotulos() {
        return yNumRotulos;
    }

    public void setXNumRotulos(int xNumRotulos) {
        this.xNumRotulos = xNumRotulos;
    }

    public void setYNumRotulos(int yNumRotulos) {
        this.yNumRotulos = yNumRotulos;
    }

    public boolean isCalcularIntervalo() {
        return calcularIntervalo;
    }

    public void setCalcularIntervalo(boolean calcularIntervalo) {
        this.calcularIntervalo = calcularIntervalo;
    }

    public double getIXF() {
        return ixf;
    }

    public double getIYF() {
        return iyf;
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getIDeslocX() {
        return ideslocX;
    }

    public double getIDeslocY() {
        return ideslocY;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public double getIX1() {
        return ix1;
    }

    public void setIX1(double ix1) {
        this.ix1 = ix1;
    }

    public double getIX2() {
        return ix2;
    }

    public void setIX2(double ix2) {
        this.ix2 = ix2;
    }

    public double getIY1() {
        return iy1;
    }

    public void setIY1(double iy1) {
        this.iy1 = iy1;
    }

    public double getIY2() {
        return iy2;
    }

    public void setIY2(double iy2) {
        this.iy2 = iy2;
    }

    public void setIXF(double ixf) {
        this.ixf = ixf;
    }

    public void setIYF(double iyf) {
        this.iyf = iyf;
    }

    public void setIIncX(double iincX) {
        this.iincX = iincX;
    }

    public void setIIncY(double iincY) {
        this.iincY = iincY;
    }

    public void setIDeslocX(double ideslocX) {
        this.ideslocX = ideslocX;
    }

    public void setIDeslocY(double ideslocY) {
        this.ideslocY = ideslocY;
    }

    public PlotObj2DContainer getContainer() {
        return container;
    }
    
}
