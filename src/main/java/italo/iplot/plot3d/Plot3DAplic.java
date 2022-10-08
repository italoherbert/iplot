package italo.iplot.plot3d;

import italo.iplot.plot3d.desenho.Desenho3D;
import italo.iplot.gui.Pintura;
import italo.iplot.gui.Tela;
import italo.iplot.plot3d.g3d.util.Math3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.g3d.util.Transformador3D;
import italo.iplot.plot3d.g3d.UniversoVirtual3D;
import italo.iplot.plot3d.g3d.vert.InicialFiltroVert3D;
import italo.iplot.plot3d.g3d.vert.VisaoFiltroVert3D;
import italo.iplot.plot3d.g3d.vert.XYZFiltroVert3D;
import italo.iplot.gui.DesenhoUI;
import italo.iplot.plot3d.g3d.util.Cortador3D;
import italo.iplot.plot3d.g3d.util.Malhador3D;
import italo.iplot.plot3d.g3d.Objeto3DGraficoDriver;

public interface Plot3DAplic extends Objeto3DTO, Objeto3DGraficoDriver {
            
    public Tela getTela();    
    
    public Math3D getMath3D();
    
    public Malhador3D getMalhador3D();
    
    public Cortador3D getCortador3D();
    
    public UniversoVirtual3D getUniversoVirtual();
                
    public double[][] getLuzes();
    
    public Desenho3D getDesenho3D();
    
    public DesenhoUI getDesenhoUI();
        
    public Transformador3D getTransformador3D();
    
    public XYZFiltroVert3D getXYZFiltroV3D();
    
    public VisaoFiltroVert3D getVisaoFiltroV3D();
    
    public InicialFiltroVert3D getInicialFiltroV3D();
    
     public Pintura getPintura();
    
    public void setLuzes( double[][] luzes );
    
    public boolean isAplicarPerspectiva();
    
    public OperManager3D getRotManager();
            
}
