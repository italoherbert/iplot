package italo.iplot.plot2d.planocartesiano.objgrafico.draw.inifim;

import italo.iplot.gui.grafico.GraficoBufferPainter;

public class IniciaFinalizaDrawGBuffer implements IniciaFinalizaDraw {

    private final static int DEFAULT_MULT_FATOR = 1;
    
    private final GraficoBufferPainter gbufferPainter;
    private int multFator = 1; 

    public IniciaFinalizaDrawGBuffer( GraficoBufferPainter gbufferPainter ) {        
        this( gbufferPainter, DEFAULT_MULT_FATOR );
    }
    
    public IniciaFinalizaDrawGBuffer( GraficoBufferPainter gbufferPainter, int multFator ) {        
        this.gbufferPainter = gbufferPainter;
        this.multFator = multFator;
    }
    
    @Override
    public void inicia() {
        gbufferPainter.iniciaBuffer( multFator );       
    }

    @Override
    public void finaliza() {
        gbufferPainter.pintaBuffer();
    }

    @Override
    public int getMultFator() {
        return multFator;
    }
    
}
