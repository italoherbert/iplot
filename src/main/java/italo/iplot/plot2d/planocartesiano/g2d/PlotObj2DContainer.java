package italo.iplot.plot2d.planocartesiano.g2d;

public interface PlotObj2DContainer {
    
    public double getDX();
    
    public double getDY();
            
    public double[] calculaIntervalo( double min, double max, int numRotulos );
    
}
