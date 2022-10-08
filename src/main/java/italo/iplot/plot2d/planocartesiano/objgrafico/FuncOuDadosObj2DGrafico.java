package italo.iplot.plot2d.planocartesiano.objgrafico;

import italo.iplot.grafico.pixel.BufferGraficoPixel;
import italo.iplot.grafico.pixel.doublegpx.DoubleGraficoPixelRound;
import italo.iplot.gui.grafico.Grafico;
import italo.iplot.gui.grafico.GraficoBufferPainter;
import italo.iplot.plot2d.g2d.ComponenteObjeto2D;
import italo.iplot.plot2d.g2d.Objeto2D;
import italo.iplot.plot2d.g2d.Objeto2DGrafico;
import italo.iplot.plot2d.g2d.Objeto2DGraficoDriver;
import italo.iplot.plot2d.planocartesiano.g2d.PCContainerObjeto2D;
import italo.iplot.plot2d.planocartesiano.objgrafico.draw.inifim.IniciaFinalizaDraw;
import italo.iplot.plot2d.planocartesiano.objgrafico.draw.inifim.IniciaFinalizaDrawGBuffer;
import italo.iplot.plot2d.planocartesiano.objgrafico.draw.inifim.IniciaFinalizaDrawVazio;
import italo.iplot.plot2d.planocartesiano.objgrafico.draw.linha.DrawLinha;
import italo.iplot.plot2d.planocartesiano.objgrafico.draw.linha.DrawLinhaNormal;
import italo.iplot.plot2d.planocartesiano.objgrafico.pcrect.PCRect;
import italo.iplot.plot2d.planocartesiano.objgrafico.draw.dadosoufunc.DrawFuncOuDados;

public class FuncOuDadosObj2DGrafico implements Objeto2DGrafico{
    
    private final PCRect pcrect = new PCRect();
    
    private final int fatorSSAA = 2;
    private final int filtroD = 1;
    private final int reduzirEm = 10;
    
    private final DrawFuncOuDados drawFuncOuDados;
    private final BufferGraficoPixel buffer = new BufferGraficoPixel();

    public FuncOuDadosObj2DGrafico(DrawFuncOuDados drawFuncOuDados) {
        this.drawFuncOuDados = drawFuncOuDados;
    }
    
    @Override
    public void desenho( Grafico grafico, Objeto2D obj, Objeto2DGraficoDriver drv ) {
        //PCContainerObjeto2D container = (PCContainerObjeto2D)((ComponenteObjeto2D)obj).getContainerObjeto2D();
                        
        //int[] pcP0 = pcrect.calculaP0( container, drv );
        //int[] pcP1 = pcrect.calculaP1( container, drv );

        buffer.criaBuffer( grafico.getLarg(), grafico.getAlt() );        
        buffer.iniciaBuffer( obj.getGrupo().getCor().getRGB() ); 

        GraficoBufferPainter gbufferPainterSSAA = grafico.getGBufferPainterFactory().criaGraficoBufferPainterSSAA( grafico ); //buffer );
        
        //IniciaFinalizaDraw ifdrawVazio = new IniciaFinalizaDrawVazio();
        IniciaFinalizaDraw ifdrawSSAA = new IniciaFinalizaDrawGBuffer( gbufferPainterSSAA, fatorSSAA );        
        
        DrawLinha dlnormal = new DrawLinhaNormal( new DoubleGraficoPixelRound( gbufferPainterSSAA.getBufferGraficoPixel() ) );                        
        drawFuncOuDados.draw( grafico, obj, drv, dlnormal, ifdrawSSAA );  
                               
        /*
        FiltroConfig fconf = new FiltroConf( pcP0[0], pcP0[1], pcP1[0], pcP1[1] );
                
        FiltroPixel fpx = grafico.getFiltroManager().criaFiltroPixelGaussiana3x3( buffer, fconf );
        GPintaPixelCondicao c = new GPintaPixelCondicaoBorda( pcP0[0], pcP0[1], pcP1[0], pcP1[1] );        
        GraficoPixelFiltro fgpx = new GraficoPixelFiltro( grafico, fpx, c, filtroD );
        DrawLinhaNormal dlnormal2 = new DrawLinhaNormal( new DoubleGraficoPixelRound( fgpx ) );         
        
        drawFuncOuDados.draw( grafico, obj, drv, dlnormal2, ifdrawVazio );                                      
                
        FiltroPixel fpx2 = grafico.getFiltroManager().criaFiltroPixelEscurece( grafico, reduzirEm );
        GPintaPixelCondicao c2 = new GPintaPixelCondicaoBorda( pcP0[0], pcP0[1], pcP1[0], pcP1[1] );
        GraficoPixelFiltro fgpx2 = new GraficoPixelFiltro( grafico, fpx2, c2, filtroD );
        DrawLinhaNormal dlfRealca = new DrawLinhaNormal( new DoubleGraficoPixelRound( fgpx2 ) );        
        
        drawFuncOuDados.draw( grafico, obj, drv, dlfRealca, ifdrawVazio );                 
        */
    }
    
}
