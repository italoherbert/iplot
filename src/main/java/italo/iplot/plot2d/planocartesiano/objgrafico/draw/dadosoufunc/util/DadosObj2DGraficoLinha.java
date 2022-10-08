package italo.iplot.plot2d.planocartesiano.objgrafico.draw.dadosoufunc.util;

public class DadosObj2DGraficoLinha {

    private final double[] p0;
    private final double[] p1;
    private final boolean nan;

    public DadosObj2DGraficoLinha( double[] p0, double[] p1, boolean nan ) {
        this.p0 = p0;
        this.p1 = p1;
        this.nan = nan;
    }

    public double[] getP0() {
        return p0;
    }

    public double[] getP1() {
        return p1;
    }

    public boolean isNaN() {
        return nan;
    }
    
}
