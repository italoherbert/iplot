package italo.iplot.planocartesiano.telaajuste;

import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

public interface PCTelaAjusteTO {
        
    public double verticeUnidade( double px );
    
    public Rectangle2D getStringLimites( String str, Font font );
    
    public DecimalFormat getRotuloDecimalFormat();
    
}
