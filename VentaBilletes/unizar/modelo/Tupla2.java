/**
 * Tupla.java
 * 
 * Cristian Bogdan Bucutea & Borja Rando Jarque
 * 
 * 03/2020
 * 
 */

package modelo;

/**
 * Tupla genérica de dos objetos
 * 
 */
public class Tupla2<A, B> {
  public final A a;
  public final B b;
  
  /**
   *  Construye una tupla
   *  
   */   
  public Tupla2(A a, B b) { 
    this.a = a; 
    this.b = b; 
  }
}  
