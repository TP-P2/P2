package vista;

import control.Oficina;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import control.OyenteVista;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import modelo.Viaje;
import modelo.Viajero;
import modelo.Viajes;
import modelo.Asiento;
import modelo.Tupla;

/**
 * Vista Swing de la oficina
 */
public class OficinaVista implements ActionListener, PropertyChangeListener { 
  private OyenteVista oyenteVista;
  private Viajes viajes;
  
  private static OficinaVista instancia = null;  // es singleton
  private JFrame ventana;
  private ViajeVista viajeVistaSeleccionado;
  private Viaje viajeVista;
  
  private JTextArea viajesVista;
  private JTextField viaje;
  private JButton botonNuevo;
  private JButton botonEliminar;
  
  private AsientoVista asientoVistaSeleccionado;
  private Asiento asientoVista;
  
  public static final int NUM_FILAS = 5;
  public static final int NUM_COLUMNAS = 14; 
  
  private static final int FILAS_VIAJES_VISTA = 5;
  private static final int COLUMNAS_VIAJES_VISTA = 20;
  private static final int COLUMNAS_VIAJE = 10;
 
  /** Identificadores de textos dependientes del idioma */
  private static final String ETIQUETA_VIAJERO = "Viajero";
  
  private static final String NUEVO_VIAJERO = "Nuevo";
  private static final String ELIMINAR_VIAJERO = "Eliminar";
  public static final String VIAJERO = "Viajero";
  private static final String VIAJE_ANTERIOR = " < ";
  private static final String VIAJE_SIGUIENTE = " > ";
  private static final String ACERCA_DE = "Acerca de...";
  public static final String ETIQUETA_INTRODUCE_TEXTO = "Introduce texto";
  
  // viajes ya existe por eso le he puesto los_viajes
  private static final String[] los_viajes = {"VT001", "TV001"};
  
  /** Constantes para redimensionamiento */
  public static final int MARGEN_HORIZONTAL = 50;
  public static final int MARGEN_VERTICAL = 20;
	public static final String FICHERO_NO_ENCONTRADO = null;
	public static final String VIAJES_NO_LEIDOS = null;
  
  /**
   * Construye la vista de la oficina 
   */
  private OficinaVista(OyenteVista oyenteVista, Viajes viajes) {
    this.oyenteVista = oyenteVista;
    this.viajes = viajes;      
    crearVentana();
    
    // inicia vista al viaje actual
    // ------o igual con viajeVista------
    viajeVista = viajes.getViajePorId(los_viajes[0]);
    ponerViajeVista(viajeVista); 
  }  
  
