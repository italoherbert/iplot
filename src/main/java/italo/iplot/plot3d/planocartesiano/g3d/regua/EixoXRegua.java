package italo.iplot.plot3d.planocartesiano.g3d.regua;

import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public abstract class EixoXRegua implements Regua3D {
    
    private final PlanoCartesianoObjeto3D plano;
    
    public EixoXRegua( PlanoCartesianoObjeto3D plano ) {
        this.plano = plano;
    }
    
    @Override
    public double getN1() {
        return plano.getPlotObj3DManager().getIMinX();
    }

    @Override
    public double getN2() {
        return plano.getPlotObj3DManager().getIMaxX();
    }

    @Override
    public double getInc() {
        return plano.getPlotObj3DManager().getIIncX();
    }

    @Override
    public int getNumRotulos() {
        return plano.getPlotObj3DManager().getXNumRotulos();
    }

    @Override
    public double getDesloc() {
        return plano.getPlotObj3DManager().getIDeslocX();
    }

    @Override
    public double getDN() {
        return plano.getDX();
    }    
    
}
