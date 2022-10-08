package italo.iplot.plot3d.planocartesiano.g3d.divs;

import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public abstract class YDivisoes extends AbstractDivisoes {
    
    protected PlanoCartesianoObjeto3D planoCartesianoObj3D;
    
    public YDivisoes(PlanoCartesianoObjeto3D plano) {
        this.planoCartesianoObj3D = plano;
    }

    @Override
    public double getN1() {
        return planoCartesianoObj3D.getPlotObj3DManager().getIMinY();
    }

    @Override
    public double getN2() {
        return planoCartesianoObj3D.getPlotObj3DManager().getIMaxY();
    }

    @Override
    public double getInc() {
        return planoCartesianoObj3D.getPlotObj3DManager().getIIncY();
    }
        
    @Override
    public int getNumRotulos() {
        return planoCartesianoObj3D.getPlotObj3DManager().getYNumRotulos();
    }       

    @Override
    public double getIDesloc() {
        return planoCartesianoObj3D.getPlotObj3DManager().getIDeslocY();
    }

    @Override
    public double getDN() {
        return planoCartesianoObj3D.getDY();
    }
           
}
