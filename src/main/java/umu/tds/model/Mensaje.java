package umu.tds.model;

import java.time.LocalDateTime;

/**
 * Clase que modela los mensajes de AppChat.
 */
public class Mensaje {
	
	// Atributos
	private String texto;
	private Usuario emisor;
	private Usuario receptor;
	private LocalDateTime fechaHora;
	
	// Constructores
	/**
	 * Crea un usuario a partir de los siguientes parámetros:
	 * @param texto texto del mensaje (información enviada).
	 * @param emisor usuario que envía el mensaje.
	 * @param repector usuario que recibe el mensaje.
	 * @param fechaHora fecha y hora del envío del mensaje.
	 */
	public Mensaje(String texto, String usuario, Usuario emisor, Usuario receptor, LocalDateTime fechaHora) {
		this.texto = texto;
		this.emisor = emisor;
		this.receptor = receptor;
		this.fechaHora = fechaHora;
	}
	
	// Métodos de consulta
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Usuario getEmisor() {
		return emisor;
	}

	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}

	public Usuario getReceptor() {
		return receptor;
	}

	public void setReceptor(Usuario receptor) {
		this.receptor = receptor;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

}
