package italo.iplot.plot3d.g3d.util;

import italo.iplot.gui.Tela;
import italo.iplot.plot3d.g3d.Aresta3D;
import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.ParalelepipedoObjeto3D;
import italo.iplot.plot3d.g3d.Vertice3D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import italo.iplot.plot3d.g3d.vert.FiltroVert3D;

public class Math3D {

    public final static double PROX_DE_ZERO = 0.00000000001d;    
        
    public double getMeioH( Tela tela ) {
        return this.getMeioH( tela.getTelaLargura(), tela.getTelaAltura() );
    }
    
    public double getMeioH( double largura, double altura ) {
        return Math.min( altura, largura ) *.5d;
    }
    
    public double verticeUnidade( double n, Tela tela ) {
        return n / this.getMeioH( tela );
    }
        
    public double telaUnidade( double n, Tela tela ) {
        return n * this.getMeioH( tela );
    }
                           
    public double distancia( Vertice3D u, Vertice3D v, FiltroVert3D filtro ) {
        double[] uP = filtro.getPonto3D( u );
        double[] vP = filtro.getPonto3D( v );
        return this.distancia( uP, vP );
    }
    
    public double distancia( double[] uP, double[] vP ) {
        return Math.sqrt( Math.pow( vP[0]-uP[0], 2 ) + Math.pow( vP[1]-uP[1], 2) + Math.pow( vP[2]-uP[2], 2 ) );
    }
    
    public int[] minMaxYVertIndices( List<Vertice3D> lista, FiltroVert3D filtro ) {
        if ( lista.isEmpty() )
            return null;
        int minI = 0;
        int maxI = 0;
        
        int tam = lista.size();
        for( int i = 1; i < tam; i++) {
            double y = filtro.getY( lista.get( i ) );
            double minY = filtro.getY( lista.get( minI ) );
            double maxY = filtro.getY( lista.get( maxI ) ); 
               
            if ( y < minY )
                minI = i;
            if ( y > maxY )
                maxI = i;
        }
        
        return new int[] { minI, maxI };
    }
    
    public double[][] calculaMinMaxXYZ( List<Vertice3D> lista, FiltroVert3D filtro ) {
        double minX, minY, maxX, maxY, minZ, maxZ;
        minX = minY = minZ = Double.MAX_VALUE;
        maxX = maxY = maxZ = Double.MIN_VALUE;
        
        for( Vertice3D v : lista ) {
            double x = filtro.getX( v );
            double y = filtro.getY( v );
            double z = filtro.getZ( v );
            
            if ( x < minX )
                minX = x;
            if ( y < minY )
                minY = y;
            if ( z < minZ )
                minZ = z;
            if ( x > maxX )
                maxX = x;
            if ( y > maxY )
                maxY = y;
            if ( z > maxZ )
                maxZ = z;
        }
        
        return new double[][] {
            { minX, minY, minZ },
            { maxX, maxY, maxZ }                
        };        
        
    }        
    public double[] calculaMinMaxY( List<Vertice3D> vertices, FiltroVert3D filtro ) {
        double maiorY = Double.MIN_VALUE;        
        double menorY = Double.MAX_VALUE;
        for( Vertice3D v : vertices ) {
            double y = filtro.getY( v );                        
            if ( y > maiorY )
                maiorY = y;
            if ( y < menorY )
                menorY = y;
        }
        
        return new double[] { menorY, maiorY };
    }
    
    public double[] calculaMinMaxD( Face3D base, List<Vertice3D> vertices, FiltroVert3D filtro ) {
        double[][] plano = this.planoPontos( base, filtro );
        
        double maiorD = Double.NEGATIVE_INFINITY;        
        double menorD = Double.POSITIVE_INFINITY;
        for( Vertice3D v : vertices ) {
            double[] p = filtro.getPonto3D( v );                        
            double d = this.distanciaPontoEPlano( plano, p );
            if ( d > maiorD )
                maiorD = d;
            if ( d < menorD )
                menorD = d;
        }
        
        return new double[] { menorD, maiorD };
    }
    
    public double ajusteATelaFatorProporcao( ParalelepipedoObjeto3D obj, double h, Tela tela ) {                        
        double[] v1 = obj.getTrazV1().getP();
        double[] v2 = obj.getFrenteV3().getP();
        
        double[] v3 = obj.getTrazV2().getP();
        double[] v4 = obj.getFrenteV4().getP();
        
        double d1 = this.distancia( v1, v2 );
        double d2 = this.distancia( v3, v4 );
        
        double diagonal = Math.max( d1, d2 );
        double meioH = this.verticeUnidade( this.getMeioH( tela ), tela );
                                
        return ( h * meioH ) / diagonal;
    }
                
    public List<Face3D> facesOrganizadasPorZ( List<Face3D> faces, FiltroVert3D filtro ) {
        List<Face3D> frente = new ArrayList();
        List<Face3D> traz = new ArrayList();
        for( Face3D f : faces ) {
            double[] vnf = vetorNormal( f, filtro );            
            if ( vnf[2] >= 0 )
                frente.add( f );
            else traz.add( f );
            
            f.setOrdZ( pontoMedioZ( f, filtro ) ); 
        }
        
        this.ordenaPorZ( frente, filtro );
        List<Face3D> lista = new ArrayList( faces.size() );
        lista.addAll( traz );
        lista.addAll( frente );
        return lista;
    }
            
