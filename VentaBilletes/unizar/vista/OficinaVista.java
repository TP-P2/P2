/**
 * OficinaVista.java
 * 
 * Cristian Bogdan Bucutea & Borja Rando Jarque
 * 
 * 05/2020
 * 
 */

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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import modelo.Viaje;
import modelo.Viajero;
import modelo.Viajes;
import modelo.Asiento;
import modelo.Tupla;
import modelo.Tupla2;

/**
 * Vista Swing de la oficina
 * 
 */
public class OficinaVista implements ActionListener, PropertyChangeListener {
	private OyenteVista oyenteVista;
	private Viajes viajes;

	private static OficinaVista instancia = null; // es singleton
	private JFrame ventana;
	private ViajeVista viajeVistaSeleccionado;

	private JTextArea viajesVista;
	private JButton botonBuscar;
	private JButton botonNuevo;
	private JButton botonEliminar;
	private JButton botonVerAsientos;

	private JMenuItem botonGenerarHoja;

	private JComboBox desplegableDia;
	private JComboBox<Month> desplegableMes;
	private JComboBox desplegableAnno;

	private JComboBox desplegableViaje;

	private AsientoVista asientoVistaSeleccionado;
	private Asiento asientoVista;

	public static final int NUM_FILAS = 14;
	public static final int NUM_COLUMNAS = 5;

	private static final int FILAS_VIAJES_VISTA = 5;
	private static final int COLUMNAS_VIAJES_VISTA = 20;

	/** Identificadores de textos dependientes del idioma */
	private static final String ETIQUETA_VIAJERO = "Viajero";

	private static final String NUEVO_VIAJERO = "Nuevo";
	private static final String ELIMINAR_VIAJERO = "Eliminar";
	public static final String VIAJERO = "Viajero";
	private static final String ACERCA_DE = "Acerca de...";
	public static final String ETIQUETA_INTRODUCE_NOMBRE = "Introduce nombre:";
	public static final String ETIQUETA_INTRODUCE_DNI = "Introduce DNI:";
	public static final String IDIOMA = "Seleccionar idioma";
	private static final String CREAR_HOJA_VIAJE = "Generar hoja de viaje";
	private static final String VER_ASIENTOS = "Ver asientos";
	private static final String PONER_DIAS_MES = "Poner días mes";
	private static final String LISTAR_VIAJES_FECHA = "Buscar";

	private static final Integer[] ANNOS = { 2020, 2021 };

	/** Constantes para redimensionamiento */
	public static final int MARGEN_HORIZONTAL = 50;
	public static final int MARGEN_VERTICAL = 20;
	public static final String FICHERO_NO_ENCONTRADO = null; // Gestionar con clase excepciones??
	public static final String VIAJES_NO_LEIDOS = null;

	/**
	 * Construye la vista de la oficina
	 * 
	 */
	private OficinaVista(OyenteVista oyenteVista, Viajes viajes) {
		this.oyenteVista = oyenteVista;
		this.viajes = viajes;
		crearVentana();
	}

	/**
	 * Crea la ventana de la vista
	 * 
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
		panelNorte.setLayout(new BorderLayout(2, 1));

		// creamos elementos
		crearBarraMenus(panelNorte);
		crearBarraFecha(panelNorte);
		crearBarraViaje(panelNorte);
		ventana.getContentPane().add(panelNorte, BorderLayout.NORTH);

		JPanel panelViaje = new JPanel();
		panelViaje.setLayout(new FlowLayout());
		viajeVistaSeleccionado = new ViajeVista(this, viajes, ViajeVista.RECIBE_EVENTOS_RATON);
		panelViaje.add(viajeVistaSeleccionado);
		ventana.getContentPane().add(panelViaje, BorderLayout.CENTER);

		ventana.setResizable(false);

		ventana.pack(); // ajusta ventana y sus componentes
		ventana.setVisible(true);
		ventana.setLocationRelativeTo(null); // centra en la pantalla
	}

	/**
	 * Devuelve la instancia de la vista de la oficina
	 * 
	 */
	public static synchronized OficinaVista instancia(OyenteVista oyenteIU, Viajes viajes) {
		if (instancia == null) {
			instancia = new OficinaVista(oyenteIU, viajes);
		}
		return instancia;
	}

