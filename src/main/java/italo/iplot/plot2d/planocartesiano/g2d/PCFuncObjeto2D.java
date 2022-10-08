package italo.iplot.plot2d.planocartesiano.g2d;

import italo.iplot.planocartesiano.Legenda;
import italo.iplot.plot2d.g2d.ComponenteObjeto2D;
import italo.iplot.plot2d.g2d.ComponenteObjeto2DLimite;
import italo.iplot.plot2d.g2d.ContainerObjeto2D;
import italo.iplot.plot2d.g2d.Objeto2D;
import italo.iplot.plot2d.g2d.Objeto2DTO;
import italo.iplot.plot2d.planocartesiano.objgrafico.FuncObj2DGrafico;
import java.awt.Color;
import italo.iplot.funcs.g2d.Func2D;

public class PCFuncObjeto2D extends Objeto2D implements ComponenteObjeto2D {
        
    private double x1 = -1;
    private double x2 = 1;    
    private double y1 = -1;
    private double y2 = 1;
    
    private double minY = Double.NEGATIVE_INFINITY;
    private double maxY = Double.POSITIVE_INFINITY;
    private boolean yIntervaloAtivado = false;
    private boolean xIntervaloCompleto = false;
                
    private Func2D func2D;
    private String legenda = null;
    
    private ContainerObjeto2D container;
    
    public PCFuncObjeto2D() {        
        super.pintarFaces = false;
        
        super.arestasCor = Color.BLUE;
        super.grafico = new FuncObj2DGrafico();
    }
            
    @Override
    public void constroiObjeto2D( Objeto2DTO to ) {                                                                         
        if ( legenda != null )           
            ((PCContainerObjeto2D)container).addLegenda( new Legenda( legenda, arestasCor ) );
    }

    @Override
    public ComponenteObjeto2DLimite calculaLimites() {
        if ( yIntervaloAtivado ) {
            if ( y1 < minY )
                y1 = minY;
            if ( y2 > maxY )
                y2 = maxY;                       
        } else {        
            y1 = Double.POSITIVE_INFINITY;
            y2 = Double.NEGATIVE_INFINITY;                      

            double d = x2-x1;

            int npontos = ((PCContainerObjeto2D)container).getTelaDX();

            for( int i = 0; i < npontos; i++ ) {                                    
                double pcx = x1 + ( (double)i / (double)(npontos+1) ) * d;            

                if ( func2D != null )  {
                    double pcy;
                    try {
                        pcy = func2D.getY( pcx );                          
                    } catch ( ArithmeticException e ) {
                        pcy = Double.NaN;
                    }
                    if ( pcy != Double.NaN && pcy != Double.NEGATIVE_INFINITY && pcy != Double.POSITIVE_INFINITY ) {                        
                        if ( pcy >= minY && pcy <= maxY ) {
                            if ( pcy < y1 )
                                y1 = pcy;
                            if ( pcy > y2 )
                                y2 = pcy;
                        }
                    }
                }           
            } 
            if ( y1 == Double.POSITIVE_INFINITY )
                y1 = ComponenteObjeto2DLimite.MINY;
            if ( y2 == Double.NEGATIVE_INFINITY )
                y2 = ComponenteObjeto2DLimite.MAXY;
        }
        return new ComponenteObjeto2DLimite( x1, x2, y1, y2 );
    }

    @Override
    public void escalar( double escala, Objeto2DTO to ) {
        
    }
    
    public void limitarY( double y1, double y2 ) {
        this.minY = y1;
        this.maxY = y2;
    }
        
    public void setXIntervalo( double x1, double x2 ) {
        this.x1 = x1;
        this.x2 = x2;
    }
    
    public void setYIntervalo( double y1, double y2 ) {
        this.y1 = y1;
        this.y2 = y2;
        this.yIntervaloAtivado = true;
    }
        
    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }
    
    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }

    public boolean isYIntervaloAtivado() {
        return yIntervaloAtivado;
    }

    public void setYIntervaloAtivado(boolean yIntervaloAtivado) {
        this.yIntervaloAtivado = yIntervaloAtivado;
    }

    public boolean isXIntervaloCompleto() {
        return xIntervaloCompleto;
    }

    public void setXIntervaloCompleto(boolean xIntervaloCompleto) {
        this.xIntervaloCompleto = xIntervaloCompleto;
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
        
    public Func2D getFunc2D() {
        return func2D;
    }

    public void setFunc2D(Func2D func2D) {
        this.func2D = func2D;
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

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }
         
}