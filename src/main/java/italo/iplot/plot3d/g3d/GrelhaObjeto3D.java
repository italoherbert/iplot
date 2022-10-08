package italo.iplot.plot3d.g3d;

import java.util.List;

public interface GrelhaObjeto3D {
    
    public int getXNDivs();
    
    public int getZNDivs();
    
    public List<Vertice3D> getVertices();
    
    public void addFace( Face3D face, Objeto3DTO to );
    
}
