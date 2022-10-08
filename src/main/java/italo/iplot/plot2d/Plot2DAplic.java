package italo.iplot.plot2d;

import italo.iplot.gui.DesenhoUI;
import italo.iplot.gui.Pintura;
import italo.iplot.gui.Tela;
import italo.iplot.plot2d.desenho.Desenho2D;
import italo.iplot.plot2d.g2d.Objeto2DTO;
import italo.iplot.plot2d.g2d.UniversoVirtual2D;
import italo.iplot.plot2d.g2d.util.Math2D;
import italo.iplot.plot2d.g2d.util.Transformador2D;
import italo.iplot.plot2d.g2d.vert.InicialFiltroVert2D;
import italo.iplot.plot2d.g2d.vert.VisaoFiltroVert2D;
import italo.iplot.plot2d.g2d.vert.XYFiltroVert2D;
import italo.iplot.plot2d.g2d.Objeto2DGraficoDriver;

public interface Plot2DAplic extends Objeto2DTO, Objeto2DGraficoDriver {
            
    public Tela getTela();    
    
    public Math2D getMath2D();
    
    public UniversoVirtual2D getUniversoVirtual();
                    
    public Desenho2D getDesenho2D();
    
    public DesenhoUI getDesenhoUI();
        
    public Transformador2D getTransformador2D();
    
    public XYFiltroVert2D getXYFiltroV2D();
    
    public VisaoFiltroVert2D getVisaoFiltroV2D();
    
    public InicialFiltroVert2D getInicialFiltroV2D();
    
    public Pintura getPintura();  
            
}