    public void ordenaPorZ( List<Face3D> faces, FiltroVert3D filtro ) {            
        Collections.sort( faces, ( f1, f2 ) -> {              
            double zvn1 = f1.getOrdZ();
            double zvn2 = f2.getOrdZ();
                        
            if ( zvn1 > zvn2 )
                return 1;
            if ( zvn1 < zvn2 )
                return -1;            
            return 0;            
        } );        
    }     
                
    public Color gradienteCor( Face3D base, Face3D face, double[] minMaxDs, Color[] gradienteCores, FiltroVert3D filtro ) {                                 
        double[][] plano = this.planoPontos( base, filtro );
                
        double menorD = minMaxDs[0];
        double maiorD = minMaxDs[1];
        double mediaD = 0;
        
        for( Vertice3D v : face.getVertices() )  {
            double[] p = filtro.getPonto3D( v );            
            double d = this.distanciaPontoEPlano( plano, p );                                   
            mediaD += d;
        }
        
        if ( mediaD > 0 )
            mediaD /= face.getVertices().size();
                                            
        return this.calculaGradienteCores( gradienteCores, menorD, maiorD, mediaD );                        
    }
    
    public Color gradienteCor( Face3D base, Aresta3D aresta, double[] minMaxDs, Color[] gradienteCores, FiltroVert3D filtro ) {
        double[] p1 = filtro.getPonto3D( aresta.getV1() );
        double[] p2 = filtro.getPonto3D( aresta.getV2() );
        double[][] plano = this.planoPontos( base, filtro );
        
        double d1 = this.distanciaPontoEPlano( plano, p1 );
        double d2 = this.distanciaPontoEPlano( plano, p2 );
                
        double minD = minMaxDs[0];
        double maxD = minMaxDs[1];
        double meioD = ( minD + maxD ) / 2;
        double d3 = Math.abs( d1 - meioD );
        double d4 = Math.abs( d2 - meioD );                
        
        double d = ( d3 > d4 ? d1 : d2 );
        return this.calculaGradienteCores( gradienteCores, minD, maxD, d );
    }
    
    public Color calculaGradienteCores( Color[] cores, double minD, double maxD, double d ) {                                                                       
        if ( minD == maxD )
            return cores[ 0 ];        
        
        double u = Math.abs( maxD - minD ) / ( cores.length - 1 );
        int i = (int)Math.floor( Math.abs( d - maxD ) / u );                        
        int j = i+1;                                                                       
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
        Color cor1 = cores[ i ];        
        Color cor2 = cores[ j > cores.length-1 ? cores.length-1 : j ];
        
        double min = i * u;
        double max = j * u;
        double media = Math.abs( d - maxD );
                
        double proporcaoCor1 = ( max - media ) / ( max - min );
        double proporcaoCor2 = ( media - min ) / ( max - min );
                                       
        int r = (int)Math.round( ( proporcaoCor1 * cor1.getRed()   ) + ( proporcaoCor2 * cor2.getRed()   ) );
        int g = (int)Math.round( ( proporcaoCor1 * cor1.getGreen() ) + ( proporcaoCor2 * cor2.getGreen() ) );
        int b = (int)Math.round( ( proporcaoCor1 * cor1.getBlue()  ) + ( proporcaoCor2 * cor2.getBlue()  ) );
        if ( r > 255 ) r = 255;
        if ( g > 255 ) g = 255;
        if ( b > 255 ) b = 255;
                
        return new Color( r, g, b );        
    }
    
    public Color calculaCor( double[][] luzes, Aresta3D aresta, Color cor, FiltroVert3D filtro ) {                
        double[] p1 = filtro.getPonto3D( aresta.getV1() );
        double[] p2 = filtro.getPonto3D( aresta.getV2() );
        
        double[] maiorZP = ( p1[2] > p2[2] ? p1 : p2 );
        
        double[] p3 = { maiorZP[0] + 1.0d, maiorZP[1], (p1[2] + p2[2]) / 2 };
        
        double[] vn1 = vetorNormal( p1, p2, p3, false );
        double[] vn2 = mult( vn1, -1 );
        double[] vn = ( vn1[2] > vn2[2] ? vn1 : vn2 );
        
        return this.calculaCor( luzes, vn, cor );        
    }

    public Color calculaCor( double[][] luzes, Face3D f, Color cor, FiltroVert3D filtro ) {
        double[] vn = vetorNormal( f, filtro );
        return this.calculaCor( luzes, vn, cor );
    }
    
    public Color calculaCor( double[][] luzes, double[] vn, Color cor ) {
        int r = 0;
        int g = 0;
        int b = 0;
                        
        for( double[] vluz : luzes ) {
            double a = this.angulo( vn, vluz );                
            if ( a < ( Math.PI * .5d ) ) {
                double fator = ( ( Math.PI * .5d ) - a ) / ( Math.PI * .5d );
                                
                r += (int)Math.round( cor.getRed() * fator );
                g += (int)Math.round( cor.getGreen() * fator );
                b += (int)Math.round( cor.getBlue() * fator );
            }                     
        }
        if ( r > 255 ) r = 255;
        if ( g > 255 ) g = 255;
        if ( b > 255 ) b = 255;
        return new Color( r, g, b );
    }
    
    public double distanciaPontoEPlano( double[][] plano, double[] ponto ) {
        double[] p1 = plano[0];
        double[] p2 = plano[1];
        double[] p3 = plano[2];

        double[] proj = this.projecaoOrtogonalDePontoSobrePlano( ponto, p1, p2, p3 );
        return this.distancia( proj, ponto );
    }
    
