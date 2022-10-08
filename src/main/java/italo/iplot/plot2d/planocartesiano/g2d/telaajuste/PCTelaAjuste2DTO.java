package italo.iplot.plot2d.planocartesiano.g2d.telaajuste;

import italo.iplot.planocartesiano.telaajuste.PCTelaAjusteTO;
import italo.iplot.plot2d.g2d.Objeto2DTO;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

public class PCTelaAjuste2DTO implements PCTelaAjusteTO {

    private final Objeto2DTO to;

    public PCTelaAjuste2DTO( Objeto2DTO to ) {
        this.to = to;
    }

    @Override
    public double verticeUnidade(double px) {
        return to.getMath2D().verticeUnidade( px, to.getTela() );
    }

    @Override
    public Rectangle2D getStringLimites(String str, Font font) {
        return to.getStringLimites( str, font );
    }

    @Override
    public DecimalFormat getRotuloDecimalFormat() {
        return to.getRotuloDecimalFormat();
    }
    
}
