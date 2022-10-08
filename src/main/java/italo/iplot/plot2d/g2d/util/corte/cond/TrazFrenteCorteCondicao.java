package italo.iplot.plot2d.g2d.util.corte.cond;

import italo.iplot.plot2d.g2d.util.corte.CorteCondicao;

public class TrazFrenteCorteCondicao implements CorteCondicao {

    @Override
    public boolean condicao(double x1, double y1, double x2, double y2, double x, double y) {
        return ( x1 < x && x2 > x ) || ( x2 < x && x1 > x );
    }
    
}