    public int[] pontoPX( double[] vert, Tela tela ) {                                        
        int cx = tela.getTelaLargura() / 2;
        int cy = tela.getTelaAltura()  / 2;        

        int h = Math.min( cx, cy );
        
        return new int[] {
            tela.getTelaX() + cx + (int)Math.floor( vert[0] * h ),
            tela.getTelaY() + cy - (int)Math.floor( vert[1] * h ) 
        };                                              
    }
            
    public double[] doublePontoPX( double[] vert, Tela tela ) {                                        
        int cx = tela.getTelaLargura() / 2;
        int cy = tela.getTelaAltura()  / 2;        

        int h = Math.min( cx, cy );
        
        return new double[] {
            tela.getTelaX() + cx + vert[0] * h,
            tela.getTelaY() + cy - vert[1] * h 
        };                                              
    }

    public double[] verticeNoPlano3D( double x, double y, Tela tela ) {
        return this.verticeNoPlano3D( x, y, tela.getTelaX(), tela.getTelaY(), tela.getTelaLargura(), tela.getTelaAltura() );
    }
    
    public double[] verticeNoPlano3D( double x, double y, int telaX, int telaY, int telaLargura, int telaAltura ) {
        int cx = telaLargura / 2;
        int cy = telaAltura  / 2;        

        int h = Math.min( cx, cy );

        return new double[] {
            ( x - telaX - cx ) / h,
            ( telaY + cy - y ) / h 
        };
    }
       
    public double calculaZ( double x, double y, double[] p0, double[] p1 ) {        
        double[] p2 = { p0[0] + p1[0] + 1, p0[1] + p1[1] + 1, p0[2] + p1[2] + 1 };
        return this.calculaZ( x, y, p0, p1, p2 );
    }
    
    public double calculaZ( double x, double y, double[] p0, double[] p1, double[] p2 ) { // Não verifica se colineares 
        double[] v1 = sub( p1, p0 );
        double[] v2 = sub( p2, p0 );        
        
        return ( ( ( x - p0[0] ) * ( v1[1]*v2[2] - v1[2]*v2[1] ) ) + ( ( y - p0[1] ) * ( v1[2]*v2[0] - v1[0]*v2[2] ) ) + ( p0[2] * (v1[1]*v2[0] - v1[0]*v2[1]) ) ) / ( v1[0]*v2[1] - v1[1]*v2[0] );
    }
    
    public List<Face3D> facesCoplanares( List<Face3D> faces, double[] p1, double[] p2, double[] p3, FiltroVert3D filtro ) {
        List<Face3D> coplanares = new ArrayList();
        for( Face3D f : faces )
            if ( this.verificaSeVerticesPertencemAPlano( f.getVertices(), p1, p2, p3, filtro ) )
                coplanares.add( f );        
        return coplanares;
    }
     
    public boolean verificaSePontoEmSuperficie( List<Face3D> faces, double[] ponto, FiltroVert3D filtro ) {
        for( Face3D f : faces )
            if ( this.verificaSePontoPertenceAFace( f.getVertices(), ponto, filtro ) )
                return true;        
        return false;
    }
    
    public boolean verificaSeVerticesPertencemAPlano( List<Vertice3D> vertices, double[] p1, double[] p2, double[] p3, FiltroVert3D filtro ) {        
        for( Vertice3D v : vertices ) {
            double[] ponto = filtro.getPonto3D( v );
            if ( !this.verificaSePontoPertenceAPlano( ponto, p1, p2, p3 ) )
                return false;
        }
        return true;
    }
            
    public boolean verificaSeVerticesPertencemAFace( Face3D f, List<Vertice3D> vertices, FiltroVert3D filtro ) {        
        for( Vertice3D v : vertices ) {
            double[] ponto = filtro.getPonto3D( v );
            if ( !this.verificaSePontoPertenceAFace( f.getVertices(), ponto, filtro ) )
                return false;
        }
        return true;
    }
    
    public boolean verificaSeLinhasSeCruzam( double[] l1_p0, double[] l1_p, double[] l2_p0, double[] l2_p ) {
        double[] p_int = this.intersecaoRetas( l1_p0, l1_p, l2_p0, l2_p );
        if ( p_int == null )
            return false;
        if ( this.verificaSeParalelepipedoContemPonto( l1_p0, l1_p, p_int ) )
            if ( this.verificaSeParalelepipedoContemPonto( l2_p0, l2_p, p_int ) )
                return true;
        return false;
    }
     
    public boolean verificaSePontoInternoAObjeto( Objeto3D obj, double[] ponto, FiltroVert3D filtro ) {
        List<Face3D> faces = obj.getEstrutura().getFaces();
        return this.verificaSePontoInternoAObjeto( faces, ponto, filtro );
    }
    
    public boolean verificaSePontoInternoAObjeto( List<Face3D> faces, double[] ponto, FiltroVert3D filtro ) {
        for( Face3D f : faces ) {
            double[][] plano = this.planoPontos( f, filtro );
            
            double[] pmedio = this.pontoMedio( plano );
            double[] vn = this.vetorNormal( f, filtro );
            double[] vet = sub( ponto, pmedio );
            double a = this.angulo( vn, vet );
            if ( a <= Math.PI*.5d )
                return false;
        }        
        return true;
    }

