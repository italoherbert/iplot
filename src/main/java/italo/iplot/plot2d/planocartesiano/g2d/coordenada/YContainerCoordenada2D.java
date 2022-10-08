package italo.iplot.plot2d.planocartesiano.g2d.coordenada;

import italo.iplot.plot2d.planocartesiano.g2d.PlotObj2DManager;
import italo.iplot.planocartesiano.coordenada.PCContainerCoordenada;

public class YContainerCoordenada2D implements PCContainerCoordenada {
    
    private final PlotObj2DManager manager;

    public YContainerCoordenada2D(PlotObj2DManager manager) {
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

