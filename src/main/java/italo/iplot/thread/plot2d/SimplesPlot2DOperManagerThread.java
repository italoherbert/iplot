package italo.iplot.thread.plot2d;

import italo.iplot.plot2d.Plot2DAplic;

public class SimplesPlot2DOperManagerThread extends Plot2DOperManagerThread {
    
    public SimplesPlot2DOperManagerThread( Plot2DAplic aplic ) {
        super( aplic );        
    }

    @Override
    protected void inicializaObjeto2D() {}

    @Override
    protected void constroi() {
        aplic.getUniversoVirtual().constroi( aplic );
    }     
    
}
