package italo.iplot.plot3d.planocartesiano.g3d.regua;

import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public abstract class EixoZRegua implements Regua3D {

    private final PlanoCartesianoObjeto3D plano;
    
    public EixoZRegua( PlanoCartesianoObjeto3D plano ) {
        this.plano = plano;
    }   
 
    @Override
    public double getN1() {
        return plano.getPlotObj3DManager().getIMinZ();
    }

    @Override
    public double getN2() {
        return plano.getPlotObj3DManager().getIMaxZ();
    }

    @Override
    public double getInc() {
        return plano.getPlotObj3DManager().getIIncZ();
    }
    
    @Override
    public int getNumRotulos() {
        return plano.getPlotObj3DManager().getZNumRotulos();
    }
    
    @Override
    public double getDesloc() {
        return plano.getPlotObj3DManager().getIDeslocZ();
    }
    
    @Override
    public double getDN() {
        return plano.getDZ();
    }
    
}
