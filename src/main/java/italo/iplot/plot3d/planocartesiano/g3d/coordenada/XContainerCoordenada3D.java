package italo.iplot.plot3d.planocartesiano.g3d.coordenada;

import italo.iplot.plot3d.planocartesiano.g3d.PlotObj3DManager;
import italo.iplot.planocartesiano.coordenada.PCContainerCoordenada;

public class XContainerCoordenada3D implements PCContainerCoordenada {
    
    private PlotObj3DManager manager;

    public XContainerCoordenada3D(PlotObj3DManager manager) {
        this.manager = manager;
    }

    @Override
    public double getMin() {
        return manager.getMinX();
    }

    @Override
    public double getMax() {
        return manager.getMaxX();
    }
        
    @Override
    public double getIMin() {
        return manager.getIMinX();
    }

    @Override
    public double getIMax() {
        return manager.getIMaxX();
    }

    @Override
    public double getIFator() {
        return manager.getIXF();
    }

    @Override
    public double getDN() {
        return manager.getContainer().getDX();
    }
    
}
