package umu.tds.controlador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import umu.tds.dao.AdaptadorContactoDAO;
import umu.tds.dao.AdaptadorContactoIndividualDAO;
import umu.tds.dao.AdaptadorGrupoDAO;
import umu.tds.dao.AdaptadorMensajeDAO;
import umu.tds.dao.AdaptadorUsuarioDAO;
import umu.tds.dao.DAOFactory;
import umu.tds.model.BuscadorMensajes;
import umu.tds.model.Contacto;
import umu.tds.model.ContactoIndividual;
import umu.tds.model.CriteriosBusqueda;
import umu.tds.model.Mensaje;
import umu.tds.model.RepositorioUsuarios;
import umu.tds.model.Usuario;
import umu.tds.model.Grupo;

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

		mensajeDAO.recuperarAllMensajes();
		usuarioDAO.modificarUsuario(usuarioRegistrado);

		return true;
	}

	/**
	 * Registra un nuevo usuario en la aplicación.
	 *
	 * @param phone      el número de teléfono del nuevo usuario.
	 * @param firstName  el nombre del usuario.
	 * @param lastName   los apellidos del usuario.
	 * @param password   la contraseña del usuario.
	 * @param fechaNacim la fecha de nacimiento del usuario.
	 * @param imagenURL  la URL de la imagen de perfil.
	 * @param saludo     el mensaje de saludo personalizado.
	 * @return {@code true} si el registro se realiza con éxito, {@code false} si ya
	 *         existe un usuario con ese número de teléfono o si no se pudo añadir
	 *         al repositorio.
	 */
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

	/**
	 * Función que añade un nuevo contacto individual al usuario actual, si aún no
	 * lo tiene registrado
	 * 
	 * @param name
	 * @param phone
	 * @return
	 */
	public boolean addContacto(String name, String phone) {
		// Comprobar que el número no está asociado ya a un contacto del usuario
		// principal

		Usuario userToAdd = repoUsuarios.getUserByPhone(phone);

		ContactoIndividual contacto = new ContactoIndividual(name, phone, userToAdd);
		user.addContacto(contacto);

		contactoDAO.registrarContacto(contacto);
		usuarioDAO.modificarUsuario(user);

		return true;
	}

	/**
	 * Crea un nuevo grupo con los contactos especificados y lo añade tanto al
	 * usuario actual como a los miembros.
	 *
	 * @param nombre         el nombre del grupo.
	 * @param miembros       la lista de contactos individuales que formarán parte
	 *                       del grupo.
	 * @param imagenGrupoURL la URL de la imagen asociada al grupo.
	 * @return {@code true} si el grupo se crea y registra correctamente,
	 *         {@code false} en caso contrario.
	 */
	public boolean addGrupo(String nombre, List<ContactoIndividual> miembros, String imagenGrupoURL) {

		Grupo grupo = new Grupo(nombre, user, miembros, imagenGrupoURL);

		// Añadir el grupo al usuario actual
		user.addContacto(grupo);
		contactoDAO.registrarContacto(grupo);
		usuarioDAO.modificarUsuario(user);

		// Añadir el grupo a todos los miembros
		miembros.stream().forEach(m -> {
			m.getUsuario().addContacto(grupo);
			usuarioDAO.modificarUsuario(m.getUsuario());
		});

		return true;
	}

	/**
	 * Función que envía un mensaje a un contacto (indivual o grupo) Se encarga de
	 * registrar el mensaje, actualizar los contactos involucrados y reflejar el
	 * mensaje también en el receptor
	 * 
	 * @param texto
	 * @param contacto
	 * @return
	 */
	public boolean sendMessage(String texto, Contacto contacto) {

		if (texto == null || contacto == null)
			return false;

		// Se crea el mensaje original con la fecha actual, sin emoticono, usuario
		// emisor el que ha iniciado sesión y receptor contacto
		Mensaje mensaje = new Mensaje(texto, user, contacto, LocalDateTime.now(), 0);
		// Hay que registrar el mensaje en la base de datos y en el contacto del emisor
		contacto.addMensaje(mensaje);
		mensajeDAO.registrarMensaje(mensaje);
		contactoDAO.modificarContacto(contacto); // Se actualiza el contacto del emisor

		// Si es contacto individual, se replica el mensaje en el receptor
		if (contacto instanceof ContactoIndividual) {
			ContactoIndividual receptorContacto = (ContactoIndividual) contacto;
			Usuario receptor = receptorContacto.getUsuario();

			// Hay que buscar si el receptor tiene ya al emisor como contacto o no
			ContactoIndividual contactoDelEmisor = receptor.getContactos().stream()
					.filter(c -> c instanceof ContactoIndividual).map(c -> (ContactoIndividual) c)
					.filter(c -> c.getUsuario().equals(user)).findFirst().orElse(null);
			// Si no lo tiene, se crea el contacto y se registra
			if (contactoDelEmisor == null) {
				contactoDelEmisor = new ContactoIndividual(user.getName(), user.getPhone(), user);
				receptor.addContacto(contactoDelEmisor);
				contactoDAO.registrarContacto(contactoDelEmisor);
			}
			// Se crea una copia del mensaje para el recptor, apuntando a su contado del
			// emisor
			Mensaje copia = new Mensaje(texto, user, contactoDelEmisor, LocalDateTime.now(), 0);
			mensajeDAO.registrarMensaje(copia);
			contactoDelEmisor.addMensaje(copia);
			// Actualizar el contacto del receptor y el propio usuario receptor
			contactoDAO.modificarContacto(contactoDelEmisor);
			usuarioDAO.modificarUsuario(receptor);
		} else if (contacto instanceof Grupo) {
			// Si el mensaje es para un grupo, se modifica directamente el grupo
			contactoDAO.modificarContacto(contacto);
		}

		return true;

	}

	/**
	 * Actualiza el estado {@code premium} de un usuario.
	 *
	 * @param premium el nuevo estado {@code premium}.
	 */
	public void setUserPremiumStatus(boolean premium) {
		user.setPremium(premium);
		usuarioDAO.modificarUsuario(user);
	}

	/**
	 * 
	 * @param mensaje
	 * @return
	 */
	public boolean esMensajeDelUsuarioActual(Mensaje mensaje) {
		return mensaje.getEmisor().equals(user);
	}

	/**
	 * 
	 * @param contacto
	 * @return
	 */
	public List<Mensaje> getMensajesConContacto(Contacto contacto) {
		if (contacto == null) {
			return Collections.emptyList();
		}
		return contacto.getMensajes();
	}

	/**
	 * 
	 * @return
	 */
	public List<Contacto> getContactosUsuarioActual() {
		if (user == null) {
			return Collections.emptyList();
		}
		return user.getContactos();
	}

	/**
	 * 
	 * @return
	 */
	public String getNombreUsuarioActual() {
		return user != null ? user.getName() : "";
	}

	/**
	 * Busca mensajes que cumplan con los criterios especificados.
	 * 
	 * @param texto
	 * @param telefono
	 * @param nombreContacto
	 * @return
	 * @throws IllegalArgumentException
	 */
	public List<Mensaje> buscarMensajes(String texto, String telefono, String nombreContacto)
			throws IllegalArgumentException {
		if (user == null)
			return Collections.emptyList();

		// Crear los criterios de búsqueda
		CriteriosBusqueda criterios = new CriteriosBusqueda(texto, telefono, nombreContacto);

		// Validar que al menos hay un criterio
		if (criterios.isVacio()) {
			throw new IllegalArgumentException("Debe introducir al menos un criterio de búsqueda");
		}

		BuscadorMensajes buscador = new BuscadorMensajes(user);
		return buscador.buscar(criterios);
	}

}
