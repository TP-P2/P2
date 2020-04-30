package vista;
///
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
   *  Indica que día de la semana es el día uno para un mes
   * 
   */    
  private int diaSemanaUnoMes(GregorianCalendar fecha) {
    fecha.set(Calendar.DAY_OF_MONTH, 1);  
    int diaSemanaUno = fecha.get(Calendar.DAY_OF_WEEK);
    
    // 1->domingo, 2->lunes, etc., ajustamos a 0-> lunes, 1-> martes, etc.
    if (diaSemanaUno > 1) {
      return diaSemanaUno - 2;
    } else {
      return 6;
    }      
  }
  
  /**
   *  Inicia vista del mes
   * 
   */   
  private void iniciarMesVista() {
    for (int semana = 0; semana < vista.NUM_SEMANAS; semana++) {
      for (int diaSemana = 0; diaSemana < vista.NUM_DIAS_SEMANA; diaSemana++) {  
         diasVista[semana][diaSemana].iniciar();
      }
    }  
  }
  
  /**
   *  Indica si la fecha corresponde al día de hoy
   * 
   */  
  private boolean esHoy(GregorianCalendar fecha) {
    GregorianCalendar hoy = new GregorianCalendar();

    return (fecha.get(Calendar.DAY_OF_MONTH) == hoy.get(Calendar.DAY_OF_MONTH)) &&            
           (fecha.get(Calendar.MONTH) == hoy.get(Calendar.MONTH)) &&
           (fecha.get(Calendar.YEAR) == hoy.get(Calendar.YEAR));
  }
  
  /**
   *  Pone días de mes vista
   * 
   */       
  void ponerDias(GregorianCalendar fecha) {     
    int dia = 1;
    int mes = fecha.get(Calendar.MONTH);
    int año = fecha.get(Calendar.YEAR);
    int diaSemanaUnoMes = diaSemanaUnoMes(fecha);
    
    iniciarMesVista();
        
    for (int semana = 0; semana < vista.NUM_SEMANAS; semana++) {
      for (int diaSemana = 0; diaSemana < vista.NUM_DIAS_SEMANA; diaSemana++) { 
        
        // salta huecos primera semana
        if ((diaSemana < diaSemanaUnoMes) && (semana == 0)) {
          continue;
        }
        
        DiaVista diaVista = diasVista[semana][diaSemana];
        GregorianCalendar fechaDiaActual = new GregorianCalendar(año, mes, dia);
        diaVista.ponerFecha(fechaDiaActual);
        
        if(esHoy(fechaDiaActual)) {
          diaVista.ponerTexto(Integer.toString(dia), DiaVista.Formato.DESTACADO);          
        } else {
          diaVista.ponerTexto(Integer.toString(dia));            
        }
        
        try {
          if (recordatorios.hayRecordatorio(fechaDiaActual)) { 
            diaVista.ponerRecordatorio();            
          }
        } catch (Exception e) {
          if (Agenda.esModoDebug()) {
            DebugVista.devolverInstancia()
              .mostrar(vista.ERROR_OBTENER_RECORDATORIO, e);
          }            
        }

        dia++;
        
        if (dia > fecha.getActualMaximum(Calendar.DAY_OF_MONTH)) {
          return;
        }
      }
    }
  }  
}