    public double[] intersecaoLinhaFace( Face3D face, double[] lp0, double[] lp, FiltroVert3D filtro ) {
        double[][] plano = this.planoPontos( face, filtro );
        double[] p1 = plano[0];
        double[] p2 = plano[1];
        double[] p3 = plano[2];
        double[] p_int = this.intersecaoRetaPlano( lp0, lp, p1, p2, p3 );
        if ( p_int == null )
            return null;
        
        if ( this.verificaSeParalelepipedoContemPonto( lp0, lp, p_int ) )                                                                                                                                       
            if ( this.verificaSePontoPertenceAFace( face.getVertices(), p_int, filtro ) )
                return p_int;
        return null;
    }
        
    public boolean verificaSePontoPertenceAFace( List<Vertice3D> vertices, double[] ponto, FiltroVert3D filtro ) {                                        
        if ( this.verificaSePontoPertenceAPerimetroDaFace( vertices, ponto, filtro ) )
            return true;
        return this.verificaSePontoInternoAFace( vertices, ponto, filtro );
    }

    public boolean verificaSePontoInternoAFace( List<Vertice3D> vertices, double[] ponto, FiltroVert3D filtro ) {                                                        
        int cont = 0;                                                         
        
        Vertice3D vert1 = vertices.get( 0 );
        Vertice3D vert2 = vertices.get( 1 );
        double[] vert_p1 = filtro.getPonto3D( vert1 );
        double[] vert_p2 = filtro.getPonto3D( vert2 );
        double[] p_medio = div( soma( vert_p1, vert_p2 ), 2 );
                    
        double[] pontoV = sub( ponto, p_medio );
        
        if ( this.comprimento( pontoV ) == 0 )
            return true;
                
        List<Vertice3D> lista = new ArrayList();
                                        
        int size = vertices.size();                              
        for( int i = 0; i < size; i++ ) {
            Vertice3D v1 = vertices.get( i );
            Vertice3D v2 = vertices.get( i == size-1 ? 0 : i+1 );            
                                    
            double[] arestaP0 = filtro.getPonto3D( v1 );
            double[] arestaP = filtro.getPonto3D( v2 );                                                           
                        
            double[] p_int = this.intersecaoRetas( arestaP0, arestaP, p_medio, ponto );                                               
            if ( p_int == null )
                continue;
                                                 
            boolean verificaSeIncCont = true;
            if ( this.verificaSeIguais( p_int, arestaP0 ) ) {
                if ( !lista.contains( v1 ) ) {
                    lista.add( v1 );                               
                } else {
                    verificaSeIncCont = false;
                }
            } else if ( this.verificaSeIguais( p_int, arestaP ) ) {
                if ( !lista.contains( v2 ) ) {
                    lista.add( v2 );           
                } else {
                    verificaSeIncCont = false;
                }
            } 
            
            if ( verificaSeIncCont ) {   
                if ( this.verificaSeColineares( p_medio, ponto, p_int ) ) {                   
                    double t;                    
                    if ( pontoV[0] != 0 ) {
                        t = (p_int[0]-p_medio[0]) / (ponto[0]-p_medio[0]);
                    } else if ( pontoV[1] != 0 ) {
                        t = (p_int[1]-p_medio[1]) / (ponto[1]-p_medio[1]);
                    } else {
                        t = (p_int[2]-p_medio[2]) / (ponto[2]-p_medio[2]);
                    }    
                    
                    if ( t >= 1 ) {
                        if ( this.verificaSeParalelepipedoContemPonto( arestaP0, arestaP, p_int ) )
                            cont++;
                    }
                }
            }
        }   
        return cont % 2 == 1;
    }
    
    public boolean verificaSePontoEhVerticeDaFace( List<Vertice3D> vertices, double[] ponto, FiltroVert3D filtro ) {
        int size = vertices.size();                              
        for( int i = 0; i < size; i++ ) {
            Vertice3D v = vertices.get( i );
            double[] v_p = filtro.getPonto3D( v );

            if ( this.verificaSeIguais( v_p, ponto ) )
                return true;
        }
        return false;
    }
    
    public boolean verificaSePontoPertenceAPerimetroDaFace( List<Vertice3D> vertices, double[] ponto, FiltroVert3D filtro ) {
        int size = vertices.size();                              
        for( int i = 0; i < size; i++ ) {
            Vertice3D v1 = vertices.get( i );
            Vertice3D v2 = vertices.get( i == size-1 ? 0 : i+1 );
            double[] arestaP0 = filtro.getPonto3D( v1 );
            double[] arestaP = filtro.getPonto3D( v2 );  
            
            if ( this.verificaSeColineares( arestaP0, arestaP, ponto ) )
                if ( this.verificaSeParalelepipedoContemPonto( arestaP0, arestaP, ponto ) )                    
                    return true;                
        } 
        return false;
    }
                 
    public double[] intersecaoRetaPlano( double[] lp0, double[] lp, double[] p1, double[] p2, double[] p3 ) {        
        if ( this.verificaSeRetaEParalelaAPlano( lp0, lp, p1, p2, p3 ) )
            if ( !this.verificaSeRetaPertenceAPlano( lp0, lp, p1, p2, p3 ) )                
                return null;                    
        
        if ( this.verificaSeRetaEOrtogonalAPlano( lp0, lp, p1, p2, p3) ) 
            return this.projecaoOrtogonalDePontoSobrePlano( lp0, p1, p2, p3 );
        
        return this.intersecaoRetaPlanoSemVerif( lp0, lp, p1, p2, p3 );                                
    }
    
