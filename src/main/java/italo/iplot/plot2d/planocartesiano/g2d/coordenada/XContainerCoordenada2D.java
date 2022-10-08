package italo.iplot.plot2d.planocartesiano.g2d.coordenada;

import italo.iplot.plot2d.planocartesiano.g2d.PlotObj2DManager;
import italo.iplot.planocartesiano.coordenada.PCContainerCoordenada;

public class XContainerCoordenada2D implements PCContainerCoordenada {
    
    private PlotObj2DManager manager;

    public XContainerCoordenada2D(PlotObj2DManager manager) {
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
