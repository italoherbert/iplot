package italo.iplot.plot3d.planocartesiano.g3d.coordenada;

import italo.iplot.plot3d.planocartesiano.g3d.PlotObj3DManager;
import italo.iplot.planocartesiano.coordenada.PCContainerCoordenada;

public class YContainerCoordenada3D implements PCContainerCoordenada {
    
    private final PlotObj3DManager manager;

    public YContainerCoordenada3D(PlotObj3DManager manager) {
        this.manager = manager;
    }

    @Override
    public double getMin() {
        return manager.getMinY();
    }

    @Override
    public double getMax() {
        return manager.getMaxY();
    }
    
    @Override
    public double getIMin() {
        return manager.getIMinY();
    }

    @Override
    public double getIMax() {
        return manager.getIMaxY();
    }

    @Override
    public double getIFator() {
        return manager.getIYF();
    }

    @Override
    public double getDN() {
        return manager.getContainer().getDY();
    }
    
}

