package umu.tds.model;

import java.time.LocalDateTime;
//Falta emoticono, según el documento de libreria de chat en swing, es de tipo entero
/**
 * Clase que modela los mensajes de AppChat.
 */
public class Mensaje {
	
	// Atributos
	private String texto;
	private Usuario emisor;
	private Usuario receptor;
	private LocalDateTime fechaHora;
	private int emoticono;
	
	// Constructores
	/**
	 * Crea un usuario a partir de los siguientes parámetros:
	 * @param texto texto del mensaje (información enviada).
	 * @param emisor usuario que envía el mensaje.
	 * @param repector usuario que recibe el mensaje.
	 * @param fechaHora fecha y hora del envío del mensaje.
	 */
	//El string usuario aquí para que sirve???
	public Mensaje(String texto, String usuario, Usuario emisor, Usuario receptor, LocalDateTime fechaHora, int emoticono) {
		this.texto = texto;
		this.emisor = emisor;
		this.receptor = receptor;
		this.fechaHora = fechaHora;
		this.emoticono = emoticono;
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

	public int getEmoticono() {
		return emoticono;
	}

	public void setEmoticono(int emoticono) {
		this.emoticono = emoticono;
	}
	

}
