package umu.tds.controlador;

import java.util.List;

import umu.tds.model.Mensaje;
import umu.tds.model.RepositorioUsuarios;
import umu.tds.model.Usuario;

/**
 * Clase controlador de la aplicación.
 */
public class AppChat {

	/* Instancia Singleton */
	private static AppChat unicaInstancia = null;

	/* Atributos */
	private Usuario user;
	private RepositorioUsuarios repoUsuarios;

	/* Constructor */
	private AppChat(RepositorioUsuarios repoUsuarios) {
		this.repoUsuarios = repoUsuarios;

		this.user = null;
	}

	/* Consulta */
	public Usuario getCurrentUser() {
		return user;
	}

	/* Métodos */
	/**
	 * Inicia sesión en la aplicación. MAL. HAY QUE HACER LOGIN EN EL USER, Y AQUI
	 * COMPROBAR CON user.isClave(). estamos violando patron experto
	 * 
	 * @param phone    el número de teléfono.
	 * @param password la contraseña del usuario.
	 * @return {@code false} si no existe un usuario registrado con ese nombre o la
	 *         contraseña es incorrecta, {@code true} si se ha iniciado sesión
	 *         exitosamente.
	 */
	public boolean login(String phone, char[] password) {
		Usuario usuarioReg = repoUsuarios.getUserByPhone(phone);
		if (!isPhoneRegistered(phone) || !usuarioReg.getPassword().equals(password))
			return false;

		this.user = usuarioReg;

		return true;
	}

	/**
	 * Verifica si un número de teléfono está registrado en el sistema.
	 *
	 * @param phone el número de teléfono que se desea comprobar.
	 * @return {@code true} si el número de teléfono está registrado, {@code false}
	 *         si no lo está.
	 */
	public boolean isPhoneRegistered(String phone) {
		return repoUsuarios.getUserByPhone(phone) != null;
	}

	/**
	 * Obtiene la instancia única de la clase {@code AppChat}.
	 *
	 * Si la instancia aún no ha sido creada, se inicializa en este momento.
	 *
	 * @param repoUsuarios El repositorio de usuarios.
	 * @return La instancia única de AppChat.
	 */
	public static AppChat getInstance(RepositorioUsuarios repoUsuarios) {
		if (unicaInstancia == null) {
			unicaInstancia = new AppChat(repoUsuarios);
		}
		return unicaInstancia;
	}

	/**
	 * Obtiene la instancia única de la clase {@code AppChat}.
	 *
	 * @return La instancia única de AppChat.
	 * @thrq
	 */
	public static AppChat getInstance() {
		if (unicaInstancia == null) {
			throw new IllegalStateException("No existe ninguna instancia de AppChat.");
		}
		return unicaInstancia;
	}

	public static List<Mensaje> obtenerMensajesRecientesPorUsuario;

}
