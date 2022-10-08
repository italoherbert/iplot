package italo.iplot.plot3d.grafico.geom;

import italo.iplot.gui.Tela;
import italo.iplot.plot3d.g3d.util.Math3D;
import italo.iplot.plot3d.g3d.vert.InicialFiltroVert3D;
import italo.iplot.plot3d.g3d.vert.VisaoFiltroVert3D;
import italo.iplot.plot3d.g3d.vert.XYZFiltroVert3D;
import java.text.DecimalFormat;

public interface Geom3DTO {
    
    public Math3D getMath3D();          
             
    public Tela getTela();
    
    public InicialFiltroVert3D getInicialFiltroV3D();
    
    public XYZFiltroVert3D getXYZFiltroV3D();
    
    public VisaoFiltroVert3D getVisaoFiltroV3D();
    
    public DecimalFormat getRotuloDecimalFormat();
    
}
