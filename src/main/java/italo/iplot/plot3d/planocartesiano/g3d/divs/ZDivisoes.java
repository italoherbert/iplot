package italo.iplot.plot3d.planocartesiano.g3d.divs;

import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public abstract class ZDivisoes extends AbstractDivisoes {
    
    protected PlanoCartesianoObjeto3D planoCartesianoObj3D;
    
    public ZDivisoes(PlanoCartesianoObjeto3D plano) {
        this.planoCartesianoObj3D = plano;
    }

    @Override
    public double getN1() {
        return planoCartesianoObj3D.getPlotObj3DManager().getIMinZ();
    }

    @Override
    public double getN2() {
        return planoCartesianoObj3D.getPlotObj3DManager().getIMaxZ();
    }

    @Override
    public double getInc() {
        return planoCartesianoObj3D.getPlotObj3DManager().getIIncZ();
    }

    
    @Override
    public int getNumRotulos() {
        return planoCartesianoObj3D.getPlotObj3DManager().getZNumRotulos();
    }       

    @Override
    public double getIDesloc() {
        return planoCartesianoObj3D.getPlotObj3DManager().getIDeslocZ();
    }
    
    @Override
    public double getDN() {
        return planoCartesianoObj3D.getDZ();
    }        
    
}