	/**
	 * Crea botón barra de herramientas
	 * 
	 */
	private JButton crearBotonBarraHerramientas(String etiqueta) {
		JButton boton = new JButton(etiqueta);
		boton.addActionListener(this);
		boton.setActionCommand(etiqueta);

		return boton;
	}

	/**
	 * Crea item menú de opciones
	 * 
	 */
	private JMenuItem crearItemMenuOpciones(String etiqueta) {
		JMenuItem boton = new JMenuItem(etiqueta);
		boton.addActionListener(this);
		boton.setActionCommand(etiqueta);

		return boton;
	}

	/**
	 * Crea barra de herramientas
	 * 
	 */
	private void crearBarraMenus(JPanel panelNorte) {
		JMenuBar barraMenus = new JMenuBar();

		JMenu menuOpciones = new JMenu("Opciones");
		barraMenus.add(menuOpciones);

		botonGenerarHoja = crearItemMenuOpciones(CREAR_HOJA_VIAJE);
		activarBotonGenerarHoja(false);
		menuOpciones.add(botonGenerarHoja);

		JMenuItem opcionIdioma = crearItemMenuOpciones(IDIOMA);
		menuOpciones.add(opcionIdioma);
		
		JMenuItem botonAcercaDe = crearItemMenuOpciones(ACERCA_DE);
		menuOpciones.add(botonAcercaDe);

		panelNorte.add(barraMenus, BorderLayout.NORTH);
	}

	/**
	 * Crea barra de selección de fecha
	 * 
	 */
	private void crearBarraFecha(JPanel panelNorte) {
		JToolBar barraFecha = new JToolBar();
		barraFecha.setFloatable(false);

		JLabel etiquetaFecha = new JLabel("Fecha: ");
		barraFecha.add(etiquetaFecha);

		desplegableMes = new JComboBox<>(Month.values());
		barraFecha.add(desplegableMes);
		desplegableMes.addActionListener(this);
		desplegableMes.setSelectedItem(null);
		desplegableMes.setActionCommand(PONER_DIAS_MES);
		barraFecha.add(new JToolBar.Separator());

		desplegableDia = new JComboBox();
		desplegableMes.setSelectedItem(0);
		barraFecha.add(desplegableDia);
		barraFecha.add(new JToolBar.Separator());

		desplegableAnno = new JComboBox(ANNOS);
		barraFecha.add(desplegableAnno);
		desplegableAnno.addActionListener(this);
		desplegableAnno.setActionCommand(PONER_DIAS_MES);
		barraFecha.add(new JToolBar.Separator());

		botonBuscar = crearBotonBarraHerramientas(LISTAR_VIAJES_FECHA);
		botonBuscar.setEnabled(false);
		barraFecha.add(botonBuscar);

		panelNorte.add(barraFecha, BorderLayout.CENTER);
	}

	/**
	 * Crea barra de selección de viaje
	 * 
	 */
	private void crearBarraViaje(JPanel panelNorte) {
		JToolBar barra = new JToolBar();
		barra.setFloatable(false);

		barra.add(new JToolBar.Separator());

		JLabel etiquetaViaje = new JLabel("Viaje: ");
		barra.add(etiquetaViaje);

		desplegableViaje = new JComboBox();
		barra.add(desplegableViaje);
		barra.add(new JToolBar.Separator());
		desplegableViaje.setSelectedItem(null);
		desplegableViaje.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXX");

		botonVerAsientos = crearBotonBarraHerramientas(VER_ASIENTOS);
		barra.add(botonVerAsientos);
		botonVerAsientos.setEnabled(false);
		barra.add(new JToolBar.Separator());

		botonNuevo = crearBotonBarraHerramientas(NUEVO_VIAJERO);
		barra.add(botonNuevo);
		botonNuevo.setEnabled(false);
		barra.add(new JToolBar.Separator());

		botonEliminar = crearBotonBarraHerramientas(ELIMINAR_VIAJERO);
		barra.add(botonEliminar);
		botonEliminar.setEnabled(false);
		barra.add(new JToolBar.Separator());

		panelNorte.add(barra, BorderLayout.SOUTH);
	}