    public double[] intersecaoRetaPlanoSemVerif( double[] lp0, double[] lp, double[] p1, double[] p2, double[] p3 ) {
        double[] v1 = sub( p2, p1 );
        double[] v2 = sub( p3, p1 );
        double[] r  = sub( lp, lp0 );
                
        double num = ((p1[0]-lp0[0])*((v1[1]*v2[2])-(v1[2]*v2[1]))) + ((p1[1]-lp0[1])*((v1[2]*v2[0])-(v1[0]*v2[2]))) + ((p1[2]-lp0[2])*((v1[0]*v2[1])-(v1[1]*v2[0])));
        double den = r[0]*((v1[1]*v2[2])-(v1[2]*v2[1])) + r[1]*((v1[2]*v2[0])-(v1[0]*v2[2])) + r[2]*((v1[0]*v2[1])-(v1[1]*v2[0]));                                  
        
        double t = num / den;
                
        return new double[] {
            lp0[0] + ( t * r[0] ),
            lp0[1] + ( t * r[1] ),
            lp0[2] + ( t * r[2] )
        };        
    }
        
    public double[] angulosEsfericos( double[] v ) {
        double r = Math.sqrt( v[0]*v[0] + v[1]*v[1] + v[2]*v[2] );
        if ( r == 0 )
            return new double[] { 0, 0 };
        double theta = Math.atan2( v[0], v[2] ); 
        double phi = Math.acos( v[1] / r );
        return new double[] { theta, phi };
    }
    
    public double[] coordenadasEsfericas( double phi, double theta, double r ) {
        return new double[] {
            r * Math.sin( theta ) * Math.sin( phi ),
            r * Math.cos( phi ),
            r * Math.cos( theta ) * Math.sin( phi ),            
        };
    }
    
    public double[][] projecaoOrtogonalDeFaceSobrePlanoDeOrigem( Face3D f, FiltroVert3D filtro ) {
        double[] p0 = { 0, 0, 0 };
        double[] vn = this.vetorNormal( f, filtro );
        
        int size = f.getVertices().size();
        double[][] pontos = new double[ size ][];
        int i = 0;
        for( Vertice3D v : f.getVertices() ) {
            double[] ponto = filtro.getPonto3D( v ).clone();
            ponto = this.projecaoOrtogonalDePontoSobrePlano( ponto, p0, vn );
            pontos[ i++ ] = ponto;
        }
        return pontos;
    }
    
    
    public double[] projecaoOrtogonalDePontoSobrePlano( double[] ponto, double[] p1, double[] p2, double[] p3 ) {
        double[][] pontos = { p1, p2, p3 };
        double[] pc = this.pontoMedio( pontos );
        double[] vn = this.vetorNormal( p1, p2, p3, false );
        
        return this.projecaoOrtogonalDePontoSobrePlano( ponto, pc, vn );
    }
    
    public double[] projecaoOrtogonalDePontoSobrePlano( double[] ponto, double[] p0, double[] vn ) {        
        double[] sub = sub( p0, ponto );
        double k = produtoEscalar( sub, vn );
        return soma( ponto, mult( vn, k ) );
    }
    
    public double[] projecaoDeVSobreU( double[] u, double[] v ) {
        double u_x_v = this.produtoEscalar( u, v );
        double u_c = this.comprimento( u );
        double fator = u_x_v / ( u_c * u_c );
        
        return mult( u, fator );
    }

    public double[] intersecaoRetas( double[] lp1, double[] lp2, double[] lp3, double[] lp4 ) {
        if ( this.verificaSeRetasSaoParalelas( lp1, lp2, lp2, lp4 ) )
            return null;
        return this.intersecaoRetasSemVerif( lp1, lp2, lp3, lp4 );                                                       
    }
    
    public double[] intersecaoRetasSemVerif( double[] lp1, double[] lp2, double[] lp3, double[] lp4 ) {                               
        double x1 = arredonda( lp1[0] );
        double y1 = arredonda( lp1[1] );
        double z1 = arredonda( lp1[2] );
        
        double x2 = arredonda( lp3[0] );
        double y2 = arredonda( lp3[1] );
        double z2 = arredonda( lp3[2] );
        
        double l1 = arredonda( lp2[0]-lp1[0] );
        double m1 = arredonda( lp2[1]-lp1[1] );
        double n1 = arredonda( lp2[2]-lp1[2] );
        
        double l2 = arredonda( lp4[0]-lp3[0] );
        double m2 = arredonda( lp4[1]-lp3[1] );
        double n2 = arredonda( lp4[2]-lp3[2] );
        
        double x = x1, y = y1, z = z1;
        
        boolean calculou = false;
        if ( l2 != 0 ) {
            if ( m1 != 0 ) {
                y = ( l2*m1*y2 - l1*m2*y1 + ( m1*m2*(x1-x2) ) ) / ( l2*m1 - l1*m2 );                                
                x = ( l1*(y-y1) + m1*x1 ) / m1;
                z = ( n1*(y-y1) + m1*z1 ) / m1;                
                calculou = true;
            } else if ( n1 != 0 ) {
                z = ( l1*n2*z1 - l2*n1*z2 + ( n1*n2*(x2-x1) ) ) / ( l1*n2 - l2*n1 );                
                x = ( l1*(z-z1) + n1*x1 ) / n1;
                y = ( m1*(z-z1) + n1*y1 ) / n1;                             
                calculou = true;
            }
        }
        
        if ( !calculou && m2 != 0 ) {            
            if ( l1 != 0 ) {                
                x = ( l1*m2*x2 - l2*m1*x1 + ( l1*l2*(y1-y2) ) ) / ( l1*m2 - l2*m1 );                
                y = ( m1*(x-x1) + l1*y1 ) / l1;
                z = ( n1*(x-x1) + l1*z1 ) / l1;
                calculou = true;
            } else if ( n1 != 0 ) {         
                z = ( m1*n2*z1 - m2*n1*z2 + ( n1*n2*(y2-y1) ) ) / ( m1*n2 - m2*n1 );
                x = ( l1*(z-z1) + n1*x1 ) / n1;
                y = ( m1*(z-z1) + n1*y1 ) / n1;
                calculou = true;
            }
        } 
        
        if ( !calculou ) {
            if ( l1 != 0 ) {
                x = ( l1*n2*x2 - l2*n1*x1 + ( l1*l2*(z1-z2) ) ) / ( l1*n2 - l2*n1 );                      
                y = ( m1*(x-x1) + l1*y1 ) / l1;
                z = ( n1*(x-x1) + l1*z1 ) / l1;
            } else if ( m1 != 0 ) {
                y = ( m2*n1*y1 - m1*n2*y2 + ( m1*m2*(z2-z1) ) ) / ( m2*n1 - m1*n2 );                          
                x = ( l1*(y-y1) + m1*x1 ) / m1;
                z = ( n1*(y-y1) + m1*z1 ) / m1;                                
            } else {
                x = x1;
                y = y1;
                z = z1;
            }            
        }
                                
        return new double[] { x, y, z };
    }
    
