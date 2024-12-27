package umu.tds.controlador;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import umu.tds.dao.DAOFactory;
import umu.tds.dao.tds.AdaptadorContactoIndividualTDS;
import umu.tds.dao.tds.AdaptadorGrupoTDS;
import umu.tds.dao.tds.AdaptadorMensajeTDS;
import umu.tds.dao.tds.AdaptadorUsuarioTDS;
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

	private AdaptadorUsuarioTDS usuarioDao;
	private AdaptadorMensajeTDS mensajeDao;
	private AdaptadorGrupoTDS grupoDao;
	private AdaptadorContactoIndividualTDS contactoIndividualDao;

	/* Constructor */
	private AppChat(RepositorioUsuarios repoUsuarios, DAOFactory daoFactory) {
		this.repoUsuarios = repoUsuarios;
		this.usuarioDao = daoFactory.getUsuarioDAO();
		this.mensajeDao = daoFactory.getMensajeDAO();
		this.grupoDao = daoFactory.getGrupoDAO();
		this.contactoIndividualDao = daoFactory.getContactoIndividualDAO();

		this.user = null;
	}

	/* Consulta */
	public Usuario getCurrentUser() {
		return user;
	}

	/**
	 * Obtiene la instancia única de la clase {@code AppChat}.
	 *
	 * Si la instancia aún no ha sido creada, se inicializa en este momento.
	 *
	 * @param repoUsuarios El repositorio de usuarios.
	 * @return La instancia única de AppChat.
	 */
	public static AppChat getInstance(RepositorioUsuarios repoUsuarios, DAOFactory daoFactory) {
		if (unicaInstancia == null) {
			unicaInstancia = new AppChat(repoUsuarios, daoFactory);
		}
		
		return unicaInstancia;
	}

	/**
	 * Obtiene la instancia única de la clase {@code AppChat}.
	 *
	 * @return La instancia única de AppChat.
	 */
	public static AppChat getInstance() {
		if (unicaInstancia == null) {
			throw new IllegalStateException("No existe ninguna instancia de AppChat.");
		}
		return unicaInstancia;
	}

	/* Métodos */
	/**
	 * Inicia sesión en la aplicación.
	 * 
	 * @param phone    el número de teléfono.
	 * @param password la contraseña del usuario.
	 * @return {@code false} si no existe un usuario registrado con ese nombre o la
	 *         contraseña es incorrecta, {@code true} si se ha iniciado sesión
	 *         exitosamente.
	 */
	public boolean login(String phone, char[] password) {
		if (!isPhoneRegistered(phone))
			return false;

		Usuario usuarioRegistrado = repoUsuarios.getUserByPhone(phone);
		if (!Arrays.equals(password, usuarioRegistrado.getPassword())) {
			return false;
		}

		this.user = usuarioRegistrado;

		return true;
	}

	public boolean register(String phone, String firstName, String lastName, char[] password, LocalDate fechaNacim,
			String imagenURL, String saludo) {
		if (isPhoneRegistered(phone))
			return false;

		Usuario usuario = new Usuario(phone, password, firstName + lastName, fechaNacim, imagenURL, saludo,
				LocalDate.now());
		
		if (!repoUsuarios.addUserToRepo(usuario)) {
			return false;
		}
		
		usuarioDao.registrarUsuario(usuario);

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

	public static List<Mensaje> obtenerMensajesRecientesPorUsuario;

}