	/**
	 * Sobreescribe actionPerformed de ActionListener
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case NUEVO_VIAJERO:
			nuevoViajero();
			break;

		case ELIMINAR_VIAJERO:
			eliminarViajero();
			break;

		case PONER_DIAS_MES:
			ponerDiasMes();
			break;

		case LISTAR_VIAJES_FECHA:
			listarViajesFecha();
			break;

		case VER_ASIENTOS:
			ponerViajeVista();
			break;

		case ACERCA_DE:
			JOptionPane.showMessageDialog(ventana, Oficina.VERSION, ACERCA_DE, JOptionPane.INFORMATION_MESSAGE);
			break;

		case IDIOMA:// IMPLEMENTAR
			break;

		case CREAR_HOJA_VIAJE:
			try {
				generarHojaViaje();
			} catch (IOException e1) {
				// FALTA GESTIONAR CON VENTANA ERROR
				e1.printStackTrace();
			}
			break;

		}
	}

	/**
	 * Nuevo viajero
	 * 
	 */
	private void nuevoViajero() {
		String nombre = JOptionPane.showInputDialog(ventana, ETIQUETA_INTRODUCE_NOMBRE,
				VIAJERO + " " + asientoVistaSeleccionado.toString(), JOptionPane.QUESTION_MESSAGE);
		String dni = JOptionPane.showInputDialog(ventana, ETIQUETA_INTRODUCE_DNI,
				VIAJERO + " " + asientoVistaSeleccionado.toString(), JOptionPane.QUESTION_MESSAGE);

		if (nombre != null && !nombre.equals("") && dni != null && !dni.equals("")) {
			oyenteVista.eventoProducido(OyenteVista.Evento.NUEVO_VIAJERO, new Tupla<Viaje, Asiento, String, String>(
					viajeVistaSeleccionado.obtenerViaje(), asientoVistaSeleccionado.obtenerAsiento(), nombre, dni));

		}
	}

	/**
	 * Elimina viajero
	 * 
	 */
	private void eliminarViajero() {
		oyenteVista.eventoProducido(OyenteVista.Evento.ELIMINAR_VIAJERO, new Tupla2<Viaje, Asiento>(
				viajeVistaSeleccionado.obtenerViaje(), asientoVistaSeleccionado.obtenerAsiento()));
	}

	/**
	 * Modifica dinámicamente los días del mes en el desplegable en función del mes
	 * y año seleccionados
	 * 
	 */
	private void ponerDiasMes() {
		int numDiasMes = ((Month) desplegableMes.getSelectedItem()).length(esBisiesto());

		Integer[] diasMes = new Integer[numDiasMes];
		for (int i = 0; i < numDiasMes; i++) {
			diasMes[i] = (Integer) (i + 1);
		}
		DefaultComboBoxModel modelo = new DefaultComboBoxModel(diasMes);
		desplegableDia.setModel(modelo);
		activarBotonBuscar(true);
	}

	/**
	 * Determina si el año seleccionado es bisiesto o no
	 * 
	 */
	private boolean esBisiesto() {
		LocalDate fechaSeleccionada = LocalDate.of((Integer) desplegableAnno.getSelectedItem(), 1, 1);
		return fechaSeleccionada.isLeapYear();
	}