  /**
   * Crea la ventana de la vista
   */  
  private void crearVentana() { 
    ventana = new JFrame(Oficina.VERSION);
    
    ventana.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        oyenteVista.eventoProducido(OyenteVista.Evento.SALIR, null);
      }
    });
    
    ventana.getContentPane().setLayout(new BorderLayout());
    
    JPanel panelNorte = new JPanel();
    panelNorte.setLayout(new GridLayout(2, 1));
    
    // creamos elementos
    crearBarraHerramientas(panelNorte);
    ventana.getContentPane().add(panelNorte, BorderLayout.NORTH);
    
    JPanel panelViaje = new JPanel();
    panelViaje.setLayout(new FlowLayout());   
    viajeVistaSeleccionado = new ViajeVista(this, viajes, ViajeVista.RECIBE_EVENTOS_RATON);
    panelViaje.add(viajeVistaSeleccionado);
    ventana.getContentPane().add(panelViaje, BorderLayout.CENTER);
    
    JPanel panelViajes = new JPanel();
    panelViajes.setLayout(new BorderLayout());
    crearViajeroVista(panelViajes);
    ventana.getContentPane().add(panelViajes, BorderLayout.EAST);
       
    ventana.setResizable(false);    
    
    ventana.pack();  // ajusta ventana y sus componentes
    ventana.setVisible(true);
    ventana.setLocationRelativeTo(null);  // centra en la pantalla
  }  
    
  /**
   * Devuelve la instancia de la vista de la oficina
   */        
  public static synchronized OficinaVista 
       instancia(OyenteVista oyenteIU, Viajes viajes) {
    if (instancia == null) {
      instancia = new OficinaVista(oyenteIU, viajes);    
    }
    return instancia;
  } 
  
  /**
   * Crea botón barra de herramientas
   */ 
  private JButton crearBotonBarraHerramientas(String etiqueta) {
    JButton boton = new JButton(etiqueta);
    boton.addActionListener(this);
    boton.setActionCommand(etiqueta);
    
    return boton;
  }       

  /**
   * Crea barra de herramientas
   */   
  private void crearBarraHerramientas(JPanel panelNorte) {
    JToolBar barra = new JToolBar();
    barra.setFloatable(false);

    botonNuevo = crearBotonBarraHerramientas(NUEVO_VIAJERO);
    barra.add(botonNuevo);
    botonNuevo.setEnabled(false);

    botonEliminar = crearBotonBarraHerramientas(ELIMINAR_VIAJERO);
    barra.add(botonEliminar);
    botonEliminar.setEnabled(false);    

    barra.add(new JToolBar.Separator());
    
    JButton botonViajeAnterior = crearBotonBarraHerramientas(VIAJE_ANTERIOR);
    barra.add(botonViajeAnterior);

    barra.add(new JToolBar.Separator());
    viaje = new JTextField(COLUMNAS_VIAJE);
    viaje.setMaximumSize(viaje.getPreferredSize());
    viaje.setEditable(false);
    viaje.setHorizontalAlignment(JTextField.CENTER);
    barra.add(viaje);
    barra.add(new JToolBar.Separator());
    
    JButton botonViajeSiguiente = crearBotonBarraHerramientas(VIAJE_SIGUIENTE);
    barra.add(botonViajeSiguiente);

    barra.add(new JToolBar.Separator());
    
    JButton botonAcercaDe = crearBotonBarraHerramientas(ACERCA_DE);
    barra.add(botonAcercaDe);
    
    panelNorte.add(barra);
  }  
           
  /**
   * Crea vista del texto del viajero 
   */   
  private void crearViajeroVista(JPanel panel) {
    viajesVista = new JTextArea(FILAS_VIAJES_VISTA, 
                                       COLUMNAS_VIAJES_VISTA); 
    viajesVista.setEditable(false);
    viajesVista.setLineWrap(true);
    panel.add(new JLabel(ETIQUETA_VIAJERO), BorderLayout.NORTH);
    panel.add(viajesVista, BorderLayout.CENTER);
  }
  
  /**
   * Nuevo viajero
   * 
   */
  private void nuevoViajero() {
    String texto = JOptionPane.showInputDialog(
            ventana,
            ETIQUETA_INTRODUCE_TEXTO,
            VIAJERO + " " + 
            asientoVistaSeleccionado.toString(),
            JOptionPane.QUESTION_MESSAGE);
    
    if (texto != null && ! texto.equals("")) {            
      oyenteVista.eventoProducido(
              OyenteVista.Evento.NUEVO_VIAJERO, 
              new Tupla<Viaje, Asiento, String, String>(
                 viajeVistaSeleccionado.obtenerViaje(), asientoVistaSeleccionado.obtenerAsiento(), texto, "")); 
    }
  }
  
  /**
   * Sobreescribe actionPerformed de ActionListener
   */
  @Override
  public void actionPerformed(ActionEvent e)  {
    switch(e.getActionCommand()) {
      case NUEVO_VIAJERO: 
        nuevoViajero();
        break;

      case ELIMINAR_VIAJERO:
        oyenteVista.eventoProducido(OyenteVista.Evento.ELIMINAR_VIAJERO,
                                    asientoVistaSeleccionado.obtenerAsiento());
        break;

      case VIAJE_ANTERIOR:
    	  //-----cambiar asientoVista por viajeVista??----
       // asientoVista.add(los_viajes, -1);
        ponerViajeVista(viajeVista);                          
        break;           

      case VIAJE_SIGUIENTE:
    	  //-----cambiar asientoVista por viajeVista??---- maybe
        //asientoVista.add(los_viajes, 1);
        ponerViajeVista(viajeVista);
        break;           
                      
      case ACERCA_DE:
        JOptionPane.showMessageDialog(ventana, Oficina.VERSION, 
           ACERCA_DE, JOptionPane.INFORMATION_MESSAGE);   
        break;
    //VIAJE_ANTERIOR Y VIAJE_SIGUIENTE --> desplegable y además seleccionar por fecha
    }
  }  
  
  /**
   * Pone texto de un viajero
   */
  public void ponerTextoViajero(String texto) {
    viajesVista.setText(texto);      
  }
     
  /**
   * Inicia viaje vista para un asiento 
   */  
  public void ponerViajeVista(Viaje esteViaje) {
    ponerTextoViajero("");      
    // ------- habrá que ver como nos desplazamos por los distintos viajes--------
    viaje.setText(viajeVistaSeleccionado.toString());
    viajeVistaSeleccionado.ponerAsientos(esteViaje);
    activarBotonNuevoViajero(false);  
    activarBotonEliminarViajero(false);      
  }  
    
  /**
   * Selecciona asiento vista
   */    
  public void seleccionarAsientoVista(AsientoVista asientoVista) {
    // Quita selección anterior  
    if (asientoVistaSeleccionado != null) {  
      asientoVistaSeleccionado.deseleccionar();
      ponerTextoViajero("");
    }     
    
    asientoVista.seleccionar();            
    this.asientoVistaSeleccionado = asientoVista;
    
    Viajero viajero = 
      viajes.obtenerViajero(asientoVista.obtenerAsiento()); // día 87 de la cuarentena, me sigo planteando si cambiar obtenerAsiento por obtenerViaje
                
    if (viajero != null) {
      ponerTextoViajero(viajero.toString());
      activarBotonEliminarViajero(true);
    } else {
      activarBotonNuevoViajero(true);
      activarBotonEliminarViajero(false); 
    }
  }  
  
  /**
   * Activa botón nuevo viajero
   */   
  public void activarBotonNuevoViajero(boolean activar) {
    botonNuevo.setEnabled(activar);
  }

  /**
   * Activa botón eliminar viajero
   */     
  public void activarBotonEliminarViajero(boolean activar) {
    botonEliminar.setEnabled(activar);
  }
      
  /**
   * Sobreescribe propertyChange para recibir cambios en modelo
   */  
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    Asiento asiento = (Asiento)evt.getNewValue();

    if (evt.getPropertyName().equals(Viajes.NUEVO_VIAJERO)) {
      viajeVistaSeleccionado.ponerOcupado(asiento);        
      ponerTextoViajero(
        viajes.obtenerViajero(asiento).toString());    
    }
    else if (evt.getPropertyName().equals(Viajes.ELIMINAR_VIAJERO)) {
    	viajeVistaSeleccionado.eliminarOcupado(asiento);  
      ponerTextoViajero("");  
      activarBotonEliminarViajero(false);      
    }
  }   
}