    public double distanciaPontoReta( double[] ponto, double[] lp0, double[] lp ) {
        double[] r = sub( lp, lp0 );
        double[] a = sub( ponto, lp0 );
        double[] prod_vet = produtoVetorial( a, r );
        return this.comprimento( prod_vet ) / this.comprimento( r ); 
    }           
            
    public boolean verificaSeRetasSaoOrtogonais( double[] l1_p1, double[] l1_p2, double[] l2_p1, double[] l2_p2 ) {
        double[] l1_v = sub( l1_p2, l1_p1 );
        double[] l2_v = sub( l2_p2, l2_p1 );
        double prod = l1_v[0]*l2_v[0] + l1_v[1]*l2_v[1] + l1_v[2]*l2_v[2];
        
        return Math.abs( prod ) < PROX_DE_ZERO;
    }
    
    public boolean verificaSeRetasSaoParalelas( double[] l1_p1, double[] l1_p2, double[] l2_p1, double[] l2_p2 ) {
        double[] l1_v = sub( l1_p2, l1_p1 );
        double[] l2_v = sub( l2_p2, l2_p1 );
        
        return ( 
            Math.abs( l1_v[0]*l2_v[1] - l1_v[1]*l2_v[0] ) < PROX_DE_ZERO && 
            Math.abs( l1_v[0]*l2_v[2] - l1_v[2]*l2_v[0] ) < PROX_DE_ZERO && 
            Math.abs( l1_v[1]*l2_v[2] - l1_v[2]*l2_v[1] ) < PROX_DE_ZERO
        );
    }
    
    public boolean verificaSeRetaEOrtogonalAPlano( double[] lp0, double[] lp, double[] p1, double[] p2, double[] p3 ) {
        double[] vn = this.vetorNormal( p1, p2, p3 );
        double[] r = sub( lp, lp0 );
        
        return ( 
            Math.abs( vn[0]*r[1] - vn[1]*r[0] ) < PROX_DE_ZERO && 
            Math.abs( vn[0]*r[2] - vn[2]*r[0] ) < PROX_DE_ZERO && 
            Math.abs( vn[1]*r[2] - vn[2]*r[1] ) < PROX_DE_ZERO
        );
    }
    
    public boolean verificaSeRetaEParalelaAPlano( double[] lp0, double[] lp, double[] p1, double[] p2, double[] p3 ) {
        double[] vn = this.vetorNormal( p1, p2, p3 );
        double[] r = sub( lp, lp0 );
        double produtoVet = vn[0]*r[0] + vn[1]*r[1] + vn[2]*r[2];
        
        return Math.abs( produtoVet ) < PROX_DE_ZERO;
    }
        
    public boolean verificaSeRetaPertenceAPlano( double[] lp0, double[] lp, double[] p1, double[] p2, double[] p3 ) {
        if ( this.verificaSePontoPertenceAPlano( lp0, p1, p2, p3 ) )
            if ( this.verificaSePontoPertenceAPlano( lp, p1, p2, p3 ) )
                return true;
        return false;
    }
    
    public boolean verificaSePontoPertenceAPlano( double[] ponto, double[] p1, double[] p2, double[] p3 ) {
        double[] v1 = sub( ponto, p1 );
        double[] v2 = sub( p2, p1 );
        double[] v3 = sub( p3, p1 );
        
        double det = (v1[0]*v2[1]*v3[2]) + (v1[1]*v2[2]*v3[0]) + (v1[2]*v2[0]*v3[1]) - (v1[2]*v2[1]*v3[0]) - (v1[0]*v2[2]*v3[1]) - (v1[1]*v2[0]*v3[2]);
        return Math.abs( det ) < PROX_DE_ZERO;
    }    
    
    public boolean verificaSeColineares( double[] p0, double[] p1, double[] p2 ) {        
        double det1 = ( (p1[0]-p0[0])*(p2[1]-p0[1]) - (p2[0]-p0[0])*(p1[1]-p0[1]) );
        double det2 = ( (p1[0]-p0[0])*(p2[2]-p0[2]) - (p2[0]-p0[0])*(p1[2]-p0[2]) );
        double det3 = ( (p1[1]-p0[1])*(p2[2]-p0[2]) - (p2[1]-p0[1])*(p1[2]-p0[2]) );
                
        return ( Math.abs( det1 ) < PROX_DE_ZERO ) && ( Math.abs( det2 ) < PROX_DE_ZERO ) && ( Math.abs( det3 ) < PROX_DE_ZERO );        
    }
            
