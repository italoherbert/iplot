package italo.iplot.thread.plot3d;

import italo.iplot.plot3d.Plot3DAplic;

public class SimplesPlot3DOperManagerThread extends Plot3DOperManagerThread {
    
    public SimplesPlot3DOperManagerThread( Plot3DAplic aplic ) {
        super( aplic );        
    }

    @Override
    protected void inicializaObjeto3D() {}

    @Override
    protected void transforma() {
        aplic.getUniversoVirtual().aplicaTransformacoes();
    }

    @Override
    protected void constroi() {
        aplic.getUniversoVirtual().constroi( aplic );                        
    }       
        
}
