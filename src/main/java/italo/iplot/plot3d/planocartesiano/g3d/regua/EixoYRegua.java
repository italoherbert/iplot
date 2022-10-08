package italo.iplot.plot3d.planocartesiano.g3d.regua;

import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public abstract class EixoYRegua implements Regua3D {
    
    private final PlanoCartesianoObjeto3D plano;
        
    public EixoYRegua( PlanoCartesianoObjeto3D plano ) {
        this.plano = plano;
    }    

    @Override
    public int getDirecao() {
        return -1;
    }
        
    @Override
    public double getN1() {
        return plano.getPlotObj3DManager().getIMinY();
    }

    @Override
    public double getN2() {
        return plano.getPlotObj3DManager().getIMaxY();
    }

    @Override
    public double getInc() {
        return plano.getPlotObj3DManager().getIIncY();
    }
        
    @Override
    public int getNumRotulos() {
        return plano.getPlotObj3DManager().getYNumRotulos();
    }
            
    @Override
    public double getDesloc() {
        return plano.getPlotObj3DManager().getIDeslocY();
    }
    
    @Override
    public double getDN() {
        return plano.getDY();
    }          
    
}
