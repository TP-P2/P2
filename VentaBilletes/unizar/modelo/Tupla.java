/**
 * Tupla.java
 * 
 * Cristian Bogdan Bucutea & Borja Rando Jarque
 * 
 * 04/2020
 * 
 */

package modelo;

/**
 *  Tupla gen�rica de cuatro objetos
 * 
 */
public class Tupla<A, B, C, D> {
  public final A a;
  public final B b;
  public final C c;
  public final D d;
  
  /**
   *  Construye una tupla de cuatro objetos
   *  
   */   
  public Tupla(A a, B b, C c, D d) { 
    this.a = a; 
    this.b = b; 
    this.c = c;
    this.d = d;
  }
}  
