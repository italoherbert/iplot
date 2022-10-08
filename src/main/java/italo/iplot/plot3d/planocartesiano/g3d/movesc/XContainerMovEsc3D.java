package italo.iplot.plot3d.planocartesiano.g3d.movesc;

import italo.iplot.planocartesiano.movesc.PCContainerMovEsc;
import italo.iplot.plot3d.planocartesiano.g3d.PlotObj3DManager;

public class XContainerMovEsc3D implements PCContainerMovEsc {

    private final PlotObj3DManager plotObjManager;

    public XContainerMovEsc3D(PlotObj3DManager plotObjManager) {
        this.plotObjManager = plotObjManager;
    }
    
    @Override
    public double getDN() {
        return plotObjManager.getContainer().getDX();
    }

    @Override
    public double getMin() {
        return plotObjManager.getMinX();
    }

    @Override
    public double getMax() {
        return plotObjManager.getMaxX();
    }

    @Override
    public double getIN1() {
        return plotObjManager.getIX1();
    }

    @Override
    public double getIN2() {
        return plotObjManager.getIX2();
    }

    @Override
    public double getIFator() {
        return plotObjManager.getIXF();
    }

    @Override
    public double getIInc() {
        return plotObjManager.getIIncX();
    }

    @Override
    public double getIDesloc() {
        return plotObjManager.getIDeslocX();
    }

    @Override
    public void setMin(double min) {
        plotObjManager.setMinX( min ); 
    }

    @Override
    public void setMax(double max) {
        plotObjManager.setMaxX( max );
    }

    @Override
    public void setIN1(double in1) {
        plotObjManager.setIX1( in1 ); 
    }

    @Override
    public void setIN2(double in2) {
        plotObjManager.setIX2( in2 ); 
    }

    @Override
    public void setIFator(double ifator) {
        plotObjManager.setIXF( ifator ); 
    }

    @Override
    public void setIInc(double iinc) {
        plotObjManager.setIIncX( iinc );
    }

    @Override
    public void setIDesloc(double desloc) {
        plotObjManager.setIDeslocX( desloc ); 
    }
    
}
