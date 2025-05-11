package umu.tds.model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase encargada de realizar búsquedas de mensajes para un usuario. Aplica los
 * criterios de búsqueda especificados sobre los mensajes del usuario
 */
public class BuscadorMensajes {
	private Usuario usuario;

	/**
	 * Constructor que inicializa el buscador para un usuario específico
	 *
	 * @param usuario
	 */
	public BuscadorMensajes(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Busca mensajes según los criterios especificados
	 */
	public List<Mensaje> buscar(CriteriosBusqueda criterios) {
		return obtenerTodosMensajes().stream().filter(criterios.toPredicate()).collect(Collectors.toList());
	}

	/**
	 * Recopila todos los mensajes de todos los contactos del usuario
	 *
	 * @return
	 */
	private List<Mensaje> obtenerTodosMensajes() {
		List<Mensaje> mensajes = new java.util.ArrayList<>();

		if (usuario == null) {
			return mensajes;
		}

		for (Contacto contacto : usuario.getContactos()) {
			mensajes.addAll(contacto.getMensajes());
		}

		return mensajes;
	}
}