package italo.iplot.plot3d.g3d;

import italo.iplot.plot3d.g3d.util.Transformador3D;
import italo.iplot.plot3d.g3d.util.Math3D;
import italo.iplot.plot3d.g3d.est_opers.EstruturaOperador;
import italo.iplot.gui.Tela;
import italo.iplot.plot3d.g3d.util.Cortador3D;
import italo.iplot.plot3d.g3d.util.Malhador3D;
import italo.iplot.plot3d.g3d.vert.InicialFiltroVert3D;
import italo.iplot.plot3d.g3d.vert.XYZFiltroVert3D;
import italo.iplot.plot3d.g3d.vert.VisaoFiltroVert3D;
import italo.iplot.plot3d.grafico.geom.Geom3DTO;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

public interface Objeto3DTO extends Geom3DTO {
        
    public Rectangle2D getStringLimites( String texto, Font fonte );
    
    public boolean isAplicarPerspectiva();
    
    public UniversoVirtual3D getUniversoVirtual();
    
    public Math3D getMath3D();  
    
    public Cortador3D getCortador3D();
    
    public Malhador3D getMalhador3D();
    
    public VerticeObjeto3DFactory getVObj3DFactory();
        
    public double[][] getLuzes();
    
    public EstruturaOperador getEstOper();
 
    public Transformador3D getTransformador3D();
    
    public Tela getTela();
    
    public InicialFiltroVert3D getInicialFiltroV3D();
    
    public XYZFiltroVert3D getXYZFiltroV3D();
    
    public VisaoFiltroVert3D getVisaoFiltroV3D();
       
    public Object getObjeto();
    
    public void setObjeto( Object obj );
        
}
