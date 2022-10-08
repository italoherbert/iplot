package italo.iplot.plot2d.g2d.util.corte.vertice;

import italo.iplot.plot2d.g2d.util.corte.CorteCondicao;

public class CimaVRCondicao implements CorteCondicao 
{
    @Override
    public boolean condicao(double x1, double y1, double x2, double y2, double x, double y) {
        return y > y2;
    }
    
}
