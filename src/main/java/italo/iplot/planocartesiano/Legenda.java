package italo.iplot.planocartesiano;

import java.awt.Color;

public class Legenda {
    
    public final static int LINHA = 1;
    public final static int PONTO = 2;
    
    private String legenda;
    private Color cor;
    private int tipo = LINHA;

    public Legenda( String legenda, Color cor ) {
        this(legenda, cor, LINHA );
    }
    
    public Legenda( String legenda, Color cor, int tipo ) {
        this.legenda = legenda;
        this.cor = cor;
        this.tipo = tipo;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}
