package italo.iplot.plot3d.g3d;

public interface ComponenteObjeto3D {
    
    public void escalar( double escala, Objeto3DTO to );
    
    public void constroi( Objeto3DTO to );
    
    public ComponenteObjeto3DLimite calculaLimites();
    
    public void setContainerObjeto3D( ContainerObjeto3D container );
    
}
