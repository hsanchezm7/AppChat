package umu.tds.model;

import java.util.LinkedList;
import java.util.List;
import java.time.LocalDateTime;

public abstract class Contacto {
	
	// Atributos
	private String nombre;
	private List<Mensaje> mensajes;
	

	public Contacto(String nombre) {
		this.nombre = nombre;
		this.mensajes = new LinkedList<Mensaje>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
	
	public int contarMensajesUltimoMes() {
		LocalDateTime hoy = LocalDateTime.now();
		LocalDateTime haceMes = hoy.minusMonths(1);
		
		int contador = 0;
		for (Mensaje mensaje : mensajes) {
			if (mensaje.getFechaHora().isAfter(haceMes) && mensaje.getFechaHora().isBefore(hoy)) {
				contador++;
			}
		}
		return contador;
	}
	
}