    public boolean verificaSeParalelepipedoContemPonto( double[] p0, double[] p, double[] ponto ) {                                                
        double[] mins = {
            Math.min( p0[0], p[0] ),
            Math.min( p0[1], p[1] ),
            Math.min( p0[2], p[2] )
        };
                
        double[] maxs = {
            Math.max( p0[0], p[0] ),
            Math.max( p0[1], p[1] ),
            Math.max( p0[2], p[2] )
        };                        
        
        return ( ponto[0] >= mins[0]-PROX_DE_ZERO && ponto[0] <= maxs[0]+PROX_DE_ZERO && 
                ponto[1] >= mins[1]-PROX_DE_ZERO && ponto[1] <= maxs[1]+PROX_DE_ZERO && 
                ponto[2] >= mins[2]-PROX_DE_ZERO && ponto[2] <= maxs[2]+PROX_DE_ZERO );           
    }            
        
    public double[] pontoMedio( double[][] pontos ) {
        double[] pmedia = { 0, 0, 0 };
        for( int i = 0; i < 3; i++ ) {
            for( int j = 0; j < pontos.length; j++ )
                pmedia[ i ] += pontos[j][i];                                                   
            pmedia[ i ] /= pontos.length;
        }
        return pmedia;
    }

    public double pontoMedioZ( Face3D f, FiltroVert3D filtro ) {
        double mz = 0;
        if ( !f.getVertices().isEmpty() ) {
            int size = f.getVertices().size();
            for( int j = 0; j < size; j++ ) {                
                Vertice3D v = f.getVertices().get( j );
                mz += filtro.getZ( v );         
            }
            mz /= size;
        }
        return mz;
    }
    
    public double[] pontoMedio( Face3D f, FiltroVert3D filtro ) {
        double[] pmedia = { 0, 0, 0 };
        if ( !f.getVertices().isEmpty() ) {
            int size = f.getVertices().size();
            for( int i = 0; i < 3; i++ ) {
                for( int j = 0; j < size; j++ ) {                
                    Vertice3D v = f.getVertices().get( j );
                    double[] p = filtro.getPonto3D( v );
                    pmedia[ i ] += p[ i ];         
                }
                pmedia[ i ] /= size;
            }
        }
        return pmedia;
    }
    
    public double[] maxZVertice( Face3D f, FiltroVert3D filtro ) {
        double[] max = { 0, 0, 0 };
        double zmax = Double.MIN_VALUE;
        for( Vertice3D v : f.getVertices() ) {
            double[] p = filtro.getPonto3D( v );
            if ( p[2] > zmax ) {
                max = p;
                zmax = p[2];
            }                
        }
        return max;
    }
        
    public double[] vetorNormal( Face3D face, FiltroVert3D filtro ) {        
        double[][] pontos = this.planoPontos( face, filtro );        
        if ( pontos != null )
            return this.vetorNormal( pontos[0], pontos[1], pontos[2], face.isInverterVN() );        
        
        return new double[] { 0, 0, 0 };
    }
    
    public double[] vetorNormal( double[] p1, double[] p2, double[] p3 ) {                                            
        return this.vetorNormal( p1, p2, p3, false );
    }
    
    public double[] vetorNormal( double[] p1, double[] p2, double[] p3, boolean inverterVN ) {                                            
        double[] v1 = sub( p2, p1 );
        double[] v2 = sub( p3, p1 );
        
        double[] vn = this.produtoVetorial( v1, v2 );        
        if ( inverterVN )
            vn = mult( vn, -1.0d );
        vn = div( vn, comprimento( vn ) );
        
        return vn;       
    }        

    public double[][] planoPontos( List<Vertice3D> vertices, FiltroVert3D filtro ) {
        if ( vertices.size() < 3 )
            return null;
        
        Vertice3D[] verts = this.verticesNaoColineares( vertices, filtro );
        return new double[][] {
            filtro.getPonto3D( verts[0] ).clone(),
            filtro.getPonto3D( verts[1] ).clone(),
            filtro.getPonto3D( verts[2] ).clone()
        };
    }
    
    public double[][] planoPontos( Face3D face, FiltroVert3D filtro ) {
        Vertice3D[] vertices = face.getVerticesParaVNCalc();
        if ( vertices == null  )
            return null;
        
        return new double[][] {
            filtro.getPonto3D( vertices[0] ).clone(),
            filtro.getPonto3D( vertices[1] ).clone(),
            filtro.getPonto3D( vertices[2] ).clone()
        };
    }
            
