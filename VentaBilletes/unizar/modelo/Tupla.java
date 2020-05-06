/**
 * Tupla.java
 *   
 */
package modelo;

/**
 *  Tupla genérica de dos objetos
 * 
 */
public class Tupla<A, B, C, D> {
  public final A a;
  public final B b;
  public final C c;
  public final D d;
  /**
   *  Construye una tupla
   *  
   */   
  public Tupla(A a, B b, C c, D d) { 
    this.a = a; 
    this.b = b; 
    this.c = c;
    this.d = d;
  }
}  
