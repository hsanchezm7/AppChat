package umu.tds.model;

import java.util.function.Predicate;

//Clase que encapsula los criterios de búsqueda
public class CriteriosBusqueda {
	private String texto;
	private String telefono;
	private String nombreContacto;

	/**
	 * Constructor para crear un objeto de criterios de búsqueda
	 *
	 * @param texto
	 * @param telefono
	 * @param nombreContacto
	 */
	public CriteriosBusqueda(String texto, String telefono, String nombreContacto) {
		this.texto = texto;
		this.telefono = telefono;
		this.nombreContacto = nombreContacto;
	}

	/**
	 * Convierte los criterios de búsqueda en un predicado que puede aplicarse a los
	 * mensajes
	 *
	 * @return
	 */
	public Predicate<Mensaje> toPredicate() {
		return mensaje -> {
			boolean cumple = true;

			// Filtar por teto del mensaje
			if (texto != null && !texto.isEmpty()) {
				cumple = mensaje.getTexto().toLowerCase().contains(texto.toLowerCase());
			}

			// Filtar por teléfono (emisor o receptor)
			if (cumple && telefono != null && !telefono.isEmpty()) {
				cumple = coincideTelefono(mensaje, telefono);
			}

			// Filtrar por nombre de contacto (emisor o receptor)
			if (cumple && nombreContacto != null && !nombreContacto.isEmpty()) {
				cumple = coincideNombre(mensaje, nombreContacto);
			}

			return cumple;
		};
	}

	/**
	 * Verifca si el teléfono coincide con el emisor o receptor del mensaje
	 *
	 * @param mensaje
	 * @param telefono
	 * @return true si hay concidencia
	 */
	private boolean coincideTelefono(Mensaje mensaje, String telefono) {
		// Verificar si el emisor tiene ese teléfono
		boolean emisorCoincide = mensaje.getEmisor().getPhone().equals(telefono);

		// Verificar si el emisor tiene ese teléfono, para contactos individuales
		boolean receptorCoincide = false;
		if (mensaje.getReceptor() instanceof ContactoIndividual) {
			ContactoIndividual contacto = (ContactoIndividual) mensaje.getReceptor();
			receptorCoincide = contacto.getMovil().equals(telefono);
		}

		// El mensaje coincide si el emisor o el receptor tienen el teléfono buscado
		return emisorCoincide || receptorCoincide;
	}

	/**
	 * Verifica si el nombre coincide con el emisor o receptor del mensaje
	 *
	 * @param mensaje
	 * @param nombre
	 * @return
	 */
	private boolean coincideNombre(Mensaje mensaje, String nombre) {
		// Verificar si el nombre del emisor contiene la cadena buscada
		boolean emisorCoincide = mensaje.getEmisor().getName().toLowerCase().contains(nombre.toLowerCase());

		// Verificar si el nombre del receptor contiene la cadena buscada
		boolean receptorCoincide = mensaje.getReceptor().getNombre().toLowerCase().contains(nombre.toLowerCase());

		return emisorCoincide || receptorCoincide;
	}

	/**
	 * Verifica si no se ha especificado ningún criterio de búsqueda
	 *
	 * @return
	 */
	public boolean isVacio() {
		return (texto == null || texto.isEmpty()) && (telefono == null || telefono.isEmpty())
				&& (nombreContacto == null || nombreContacto.isEmpty());
	}
}