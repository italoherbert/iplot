package italo.iplot.plot2d.grafico.geom;

import italo.iplot.gui.Tela;
import italo.iplot.plot2d.g2d.util.Math2D;
import italo.iplot.plot2d.g2d.vert.InicialFiltroVert2D;
import italo.iplot.plot2d.g2d.vert.XYFiltroVert2D;
import italo.iplot.plot2d.g2d.vert.VisaoFiltroVert2D;

public interface Geom2DTO {
    
    public Math2D getMath2D();          
             
    public Tela getTela();
    
    public InicialFiltroVert2D getInicialFiltroV2D();
    
    public XYFiltroVert2D getXYFiltroV2D();
    
    public VisaoFiltroVert2D getVisaoFiltroV2D();
    
}
