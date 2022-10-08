package italo.iplot.plot2d.planocartesiano.g2d;

import italo.iplot.funcs.g2d.Func2D;
import italo.iplot.planocartesiano.Legenda;
import italo.iplot.plot2d.g2d.ComponenteObjeto2D;
import italo.iplot.plot2d.g2d.ComponenteObjeto2DLimite;
import italo.iplot.plot2d.g2d.ContainerObjeto2D;
import italo.iplot.plot2d.g2d.Objeto2D;
import italo.iplot.plot2d.g2d.Objeto2DTO;
import italo.iplot.plot2d.g2d.Vertice2D;
import italo.iplot.plot2d.g2d.VerticeRaio2D;
import italo.iplot.plot2d.planocartesiano.objgrafico.DadosObj2DGrafico;
import java.awt.Color;

public class PCDadosObjeto2D extends Objeto2D implements ComponenteObjeto2D, VerticeRaio2D {

    private final int NUM_PTS_T = 20;
    
    private final double RAIO = 0.02d;
    
    private double minX = Double.NEGATIVE_INFINITY;
    private double maxX = Double.POSITIVE_INFINITY;
    private double minY = Double.NEGATIVE_INFINITY;
    private double maxY = Double.POSITIVE_INFINITY;
    
    private boolean yIntervaloAtivado = false;
    private boolean xIntervaloAtivado = false;
    
    protected double[] dadosX = {};
    protected double[] dadosY = {};
    
    private PCFuncParametrica2DOpers opers = null;
    
    protected ContainerObjeto2D container = null;

    protected String legenda = null;
    protected double verticeRaio = RAIO;
    
    public PCDadosObjeto2D() {
        this( new double[] {}, new double[] {} );
    }    

    public PCDadosObjeto2D( double[] dadosX, double[] dadosY ) {
        this.dadosX = dadosX;
        this.dadosY = dadosY;   
        
        super.arestasCor = Color.BLUE;
        super.verticesCor = Color.RED;
        super.pintarVertices = false;
        
        super.verticeRaio2D = this;
        
        super.grafico = new DadosObj2DGrafico();
        super.executarGraficoAntesDePintar = true;
    }

    @Override
    public void constroiObjeto2D(Objeto2DTO to) {                                                      
        if ( dadosX == null || dadosY == null )
            return;
        
        for( int i = 0; i < dadosX.length; i++ ) {            
            double x = container.calculaX( dadosX[i] );
            double y = container.calculaY( dadosY[i] );
                                                
            Vertice2D vertice = new Vertice2D( x, y );
            super.getEstrutura().addVertice( vertice );
        }
                                
        if ( legenda != null && container instanceof PCContainerObjeto2D ) {
            int tipo = ( pintarArestas ? Legenda.LINHA : Legenda.PONTO );
            Color c = ( pintarArestas ? arestasCor : verticesCor );
            ((PCContainerObjeto2D)container).addLegenda( new Legenda( legenda, c, tipo ) );
        }        
    }