	/**
	 * Muestra en el desplegable los viajes disponibles para una fecha señalada
	 */
	private void listarViajesFecha() {
		LocalDate fecha = LocalDate.of((int) desplegableAnno.getSelectedItem(),
				(Month) desplegableMes.getSelectedItem(), (int) desplegableDia.getSelectedItem());

		DefaultComboBoxModel modelo = new DefaultComboBoxModel(viajes.buscarViajesPorFecha(fecha).values().toArray());
		if (viajes.buscarViajesPorFecha(fecha).size() == 0) {
			activarBotonVerAsientos(false);
			// Mostrar ventana "NO HAY VIAJES PARA LA FECHA SELECCIONADA"
		} else {
			activarBotonVerAsientos(true);
		}
		desplegableViaje.setModel(modelo);
	}

	/**
	 * Pone vista del viaje seleccionado
	 * 
	 */
	public void ponerViajeVista() {
		Viaje esteViaje = viajes.getViajePorId(((Viaje) desplegableViaje.getSelectedItem()).getId());
		viajeVistaSeleccionado.ponerAsientos(esteViaje);
		activarBotonNuevoViajero(false);
		activarBotonEliminarViajero(false);
		activarBotonGenerarHoja(true);
	}

	/**
	 * Genera la hoja del viaje seleccionado
	 * 
	 */
	private void generarHojaViaje() throws IOException {
		viajeVistaSeleccionado.obtenerViaje().generarHoja();
	}

	/**
	 * Pone texto de un viajero
	 * 
	 */
	/*
	 * public void ponerTextoViajero(String texto) { viajesVista.setText(texto); }
	 */

	/**
	 * Selecciona asiento vista
	 * 
	 */
	public void seleccionarAsientoVista(AsientoVista asientoVista) {
		// Quita selección anterior
		if (asientoVistaSeleccionado != null) {
			asientoVistaSeleccionado.deseleccionar();
		}

		asientoVista.seleccionar();
		this.asientoVistaSeleccionado = asientoVista;

		Viajero viajero = viajes.obtenerViajero(asientoVista.obtenerAsiento());

		if (viajero != null) {
			activarBotonEliminarViajero(true);
		} else {
			activarBotonNuevoViajero(true);
			activarBotonEliminarViajero(false);
		}
	}

	/**
	 * Activa botón nuevo viajero
	 * 
	 */
	public void activarBotonNuevoViajero(boolean activar) {
		botonNuevo.setEnabled(activar);
	}

	/**
	 * Activa botón eliminar viajero
	 * 
	 */
	public void activarBotonEliminarViajero(boolean activar) {
		botonEliminar.setEnabled(activar);
	}

	/**
	 * Activa botón buscar viajes
	 * 
	 */
	public void activarBotonBuscar(boolean activar) {
		botonBuscar.setEnabled(activar);
	}

	/**
	 * Activa botón ver asientos
	 * 
	 */
	public void activarBotonVerAsientos(boolean activar) {
		botonVerAsientos.setEnabled(activar);
	}

	/**
	 * Activa botón generar hoja viaje
	 * 
	 */
	public void activarBotonGenerarHoja(boolean activar) {
		botonGenerarHoja.setEnabled(activar);
	}

	/**
	 * Sobreescribe propertyChange para recibir cambios en modelo
	 * 
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Asiento asiento = (Asiento) evt.getNewValue();

		if (evt.getPropertyName().equals(Viajes.NUEVO_VIAJERO)) {
			viajeVistaSeleccionado.ponerOcupado(asiento);
			asientoVistaSeleccionado.setToolTipText(asientoVistaSeleccionado.obtenerAsiento().getViajero().toString());
		} else if (evt.getPropertyName().equals(Viajes.ELIMINAR_VIAJERO)) {
			viajeVistaSeleccionado.eliminarOcupado(asiento);
			asientoVistaSeleccionado.setToolTipText(null);
			activarBotonEliminarViajero(false);
		}
	}
}