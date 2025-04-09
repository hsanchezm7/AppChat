package umu.tds.controlador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import umu.tds.dao.AdaptadorContactoDAO;
import umu.tds.dao.AdaptadorContactoIndividualDAO;
import umu.tds.dao.AdaptadorGrupoDAO;
import umu.tds.dao.AdaptadorMensajeDAO;
import umu.tds.dao.AdaptadorUsuarioDAO;
import umu.tds.dao.DAOFactory;
import umu.tds.model.Contacto;
import umu.tds.model.ContactoIndividual;
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

		// List<Mensaje> mensajes = mensajeDAO.recuperarAllMensajes();
		// user.setMensajes(mensajes);

		mensajeDAO.recuperarAllMensajes();
		usuarioDAO.modificarUsuario(usuarioRegistrado);

		cargarMensajesNoRegistrados();

		return true;
	}
	
	/**
	 * Registra un nuevo usuario en la aplicación.
	 *
	 * @param phone       el número de teléfono del nuevo usuario.
	 * @param firstName   el nombre del usuario.
	 * @param lastName    los apellidos del usuario.
	 * @param password    la contraseña del usuario.
	 * @param fechaNacim  la fecha de nacimiento del usuario.
	 * @param imagenURL   la URL de la imagen de perfil.
	 * @param saludo      el mensaje de saludo personalizado.
	 * @return {@code true} si el registro se realiza con éxito, {@code false} si
	 *         ya existe un usuario con ese número de teléfono o si no se pudo
	 *         añadir al repositorio.
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
	 * @param miembros       la lista de contactos individuales que formarán parte del grupo.
	 * @param imagenGrupoURL la URL de la imagen asociada al grupo.
	 * @return {@code true} si el grupo se crea y registra correctamente, {@code false} en caso contrario.
	 */
	public boolean addGrupo(String nombre, List<ContactoIndividual> miembros, String imagenGrupoURL) {

		Grupo grupo = new Grupo(nombre, user, miembros, imagenGrupoURL);

		user.addContacto(grupo); // Para el usuario administrador añadir el grupo a su lista de contactos

		contactoDAO.registrarContacto(grupo); // Registrar el grupo mediante el adaptador
		usuarioDAO.modificarUsuario(user); // Modificar el usuario en la base de datos para que queden reflejados los
											// cambios

		// Un usuario supongo que para añadirlo a un grupo previamente tmb debía estar
		// registrado en el sistema
		miembros.stream().forEach(m -> {
			m.getUsuario().addContacto(grupo); // Añadir a la lista de contactos de cada contacto individual el grupo
			usuarioDAO.modificarUsuario(m.getUsuario()); // Modificar el usuario en la base de datos para que queden
															// reflejados los cambios
		});

		return true;
	}

	public boolean sendMessage(String texto, Contacto contacto) {
		Mensaje mensaje = new Mensaje(texto, user, contacto, LocalDateTime.now(), 0);

		// Añado mensaje a lista de mensajes del contacto
		contacto.addMensaje(mensaje);

		// user.addMensaje(mensaje);

		mensajeDAO.registrarMensaje(mensaje); // registro mensaje enviado

		contactoDAO.modificarContacto(contacto); // modifico el contacto
		// usuarioDAO.modificarUsuario(user);

		/*
		 * if (contacto instanceof ContactoIndividual) { ContactoIndividual receptor =
		 * (ContactoIndividual) contacto; Usuario usuarioReceptor =
		 * receptor.getUsuario();
		 * 
		 * // Verificar si el receptor ya tiene al emisor como contacto boolean
		 * emisorYaExiste = usuarioReceptor.getContactos().stream() .filter(c -> c
		 * instanceof ContactoIndividual).map(c -> (ContactoIndividual) c) .anyMatch(c
		 * -> c.getUsuario().getPhone().equals(user.getPhone()));
		 * 
		 * if (!emisorYaExiste) {
		 * System.out.println("Añadiendo emisor a la lista de contactos del receptor");
		 * ContactoIndividual nuevoContacto = new ContactoIndividual(user.getName(),
		 * user.getPhone(), user); usuarioReceptor.addContacto(nuevoContacto);
		 * 
		 * // Añadir el mensaje al contacto del receptor también
		 * nuevoContacto.addMensaje(mensaje);
		 * 
		 * contactoDAO.registrarContacto(nuevoContacto);
		 * usuarioDAO.modificarUsuario(usuarioReceptor); } else { // El receptor ya //
		 * tiene al emisor como contacto, buscar ese contacto y añadir el mensaje
		 * ContactoIndividual contactoExistente =
		 * usuarioReceptor.getContactos().stream() .filter(c -> c instanceof
		 * ContactoIndividual).map(c -> (ContactoIndividual) c) .filter(c ->
		 * c.getUsuario().getPhone().equals(user.getPhone())).findFirst().orElse(null);
		 * 
		 * if (contactoExistente != null) { contactoExistente.addMensaje(mensaje);
		 * contactoDAO.modificarContacto(contactoExistente);
		 * usuarioDAO.modificarUsuario(usuarioReceptor); } } }
		 * 
		 * 
		 */
		return true;

	}

	public void cargarMensajesNoRegistrados() {
		List<Mensaje> todosLosMensajes = mensajeDAO.recuperarAllMensajes();

		for (Mensaje mensaje : todosLosMensajes) {
			if (mensaje.getReceptor() instanceof ContactoIndividual) {
				ContactoIndividual contactoReceptor = (ContactoIndividual) mensaje.getReceptor();

				if (contactoReceptor.getUsuario().equals(user)) {
					Usuario emisor = mensaje.getEmisor();

					// Verificar si el contacto ya existe
					ContactoIndividual contactoExistente = user.getContactos().stream()
							.filter(c -> c instanceof ContactoIndividual).map(c -> (ContactoIndividual) c)
							.filter(c -> c.getUsuario().equals(emisor)).findFirst().orElse(null);

					if (contactoExistente == null) {
						// Si el contacto no existe, crearlo y agregar el mensaje
						ContactoIndividual nuevoContacto = new ContactoIndividual(emisor.getName(), emisor.getPhone(),
								emisor);
						user.addContacto(nuevoContacto);

						// Solo agregar mensaje si aún no está en la lista del contacto
						if (!nuevoContacto.getMensajes().contains(mensaje)) {
							nuevoContacto.addMensaje(mensaje);
						}

						contactoDAO.registrarContacto(nuevoContacto);
						usuarioDAO.modificarUsuario(user);
					} else {
						// Si el contacto ya existe, solo agregar el mensaje si aún no está en la
						// conversación
						if (!contactoExistente.getMensajes().contains(mensaje)) {
							contactoExistente.addMensaje(mensaje);
							contactoDAO.modificarContacto(contactoExistente);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Actualiza el estado {@code premium} de un usuario.
	 *
	 * @param premium    el nuevo estado {@code premium}.
	 */
	public void setUserPremiumStatus(boolean premium) {
		user.setPremium(premium);
		usuarioDAO.modificarUsuario(user);
	}

}
