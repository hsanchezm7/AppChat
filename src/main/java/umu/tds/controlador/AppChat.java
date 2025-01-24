package umu.tds.controlador;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import umu.tds.dao.AdaptadorContactoDAO;
import umu.tds.dao.AdaptadorContactoIndividualDAO;
import umu.tds.dao.AdaptadorGrupoDAO;
import umu.tds.dao.AdaptadorMensajeDAO;
import umu.tds.dao.AdaptadorUsuarioDAO;
import umu.tds.dao.DAOFactory;
import umu.tds.model.Mensaje;
import umu.tds.model.RepositorioUsuarios;
import umu.tds.model.Usuario;

/**
 * Clase controlador de la aplicación.
 */
public class AppChat {

	public static List<Mensaje> obtenerMensajesRecientesPorUsuario;

	/* Instancia Singleton */
	private static AppChat unicaInstancia = null;

	/**
	 * Obtiene la instancia única de la clase {@code AppChat}.
	 *
	 * Si la instancia aún no ha sido creada, se inicializa en este momento.
	 *
	 * @param repoUsuarios El repositorio de usuarios.
	 * @return La instancia única de AppChat.
	 */
	public static AppChat getInstance() {
		if (unicaInstancia == null) {
			unicaInstancia = new AppChat();
		}

		return unicaInstancia;
	}

	/* Atributos */
	private DAOFactory daoFactory;
	
	private AdaptadorUsuarioDAO usuarioDAO;
	private AdaptadorMensajeDAO mensajeDAO;
	private AdaptadorContactoDAO contactoDAO;
	private AdaptadorGrupoDAO grupoDAO;
	private AdaptadorContactoIndividualDAO contactoIndividualDAO;

	private Usuario user;
	
	private RepositorioUsuarios repoUsuarios;

	/* Constructor */
	private AppChat() {
		initializeAdapters();
		initializeRepos();

		this.user = null;
	}

	/* Consulta */
	public Usuario getCurrentUser() {
		return user;
	}

	/**
	 * Inicializa los atributos de los adaptadores.
	 */
	private void initializeAdapters() {
		// TODO: Añadir try-catch
		this.daoFactory = DAOFactory.getInstance();

		this.usuarioDAO = daoFactory.getUsuarioDAO();
		this.mensajeDAO = daoFactory.getMensajeDAO();
		this.contactoDAO = daoFactory.getContactoDAO();
		this.grupoDAO = daoFactory.getGrupoDAO();
		this.contactoIndividualDAO = daoFactory.getContactoIndividualDAO();
	}

	/**
	 * Inicializa el repositorio de usuarios.
	 */
	private void initializeRepos() {
		// TODO: ¿Añadir try-catch? Creo que aquí no
		this.repoUsuarios = RepositorioUsuarios.getInstance();

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
		if (!isPhoneRegistered(phone)) {
			return false;
		}

		Usuario usuarioRegistrado = repoUsuarios.getUserByPhone(phone);
		if (!Arrays.equals(password, usuarioRegistrado.getPassword())) {
			return false;
		}

		this.user = usuarioRegistrado;

		return true;
	}

	public boolean register(String phone, String firstName, String lastName, char[] password, LocalDate fechaNacim,
			String imagenURL, String saludo) {
		if (isPhoneRegistered(phone)) {
			return false;
		}

		Usuario usuario = new Usuario(phone, password, firstName + lastName, fechaNacim, imagenURL, saludo,
				LocalDate.now());

		if (!repoUsuarios.addUserToRepo(usuario)) {
			return false;
		}

		usuarioDAO.registrarUsuario(usuario);

		return true;
	}

}
