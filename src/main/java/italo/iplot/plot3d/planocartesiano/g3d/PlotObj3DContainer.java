package italo.iplot.plot3d.planocartesiano.g3d;

public interface PlotObj3DContainer {
        
    public double getDX();
    
    public double getDY();
    
    public double getDZ();
        
    public double[] calculaIntervalo( double min, double max, int numRotulos );
    
    public double[][] xFrenteReguaLinha();

    public double[][] zFrenteReguaLinha();
    
    public double[][] planoBase();
    
    public double getYRot();
    
    public double getXRot();
    
}