    @Override
    public ComponenteObjeto2DLimite calculaLimites() {               
        if ( dadosX != null && dadosY != null ) {            
            double x1 = xIntervaloAtivado ? minX : Double.POSITIVE_INFINITY;
            double y1 = yIntervaloAtivado ? minY : Double.POSITIVE_INFINITY;
            double x2 = xIntervaloAtivado ? maxX : Double.NEGATIVE_INFINITY;                                  
            double y2 = yIntervaloAtivado ? maxY : Double.NEGATIVE_INFINITY;

            for( int i = 0; i < dadosX.length; i++ ) {                                    
                double pcx;
                try {
                    pcx = dadosX[ i ];                          
                } catch ( ArithmeticException e ) {
                    pcx = Double.NaN;
                }
                if ( pcx != Double.NaN && pcx != Double.NEGATIVE_INFINITY && pcx != Double.POSITIVE_INFINITY ) {                        
                    if ( xIntervaloAtivado ? pcx >= minX && pcx <= maxX : true ) {
                        if ( pcx < x1 )
                            x1 = pcx;
                        if ( pcx > x2 )
                            x2 = pcx;
                    }
                }
            }
                                            
            for( int i = 0; i < dadosY.length; i++ ) {                                    
                double pcy;
                try {
                    pcy = dadosY[ i ];                          
                } catch ( ArithmeticException e ) {
                    pcy = Double.NaN;
                }
                if ( pcy != Double.NaN && pcy != Double.NEGATIVE_INFINITY && pcy != Double.POSITIVE_INFINITY ) {                        
                    if ( yIntervaloAtivado ? pcy >= minY && pcy <= maxY : true ) {
                        if ( pcy < y1 )
                            y1 = pcy;
                        if ( pcy > y2 )
                            y2 = pcy;
                    }
                }
            }
            
            if ( opers != null ) {
                double[][] mat = { dadosX, dadosY };
                double[][] result = opers.operXY( mat, dadosX.length );

                dadosX = result[0];
                dadosY = result[1];

                for( int i = 0; i < dadosX.length; i++ ) {
                    double pcx = dadosX[ i ];
                    double pcy = dadosY[ i ];

                    if ( pcx != Double.NaN && pcx != Double.NEGATIVE_INFINITY && pcx != Double.POSITIVE_INFINITY ) {                                                                                
                        if ( xIntervaloAtivado ? pcx >= minX && pcx <= maxX : true ) {
                            if ( pcx < x1 )
                                x1 = pcx;
                            if ( pcx > x2 )
                                x2 = pcx;
                        }
                    }

                    if ( pcy != Double.NaN && pcy != Double.NEGATIVE_INFINITY && pcy != Double.POSITIVE_INFINITY ) {                        
                        if ( yIntervaloAtivado ? pcy >= minY && pcy <= maxY : true ) {
                            if ( pcy < y1 )
                                y1 = pcy;
                            if ( pcy > y2 )
                                y2 = pcy;
                        }
                    }
                }
            }
                     
            if ( x1 == Double.POSITIVE_INFINITY )
                x1 = ComponenteObjeto2DLimite.MINX;
            if ( x2 == Double.NEGATIVE_INFINITY )
                x2 = ComponenteObjeto2DLimite.MAXX;
            if ( y1 == Double.POSITIVE_INFINITY )
                y1 = ComponenteObjeto2DLimite.MINY;
            if ( y2 == Double.NEGATIVE_INFINITY )
                y2 = ComponenteObjeto2DLimite.MAXY;
            return new ComponenteObjeto2DLimite( x1, x2, y1, y2 );
        }
        return new ComponenteObjeto2DLimite();
    }

    public void setFuncsParametricas( double t1, double t2, Func2D fx, Func2D fy ) {
        this.setFuncsParametricas( t1, t2, NUM_PTS_T, fx, fy );
    }
                
    public void setFuncsParametricas( double t1, double t2, int npt, Func2D fx, Func2D fy ) {
        dadosX = new double[ npt ];
        dadosY = new double[ npt ];
        
        double td = Math.abs( t2 - t1 ) / ( npt - 1 );
        
        for( int i = 0; i < npt; i++ ) {
            double t = t1 + i * td;             
            
            dadosX[ i ] = fx.getY( t );
            dadosY[ i ] = fy.getY( t );     
        }
    }

    public PCFuncParametrica2DOpers getFuncParametricaOpers() {
        return opers;
    }

    public void setFuncParametricaOpers(PCFuncParametrica2DOpers opers) {
        this.opers = opers;
    }
    
    @Override
    public void escalar( double escala, Objeto2DTO to ) {        
              
    }
    
    public PCContainerObjeto2D getPCContainerObjeto2D() {
        return (PCContainerObjeto2D)container;
    }

    @Override
    public ContainerObjeto2D getContainerObjeto2D() {
        return container;
    }

    @Override
    public void setContainerObjeto2D(ContainerObjeto2D container) {
        this.container = container;
    }
    
    public double[] getDadosX() {
        return dadosX;
    }

    public void setDadosX(double[] dadosX) {
        this.dadosX = dadosX;
    }

    public double[] getDadosY() {
        return dadosY;
    }

    public void setDadosY(double[] dadosY) {
        this.dadosY = dadosY;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public boolean isYIntervaloAtivado() {
        return yIntervaloAtivado;
    }

    public void setYIntervaloAtivado(boolean yIntervaloAtivado) {
        this.yIntervaloAtivado = yIntervaloAtivado;
    }

    public boolean isXIntervaloAtivado() {
        return xIntervaloAtivado;
    }

    public void setXIntervaloAtivado(boolean xIntervaloAtivado) {
        this.xIntervaloAtivado = xIntervaloAtivado;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }    
    
    @Override
    public double getVerticeRaio() {
        return verticeRaio;
    }

    public void setVerticeRaio(double verticeRaio) {
        this.verticeRaio = verticeRaio;
    }
    
}

