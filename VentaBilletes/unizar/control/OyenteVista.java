/*
 * Oficina.java
 * 
 * Cristian Bogdan Bucutea & Borja Rando Jarque
 * 
 * 03/2020
 * 
 */
package control;

/**
 * Interfaz de escucha para los eventos de la IU
 */
public interface OyenteVista {
	public enum Evento {
		NUEVO_VIAJERO, ELIMINAR_VIAJERO, SALIR
	}

	/**
	 * Llamado para notificar un evento de la IU
	 */
	public void eventoProducido(Evento evento, Object obj);

}