    public Vertice3D[] verticesNaoColineares( List<Vertice3D> vertices, FiltroVert3D filtro ) {        
        int size = vertices.size();
        if ( size < 3 )
            return null;        

        Vertice3D v1 = vertices.get( 0 );
        Vertice3D v2 = null;

        double[] p = filtro.getPonto3D( v1 );
        
        int i;
        for( i = 1; v2 == null && i < size; i++ ) {
            Vertice3D v = vertices.get( i );
            double[] p2 = filtro.getPonto3D( v );
            
            if ( Math.abs( p[0] - p2[0] ) > PROX_DE_ZERO || 
                   Math.abs( p[1] - p2[1] ) > PROX_DE_ZERO || 
                   Math.abs( p[2] - p2[2] ) > PROX_DE_ZERO ) {
                v2 = v;
            }                        
        }
        
        if ( v2 != null ) {
            double[] v_p1 = filtro.getPonto3D( v1 );
            double[] v_p2 = filtro.getPonto3D( v2 );        
            for( ; i < size; i++ ) {
                Vertice3D v3 = vertices.get( i );
                double[] v_p3 = filtro.getPonto3D( v3 );
                if ( !this.verificaSeColineares( v_p1, v_p2, v_p3 ) )
                    return new Vertice3D[] { v1, v2, v3 };
            }
        }
        return new Vertice3D[] { vertices.get( 0 ), vertices.get( 1 ), vertices.get( 2 ) };  
    }
        
    public double[] alteraComprimento( double[] v, double comprimento ) {
        double c = this.comprimento( v );
        return this.mult( v, comprimento / c );
    }
    
    public double angulo( double[] v1, double[] v2 ) {
        double v1_r = this.comprimento( v1 );
        double v2_r = this.comprimento( v2 );
                
        double v1_x_v2 = (v1[0]*v2[0]) + (v1[1]*v2[1]) + (v1[2]*v2[2]);
        double cos = v1_x_v2 / ( v1_r * v2_r );        
        if ( Math.abs( 1.0d - cos ) <= PROX_DE_ZERO ) {
            cos = 1.0d;
        }if ( Math.abs( -1.0d - cos ) <= PROX_DE_ZERO ) {
            cos = -1.0d;
        } else if ( Math.abs( cos ) <= PROX_DE_ZERO ) {
            cos = 0.0d;
        }        
        return Math.acos( cos );
    }
        
    public double comprimento( double[] v ) {
        return Math.sqrt( (v[0]*v[0]) + (v[1]*v[1]) + (v[2]*v[2]) );
    }
    
    public double arredonda( double valor ) {
        if ( Math.abs( 1.0d - valor ) <= PROX_DE_ZERO ) {
            return 1.0d;
        }if ( Math.abs( -1.0d - valor ) <= PROX_DE_ZERO ) {
            return -1.0d;
        } else if ( Math.abs( valor ) <= PROX_DE_ZERO ) {
            return 0.0d;
        } else {
            return valor;
        }
    }
    
    public double calculaRetaFatorT( double[] u, double[] v ) {
        double u_c = this.comprimento( u );
        if ( u_c == 0 )
            return 1.0d;
        
        double t;
        if ( u[0] != 0 ) {
            t = v[0] / u[0];
        } else if ( u[1] != 0 ) {
            t = v[1] / u[1];
        } else {
            t = v[2] / u[2];
        }

        return t;
    }
        
    public double[] soma(double[] p1, double[] p2) {
        return new double[] {
            p1[0]+p2[0],
            p1[1]+p2[1],
            p1[2]+p2[2]
        };
    }
    
    public double[] soma(double[] p, double n) {
        return new double[] {
            p[0]+n,
            p[1]+n,
            p[2]+n
        };
    }
    
    public double[] sub(double[] p1, double[] p2) {
        return new double[] {
            p1[0]-p2[0],
            p1[1]-p2[1],
            p1[2]-p2[2]
        };
    }
    
    public double[] sub(double[] p, double n) {
        return new double[] {
            p[0]-n,
            p[1]-n,
            p[2]-n
        };
    }
    
    public double[] mult(double[] p1, double[] p2) {
        return new double[] {
            p1[0]*p2[0],
            p1[1]*p2[1],
            p1[2]*p2[2]
        };
    }
        
    public double[] mult(double[] p, double escalar) {
        return  new double[] { 
            p[0]*escalar,
            p[1]*escalar,
            p[2]*escalar
        };
    }
    
    public double[] div( double[] p, double escalar ) {
        double f = 1.0d / escalar;
        return this.mult( p, f );
    }
    
    public double[] div( double[] p1, double[] p2 ) {
        return  new double[] { 
            p1[0]*p2[0],
            p1[1]*p2[1],
            p1[2]*p2[2]
        };
    }
    
    public double produtoEscalar( double[] u, double[] v ) {
        return u[0]*v[0] + u[1]*v[1] + u[2]*v[2];
    }
    
    public double[] produtoVetorial( double[] u, double[] v ) {
        return new double[] {
            u[1]*v[2] - u[2]*v[1],
            u[2]*v[0] - u[0]*v[2],
            u[0]*v[1] - u[1]*v[0]
        };
    }
    
    public double produtoMisto( double[] u, double[] v, double[] w ) {
        return  w[0] * ( u[1]*v[2] - u[2]*v[1] ) + w[1] * ( u[2]*v[0] - u[0]*v[2] ) + w[2] * ( u[0]*v[1] - u[1]*v[0] );        
    }
    
    public boolean verificaSeIguais( double[][] plano1, double[][] plano2 ) {
        boolean iguais = true;        
        for( int i = 0; iguais && i < plano1.length; i++ )
            if ( !this.verificaSeIguais( plano1[i], plano2[i] ) )
                iguais = false;                
        return iguais;
    }

    public boolean verificaSeIguais( double[] p1, double[] p2 ) {
        return ( 
            Math.abs( p1[0] - p2[0] ) < PROX_DE_ZERO &&
            Math.abs( p1[1] - p2[1] ) < PROX_DE_ZERO && 
            Math.abs( p1[2] - p2[2] ) < PROX_DE_ZERO 
        );
    }    

}
