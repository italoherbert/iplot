package italo.iplot.plot2d.g2d;

import java.awt.Color;

public interface Objeto2DGraficoDriver extends Objeto2DTO {
    
    public UniversoVirtual2D getUniversoVirtual();
 
    public void setMouseIXYValor( String valor, Color cor );
    
}
