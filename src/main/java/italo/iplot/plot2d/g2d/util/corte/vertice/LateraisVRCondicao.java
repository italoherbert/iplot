package italo.iplot.plot2d.g2d.util.corte.vertice;

import italo.iplot.plot2d.g2d.util.corte.CorteCondicao;

public class LateraisVRCondicao implements CorteCondicao {
    
    @Override
    public boolean condicao( double x1, double y1, double x2, double y2, double x, double y ) {
        return x < x1 || x > x2 || y < y1 || y > y2;
    }
    
}
