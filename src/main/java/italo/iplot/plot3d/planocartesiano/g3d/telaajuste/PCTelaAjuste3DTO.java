package italo.iplot.plot3d.planocartesiano.g3d.telaajuste;

import italo.iplot.planocartesiano.telaajuste.PCTelaAjusteTO;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

public class PCTelaAjuste3DTO implements PCTelaAjusteTO {

    private final Objeto3DTO to;

    public PCTelaAjuste3DTO( Objeto3DTO to ) {
        this.to = to;
    }

    @Override
    public double verticeUnidade(double px) {
        return to.getMath3D().verticeUnidade( px, to.getTela() );
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

