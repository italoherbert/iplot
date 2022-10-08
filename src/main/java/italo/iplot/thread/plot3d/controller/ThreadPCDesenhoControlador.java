package italo.iplot.thread.plot3d.controller;

import italo.iplot.gui.DesenhoUI;
import italo.iplot.gui.DesenhoGUIListener;
import italo.iplot.thread.plot3d.PCPlot3DOperManagerThread;

public class ThreadPCDesenhoControlador implements DesenhoGUIListener {
    
    private final PCPlot3DOperManagerThread operManagerThread;

    public ThreadPCDesenhoControlador( PCPlot3DOperManagerThread operManagerThread ) {
        this.operManagerThread = operManagerThread;
    }
    
    @Override
    public void arrastou( DesenhoUI ui ) {        
        operManagerThread.semConfigConstroi( true ); 
    }

    @Override
    public void moveu( DesenhoUI ui ) {
        
    }
        
}
