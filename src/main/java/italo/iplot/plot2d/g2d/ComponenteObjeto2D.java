package italo.iplot.plot2d.g2d;

public interface ComponenteObjeto2D {
    
    public void constroi( Objeto2DTO to );
        
    public void escalar( double escala, Objeto2DTO to );
    
    public ComponenteObjeto2DLimite calculaLimites();
    
    public ContainerObjeto2D getContainerObjeto2D();
    
    public void setContainerObjeto2D( ContainerObjeto2D container );
    
}
