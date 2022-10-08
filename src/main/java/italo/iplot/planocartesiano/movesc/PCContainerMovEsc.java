package italo.iplot.planocartesiano.movesc;

public interface PCContainerMovEsc {
        
    public double getDN();
    
    public double getMin();
    
    public double getMax();
    
    public double getIN1();
    
    public double getIN2();
    
    public double getIFator();
    
    public double getIInc();
    
    public double getIDesloc();
            
    public void setMin( double min );
    
    public void setMax( double max );
    
    public void setIN1( double imin );
    
    public void setIN2( double imax );
    
    public void setIFator( double ifator );
    
    public void setIInc( double iinc );
        
    public void setIDesloc( double desloc );
    
}
