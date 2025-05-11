package umu.tds.model;

import java.time.LocalDateTime;

//Falta emoticono, según el documento de libreria de chat en swing, es de tipo entero
/**
 * Clase que modela los mensajes de AppChat.
 */
public class Mensaje {

	/* Atributos */
	private String texto;
	private Usuario emisor;
	private Contacto receptor;
	private LocalDateTime fechaHora;
	private int emoticono;
	private int id;

	/* Constructores */
	/**
	 * Crea un usuario a partir de los siguientes parámetros:
	 *
	 * @param texto     texto del mensaje (información enviada).
	 * @param emisor    usuario que envía el mensaje.
	 * @param repector  usuario que recibe el mensaje.
	 * @param fechaHora fecha y hora del envío del mensaje.
	 */
	public Mensaje(String texto, Usuario emisor, Contacto receptor, LocalDateTime fechaHora, int emoticono) {
		this.texto = texto;
		this.emisor = emisor;
		this.receptor = receptor;
		this.fechaHora = fechaHora;
		this.emoticono = emoticono;
	}

	/* Consulta */
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

	public Contacto getReceptor() {
		return receptor;
	}

	public void setReceptor(Contacto receptor) {
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

	public int getId() {
		return id;
	}

	public void setId(int codigo) {
		this.id = codigo;
	}

	@Override
	public String toString() {
		return "Mensaje [texto=" + texto + ", emisor=" + emisor + ", receptor=" + receptor + ", fechaHora=" + fechaHora
				+ ", emoticono=" + emoticono + ", id=" + id + "]";
	}

}
