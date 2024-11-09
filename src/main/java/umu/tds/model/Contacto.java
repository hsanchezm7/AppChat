package umu.tds.model;

import java.util.LinkedList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Contacto {
	
	/* Atributos */
	private String nombre;
	private List<Mensaje> mensajes;
	
	/* Constructor */
	public Contacto(String nombre) {
		this.nombre = nombre;
		this.mensajes = new LinkedList<Mensaje>();
	}

	/* Consulta */
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	// Necesario? ¿añadir addMensaje también?
	// Parece ser necesaria si añadimos un contacto cuando ya hemos estado hablando con el.
	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
	
	public int contarMensajesDesdeFecha(LocalDate fecha) {
		LocalDate fechaHoy = LocalDate.now();
		int contador = 0;
		
		for (Mensaje mensaje : mensajes) {
			if (mensaje.getFechaHora().isAfter(fechaHoy.atStartOfDay()) && mensaje.getFechaHora().isBefore(fechaHoy.atStartOfDay())) {
				contador++;
			}
		}
		
		return contador;
	}
	
}
