package italo.iplot.plot2d.planocartesiano.g2d.movesc;

import italo.iplot.planocartesiano.movesc.PCContainerMovEsc;
import italo.iplot.plot2d.planocartesiano.g2d.PlotObj2DManager;

public class YContainerMovEsc2D implements PCContainerMovEsc {

    private final PlotObj2DManager plotObjManager;

    public YContainerMovEsc2D(PlotObj2DManager plotObjManager) {
        this.plotObjManager = plotObjManager;
    }
    
    @Override
    public double getDN() {
        return plotObjManager.getContainer().getDY();
    }

    @Override
    public double getMin() {
        return plotObjManager.getMinY();
    }

    @Override
    public double getMax() {
        return plotObjManager.getMaxY();
    }

    @Override
    public double getIN1() {
        return plotObjManager.getIY1();
    }

    @Override
    public double getIN2() {
        return plotObjManager.getIY2();
    }

    @Override
    public double getIFator() {
        return plotObjManager.getIYF();
    }

    @Override
    public double getIInc() {
        return plotObjManager.getIIncY();
    }

    @Override
    public double getIDesloc() {
        return plotObjManager.getIDeslocY();
    }

    @Override
    public void setMin(double min) {
        plotObjManager.setMinY( min ); 
    }

    @Override
    public void setMax(double max) {
        plotObjManager.setMaxY( max );
    }

    @Override
    public void setIN1(double in1) {
        plotObjManager.setIY1( in1 ); 
    }

    @Override
    public void setIN2(double in2) {
        plotObjManager.setIY2( in2 ); 
    }

    @Override
    public void setIFator(double ifator) {
        plotObjManager.setIYF( ifator ); 
    }

    @Override
    public void setIInc(double iinc) {
        plotObjManager.setIIncY( iinc );
    }

    @Override
    public void setIDesloc(double desloc) {
        plotObjManager.setIDeslocY( desloc ); 
    }
    
}

