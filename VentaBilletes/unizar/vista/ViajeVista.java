package vista;

import control.Agenda;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;

import java.util.Calendar;
import java.util.GregorianCalendar;
import modelo.Recordatorios;

/**
 *  Vista del viaje a partir de un JPanel
 * 
 */
class ViajeVista extends JPanel {
	// AJUSTAR ALTURA y ANCHURA
  private static final int ALTURA_FILA = 100;
  private static final int ANCHURA_COLUMNA = 10;
  private AsientoVista[][] asientosVista;
  private OficinaVista vista;
  // ??? 
  private Viajes viajes;
  // ¿¿¿

  public static final boolean RECIBE_EVENTOS_RATON = true;
  public static final boolean NO_RECIBE_EVENTOS_RATON = false;

  /**
   *  Construye la vista del viaje
   * 
   */
  ViajeVista(OficinaVista vista, Viajes viajes, boolean recibeEventosRaton) {   
    this.vista = vista;
    this.viajes = viajes;
    
    crearAsientos(recibeEventosRaton);
    
    //FALTA cambiar NUM_SEMANAS por NUM_FILAS
    //cambiar NUM_DIAS_SEMANA por NUM_ASIENTOS_POR_FILA
    this.setPreferredSize(new Dimension(vista.NUM_SEMANAS * ALTURA_FILA, 
                                        vista.NUM_DIAS_SEMANA * ANCHURA_COLUMNA));
  }
  
  /**
   *  Crea asientos
   * 
   */  
  private void crearAsientos(boolean recibeEventosRaton) {
	// FALTA NUM_ASIENTOS_POR_FILA y NUM_FILAS
	int filas = vista.NUM_FILAS;
	int asientos = vista.NUM_ASIENTOS_POR_FILA;

    setLayout(new GridLayout(filas, asientos));
    asientosVista = new AsientoVista[filas][asientos];
    
    for(int asiento = 0; asiento < filas; asiento++) {
      for(int fila = 0; fila < asientos; fila++) {
        asientosVista[asiento][fila] = new AsientoVista(vista, recibeEventosRaton);         
        add(asientosVista[asiento][fila]);        
      }
    }      
  }  
  
  /**
   *  Busca un AsientoVista
   * 
   */  
  private AsientoVista buscarAsientoVista(Asiento asiento) {
    for(int asiento = 0; asiento < vista.NUM_FILAS; asiento++) {
      for(int fila = 0; fila < vista.NUM_ASIENTOS_POR_FILA; fila++) {
        Asiento asientoConcreto = asientosVista[asiento][fila].obtenerAsiento();
        // ¿¿ AÑADIR en AsientoVista un método que haga return de un asiento ?? obtenerAsiento()
        if ((asientoConcreto != null) && asientoConcreto.equals(asiento)) {
          return asientosVista[asiento][fila];
        }
      }  
    }
    return null;
  }
  
  /**
   *  Pone ocupado un asiento
   * 
   */  
  void ponerOcupado(Asiento asiento) {
    AsientoVista asientoVista = buscarAsientoVista(asiento);
    if (asientoVista != null) {
       asientoVista.ponerOcupado();        
    }
  }
  
  /**
   *  Elimina ocupado de un asiento
   * 
   */  
  void eliminarOcupado(Asiento asiento) {
    AsientoVista asientoVista = buscarAsientoVista(asiento);
    if (asientoVista != null) {
       asientoVista.eliminarOcupado();        
    }
  } 
  
  /**
   *  Inicia vista del viaje
   * 
   */   
  private void iniciarViajeVista() {
    for (int fila = 0; fila < vista.NUM_FILAS; fila++) {
      for (int asientoFila = 0; asientoFila < vista.NUM_ASIENTOS_POR_FILA; asientoFila++) {  
         asientosVista[fila][asientoFila].iniciar();
      }
    }  
  }
  
  /**
   *  Pone días de mes vista
   * 
   */       
  void ponerAsientos(Asiento asiento) {     
    int asiento = 1;
    int viaje = ; //GETTER del txt con los viajes
    
    iniciarViajeVista();
        
    for (int fila = 0; fila < vista.NUM_FILAS; fila++) {
      for (int asientoFila = 0; asientoFila < vista.NUM_ASIENTOS_POR_FILA; asientoFila++) { 
        
        // salta huecos primera fila
    	  // necesario tener en cuenta los huecos vacíos del pasillo y salida del autobús
        /*if () {
          continue;
        */}
        
        AsientoVista asientoVista = asientosVista[fila][asientoFila];

        if (/* asiento en el que se ha hecho clic */) {
        	asientoVista.ponerTexto(Integer.toString(asiento), AsientoVista.Formato.DESTACADO);
        } else {
        	asientoVista.ponerTexto(Integer.toString(asiento));
        }	
        // he cambiado ponerViajero por ponerTexto porque aquí creo que indicamos los nºs de asiento solamente
        // tendremos que hacer otro para que cuando le demos clic a uno nos muestre información del viajero sentado
        	
        
        // igual no es necesario el try/catch
        // el método estaOcupado() lo más seguro habrá que crearlo en la clase "Viajeros" que es = Recordatorios de Agenda
        try {
          if (viajero.estaOcupado(asiento)) { 
            asientoVista.ponerOcupado();            
          }
        } catch (Exception e) {
          if (VentaBilletes.esModoDebug()) {
            DebugVista.devolverInstancia()
              .mostrar(vista.ERROR_OBTENER_OCUPACION, e);
          }            
        }

        asiento++;
        
      }
    }
  }  
}