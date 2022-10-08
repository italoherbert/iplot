package italo.iplot.plot3d.planocartesiano.g3d.coordenada;

import italo.iplot.plot3d.planocartesiano.g3d.PlotObj3DManager;
import italo.iplot.planocartesiano.coordenada.PCContainerCoordenada;

public class ZContainerCoordenada3D implements PCContainerCoordenada {
    
    private final PlotObj3DManager manager;

    public ZContainerCoordenada3D(PlotObj3DManager manager) {
        this.manager = manager;
    }

    @Override
    public double getMin() {
        return manager.getMinZ();
    }

    @Override
    public double getMax() {
        return manager.getMaxZ();
    }   

    @Override
    public double getIMin() {
        return manager.getIMinZ();
    }

    @Override
    public double getIMax() {
        return manager.getIMaxZ();
    }
    
    @Override
    public double getIFator() {
        return manager.getIZF();
    }

    @Override
    public double getDN() {
        return manager.getContainer().getDZ();
    }
    
}

