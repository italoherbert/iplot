package italo.iplot.plot3d.planocartesiano.g3d.movesc;

import italo.iplot.planocartesiano.movesc.PCContainerMovEsc;
import italo.iplot.plot3d.planocartesiano.g3d.PlotObj3DManager;

public class ZContainerMovEsc3D implements PCContainerMovEsc {

    private final PlotObj3DManager plotObjManager;

    public ZContainerMovEsc3D(PlotObj3DManager plotObjManager) {
        this.plotObjManager = plotObjManager;
    }
    
    @Override
    public double getDN() {
        return plotObjManager.getContainer().getDZ();
    }

    @Override
    public double getMin() {
        return plotObjManager.getMinZ();
    }

    @Override
    public double getMax() {
        return plotObjManager.getMaxZ();
    }

    @Override
    public double getIN1() {
        return plotObjManager.getIZ1();
    }

    @Override
    public double getIN2() {
        return plotObjManager.getIZ2();
    }

    @Override
    public double getIFator() {
        return plotObjManager.getIZF();
    }

    @Override
    public double getIInc() {
        return plotObjManager.getIIncZ();
    }

    @Override
    public double getIDesloc() {
        return plotObjManager.getIDeslocZ();
    }

    @Override
    public void setMin(double min) {
        plotObjManager.setMinZ( min ); 
    }

    @Override
    public void setMax(double max) {
        plotObjManager.setMaxZ( max );
    }

    @Override
    public void setIN1(double in1) {
        plotObjManager.setIZ1( in1 ); 
    }

    @Override
    public void setIN2(double in2) {
        plotObjManager.setIZ2( in2 ); 
    }

    @Override
    public void setIFator(double ifator) {
        plotObjManager.setIZF( ifator ); 
    }

    @Override
    public void setIInc(double iinc) {
        plotObjManager.setIIncZ( iinc );
    }

    @Override
    public void setIDesloc(double desloc) {
        plotObjManager.setIDeslocZ( desloc ); 
    }
    
}

