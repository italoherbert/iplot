package italo.iplot.plot3d.planocartesiano.g3d.divs;

import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public abstract class XDivisoes extends AbstractDivisoes {
    
    protected PlanoCartesianoObjeto3D planoCartesianoObj3D;
    
    public XDivisoes( PlanoCartesianoObjeto3D plano ) {
        this.planoCartesianoObj3D = plano;
    }
    
    @Override
    public double getN1() {
        return planoCartesianoObj3D.getPlotObj3DManager().getIMinX();
    }

    @Override
    public double getN2() {
        return planoCartesianoObj3D.getPlotObj3DManager().getIMaxX();
    }

    @Override
    public double getInc() {
        return planoCartesianoObj3D.getPlotObj3DManager().getIIncX();
    }

    @Override
    public int getNumRotulos() {
        return planoCartesianoObj3D.getPlotObj3DManager().getXNumRotulos();
    }        

    @Override
    public double getIDesloc() {
        return planoCartesianoObj3D.getPlotObj3DManager().getIDeslocX();
    }
    
    @Override
    public double getDN() {
        return planoCartesianoObj3D.getDX();
    }
    
}
