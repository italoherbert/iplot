package italo.iplot.plot3d.planocartesiano.g3d.divs;

import italo.iplot.planocartesiano.regua.ReguaUtil;

public abstract class AbstractDivisoes implements Divisoes {
                
    @Override
    public double calculaH( ReguaUtil rutil, int i ) {
        double n1 = this.getN1();
        double n2 = this.getN2(); 
        double inc = this.getInc();
        double desloc = this.getIDesloc();
        int numRotulos = this.getNumRotulos();
        double dn = this.getDN();
        
        double borda = rutil.calculaBorda( numRotulos, inc, n1, n2 );
        borda += 2 * desloc;
        
        double pch = rutil.calculaPlanoCartesianoH( inc, borda, i );
        return rutil.calculaH( dn, n1, n2, pch );
    }
        
}
