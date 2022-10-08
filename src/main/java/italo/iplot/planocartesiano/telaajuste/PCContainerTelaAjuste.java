package italo.iplot.planocartesiano.telaajuste;

import java.awt.Font;

public interface PCContainerTelaAjuste {
    
    public double getMaxLargura();
    
    public double getMaxAltura();
            
    public String getTitulo();
    
    public Font getTituloFont();
    
    public double getReguaYValorLarguraMax();
    
    public double getReguaYValorAlturaMax();
    
    public void setReguaYValorLarguraMax( double max );
    
    public void setReguaYValorAlturaMax( double max );
        
    public int getBordaPX();

    public int getTituloGraficoDistanciaPX();
    
    public int getReguaValorDistanciaPX();
    
    public int getEixoRotulosDistanciaPX();
    
    public int getReguaTracoComprimentoPX();
        
    public String getYEixoRotulo();
    
    public String getXZEixoRotulo();
    
    public Font getEixoRotuloFont();
    
    public Font getReguaValoresFont();
    
    public PCPlotObjManagerTelaAjuste getPCPlotObjManager();
    
    public boolean isPintarRegua();
    
    public boolean isPintarEixoRotulos();          
    
    public double getCentroX();
    
    public double getCentroY();
    
    public double getPCX();
    
    public double getPCY();
    
    public double getPCLargura();
    
    public double getPCAltura();
        
    public void setPCX( double x );
    
    public void setPCY( double y );
    
    public void setPCLargura( double largura );
    
    public void setPCAltura( double altura );
                
}
