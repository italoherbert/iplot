package italo.iplot.thread.plot3d.controller;

import italo.iplot.gui.DesenhoUI;
import italo.iplot.gui.plot.Plot2DGUI;
import italo.iplot.gui.plot.Plot3DGUIListener;
import italo.iplot.gui.plot.Plot3DGUI;
import italo.iplot.thread.plot3d.PCPlot3DOperManagerThread;
import java.awt.event.MouseEvent;

public class ThreadPCGUIDesenhoControlador implements Plot3DGUIListener {

    private final PCPlot3DOperManagerThread operManagerThread;

    public ThreadPCGUIDesenhoControlador( PCPlot3DOperManagerThread operManagerThread ) {
        this.operManagerThread = operManagerThread;
    }
    
    @Override
    public void arrastou(Plot3DGUI gui, DesenhoUI ui) {
        if ( gui.getBTSelecionado() == Plot3DGUI.GIRAR ) {
            operManagerThread.semConfigConstroi( true );            
        } else if ( gui.getBTSelecionado() == Plot2DGUI.MOVER ) { 
            operManagerThread.move();
        }
    }

    @Override
    public void moveu(Plot3DGUI gui, DesenhoUI ui) {
        
    }

    @Override
    public void clicou(Plot3DGUI gui, DesenhoUI ui, MouseEvent e) {
        if ( gui.getBTSelecionado() == Plot2DGUI.ZOOM_MAIS ) {
            operManagerThread.zoomMais();
        } else if ( gui.getBTSelecionado() == Plot2DGUI.ZOOM_MENOS ) {
            operManagerThread.zoomMenos();
        }
    }

    @Override
    public void apontadorBTAcionado(Plot3DGUI gui) {
        
    }
    
    @Override
    public void girarBTAcionado(Plot3DGUI gui) {
        
    }    
    
    @Override
    public void moverBTAcionado(Plot3DGUI gui) {
        
    }

    @Override
    public void zoomMaisBTAcionado(Plot3DGUI gui) {
        
    }

    @Override
    public void zoomMenosBTAcionado(Plot3DGUI gui) {
        
    }

    @Override
    public void gradeBTAcionado(Plot3DGUI gui) {
        operManagerThread.gradeVisivel();
    }

    @Override
    public void reguaBTAcionado(Plot3DGUI gui) {
        operManagerThread.reguaVisivel();
    }

    @Override
    public void ajusteBTAcionado(Plot3DGUI gui) {
        operManagerThread.ajuste();
    }

    public PCPlot3DOperManagerThread getOperManagerThread() {
        return operManagerThread;
    }
    
}
