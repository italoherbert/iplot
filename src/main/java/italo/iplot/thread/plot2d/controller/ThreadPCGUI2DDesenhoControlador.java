package italo.iplot.thread.plot2d.controller;

import italo.iplot.gui.DesenhoUI;
import italo.iplot.gui.plot.Plot2DGUIListener;
import italo.iplot.gui.plot.Plot2DGUI;
import italo.iplot.thread.plot2d.PCPlot2DOperManagerThread;
import java.awt.event.MouseEvent;

public class ThreadPCGUI2DDesenhoControlador implements Plot2DGUIListener {

    private final PCPlot2DOperManagerThread operManagerThread;

    public ThreadPCGUI2DDesenhoControlador( PCPlot2DOperManagerThread operManagerThread ) {
        this.operManagerThread = operManagerThread;
    }
    
    @Override
    public void arrastou(Plot2DGUI gui, DesenhoUI ui) {
        if ( gui.getBTSelecionado() == Plot2DGUI.MOVER ) { 
            operManagerThread.move();
        }
    }

    @Override
    public void moveu(Plot2DGUI gui, DesenhoUI ui) {
        
    }

    @Override
    public void clicou(Plot2DGUI gui, DesenhoUI ui, MouseEvent e) {
        if ( gui.getBTSelecionado() == Plot2DGUI.ZOOM_MAIS ) {
            operManagerThread.zoomMais();
        } else if ( gui.getBTSelecionado() == Plot2DGUI.ZOOM_MENOS ) {
            operManagerThread.zoomMenos();
        }
    }
    
    @Override
    public void apontadorBTAcionado(Plot2DGUI gui) {
        
    }
        
    @Override
    public void moverBTAcionado(Plot2DGUI gui) {
        
    }

    @Override
    public void zoomMaisBTAcionado(Plot2DGUI gui) {
        
    }

    @Override
    public void zoomMenosBTAcionado(Plot2DGUI gui) {
        
    }

    @Override
    public void gradeBTAcionado(Plot2DGUI gui) {
        operManagerThread.gradeVisivel();
    }

    @Override
    public void reguaBTAcionado(Plot2DGUI gui) {
        operManagerThread.reguaVisivel();
    }

    @Override
    public void ajusteBTAcionado(Plot2DGUI gui) {
        operManagerThread.ajuste();
    }

    @Override
    public void mouseLinhasBTAcionado(Plot2DGUI gui) {
        operManagerThread.mouseLinhasVisivel();
    }
    
    public PCPlot2DOperManagerThread getOperManagerThread() {
        return operManagerThread;
    }
    
}
