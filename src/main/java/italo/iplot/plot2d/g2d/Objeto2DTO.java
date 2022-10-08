package italo.iplot.plot2d.g2d;

import italo.iplot.plot2d.g2d.util.Math2D;
import italo.iplot.gui.Tela;
import italo.iplot.plot2d.g2d.util.Transformador2D;
import italo.iplot.plot2d.g2d.util.corte.Cortador2D;
import italo.iplot.plot2d.g2d.vert.InicialFiltroVert2D;
import italo.iplot.plot2d.g2d.vert.VisaoFiltroVert2D;
import italo.iplot.plot2d.g2d.vert.XYFiltroVert2D;
import italo.iplot.plot2d.grafico.geom.Geom2DTO;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

public interface Objeto2DTO extends Geom2DTO {
    
    public Rectangle2D getStringLimites( String texto, Font fonte );
    
    public Tela getTela();

    public Math2D getMath2D();
    
    public int getMouseX();
    
    public int getMouseY();
            
    public Transformador2D getTransformador2D();
    
    public Cortador2D getCortador2D();
    
    public VerticeObjeto2DFactory getVObj2DFactory();
    
    public InicialFiltroVert2D getInicialFiltroV2D();
    
    public XYFiltroVert2D getXYFiltroV2D();
    
    public VisaoFiltroVert2D getVisaoFiltroV2D();
    
    public DecimalFormat getRotuloDecimalFormat();
    
}
