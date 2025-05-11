package umu.tds.controlador;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;

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
import umu.tds.model.DescuentoFecha;
import umu.tds.model.DescuentoMensajes;
import umu.tds.model.Mensaje;
import umu.tds.model.RepositorioUsuarios;
import umu.tds.model.Usuario;
import umu.tds.model.Grupo;

/**
 * Clase controlador de la aplicación.
 */
public class AppChat {
	
	/* Configuración */
	private static final double PRECIO_BASE_APPCHAT_PREMIUM = 11.99;
	
	private static final LocalDate DESC_INICIO = LocalDate.of(2000, 1, 1);
	private static final LocalDate DESC_FIN = LocalDate.of(2025, 5, 10);
	
	private static final int DESC_MIN_MENSAJES = 1;

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
	
	public double getPrecioBaseAppchatPremium() {
		return PRECIO_BASE_APPCHAT_PREMIUM;
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

		ContactoIndividual contacto = new ContactoIndividual(name, phone, userToAdd, true);
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
		/*System.out.println("\n------ INICIO sendMessage ------");
		System.out.println("Usuario actual: " + user.getName() + " (" + user.getPhone() + ")");
		System.out.println("Enviando mensaje a: " + contacto.getNombre());
		System.out.println("Texto: " + texto);
*/
		if (texto == null || contacto == null)
			return false;

		// Se crea el mensaje original con la fecha actual, sin emoticono, usuario
		// emisor el que ha iniciado sesión y receptor contacto
		Mensaje mensaje = new Mensaje(texto, user, contacto, LocalDateTime.now(), -1);

		/*System.out.println("\nMensaje ORIGINAL creado:");
		System.out.println("- Emisor: " + mensaje.getEmisor().getName() + " (" + mensaje.getEmisor().getPhone() + ")");
		System.out.println("- Receptor: " + mensaje.getReceptor().getNombre());
*/
		//Se añade el mensaje al contacto
		contacto.addMensaje(mensaje);
		//Se guarda en la base de datos
		mensajeDAO.registrarMensaje(mensaje);
		//Se actualiza el contacto en la base de datos
		contactoDAO.modificarContacto(contacto); 

		//System.out.println("Mensaje registrado en DB y añadido al contacto del emisor");

		// Si es contacto individual, se replica el mensaje en el receptor
		if (contacto instanceof ContactoIndividual) {

			//System.out.println("\nEl contacto es individual - replicando mensaje");

			ContactoIndividual Contactoreceptor = (ContactoIndividual) contacto;
			Usuario receptor = Contactoreceptor.getUsuario();

			//System.out.println("Usuario receptor: " + receptor.getName() + " (" + receptor.getPhone() + ")");

			// Hay que buscar si el receptor tiene ya al emisor como contacto o no
			ContactoIndividual contactoDelEmisor = receptor.getContactos().stream()
					.filter(c -> c instanceof ContactoIndividual)
					.map(c -> (ContactoIndividual) c)
					.filter(c -> c.getUsuario() != null && c.getUsuario().getPhone().equals(user.getPhone()))
					.findFirst()
					.orElse(null);
			// Si no lo tiene, se crea el contacto y se registra
			if (contactoDelEmisor == null) {

				//System.out.println("El receptor NO tiene al emisor como contacto - creando nuevo contacto");

				contactoDelEmisor = new ContactoIndividual(user.getPhone(), user.getPhone(), user, false);
				receptor.addContacto(contactoDelEmisor);
				contactoDAO.registrarContacto(contactoDelEmisor);
				
			} else {
				//System.out.println("El receptor SÍ tiene al emisor como contacto: " + contactoDelEmisor.getNombre());
			}

			// Se crea una copia del mensaje para el receptor, apuntando al contacto que
			// representa al propio receptor
			//Mensaje copia = new Mensaje(texto, user, receptor, LocalDateTime.now(), -1);

			/*System.out.println("\nCopia NUEVA propuesta:");
			System.out.println("- Emisor: " + copia.getEmisor().getName() + " (" + copia.getEmisor().getPhone() + ")");
			System.out.println("- Receptor: " + copia.getReceptor().getNombre());
*/			
			contactoDelEmisor.addMensaje(mensaje);
		//	mensajeDAO.registrarMensaje(copia);
			

			//System.out.println(
			//		"\nMensaje copia registrado en DB y añadido al contacto del emisor en la lista del receptor");

			// Actualizar el contacto del receptor y el propio usuario receptor
			contactoDAO.modificarContacto(contactoDelEmisor);
			usuarioDAO.modificarUsuario(receptor);

			//System.out.println("Contacto y usuario receptor actualizados en DB");

		} else if (contacto instanceof Grupo) {

			System.out.println("\nEl contacto es un grupo - actualizando grupo directamente");

			// Si el mensaje es para un grupo, se modifica directamente el grupo
			contactoDAO.modificarContacto(contacto);
		}

		return true;

	}
	
	
	/**
	 * Función que envía un emoticono a un contacto (individual o grupo)
	 * Se encarga de registrar el mensaje, actualizar los contactos involucrados
	 * y reflejar el mensaje también en el receptor
	 * 
	 * @param emojiId El ID del emoticono a enviar
	 * @param contacto El contacto (individual o grupo) destinatario
	 * @return true si el mensaje se envió correctamente, false en caso contrario
	 */
	public boolean sendEmoji(int emojiId, Contacto contacto) {
	    System.out.println("\n------ INICIO sendEmoji ------");
	    System.out.println("Usuario actual: " + user.getName() + " (" + user.getPhone() + ")");
	    System.out.println("Enviando emoticono a: " + contacto.getNombre());
	    System.out.println("Emoticono ID: " + emojiId);
	    
	    if (contacto == null || emojiId < 0)
	        return false;
	    
	    // Se crea el mensaje con emoticono, sin texto, usuario emisor el actual y receptor el contacto
	    Mensaje mensaje = new Mensaje("", user, contacto, LocalDateTime.now(), emojiId);
	    
	    System.out.println("\nMensaje ORIGINAL con emoticono creado:");
	    System.out.println("- Emisor: " + mensaje.getEmisor().getName() + " (" + mensaje.getEmisor().getPhone() + ")");
	    System.out.println("- Receptor: " + mensaje.getReceptor().getNombre());
	    System.out.println("- Emoticono ID: " + mensaje.getEmoticono());
	    
	    // Registrar el mensaje en el contacto del emisor
	    contacto.addMensaje(mensaje);
	    mensajeDAO.registrarMensaje(mensaje);
	    contactoDAO.modificarContacto(contacto);
	    
	    System.out.println("Mensaje registrado en DB y añadido al contacto del emisor");
	    
	    // Si es contacto individual, se replica el mensaje en el receptor
	    if (contacto instanceof ContactoIndividual) {
	        System.out.println("\nEl contacto es individual - replicando mensaje");
	        
	        ContactoIndividual receptorContacto = (ContactoIndividual) contacto;
	        Usuario receptor = receptorContacto.getUsuario();
	        
	        System.out.println("Usuario receptor: " + receptor.getName() + " (" + receptor.getPhone() + ")");
	        
	        // Buscar si el receptor tiene al emisor como contacto
	        ContactoIndividual contactoDelEmisor = receptor.getContactos().stream()
	                .filter(c -> c instanceof ContactoIndividual)
	                .map(c -> (ContactoIndividual) c)
	                .filter(c -> c.getUsuario() != null && c.getUsuario().getPhone().equals(user.getPhone()))
	                .findFirst()
	                .orElse(null);
	        
	        // Si el receptor no tiene al emisor como contacto, crearlo
	        if (contactoDelEmisor == null) {
	            System.out.println("El receptor NO tiene al emisor como contacto - creando nuevo contacto");
	            contactoDelEmisor = new ContactoIndividual(user.getName(), user.getPhone(), user, false);
	            receptor.addContacto(contactoDelEmisor);
	            contactoDAO.registrarContacto(contactoDelEmisor);
	        } else {
	            System.out.println("El receptor SÍ tiene al emisor como contacto: " + contactoDelEmisor.getNombre());
	        }
	        
	        contactoDelEmisor.addMensaje(mensaje);
	        
	        usuarioDAO.modificarUsuario(receptor);
	        
	        System.out.println("Contacto y usuario receptor actualizados en DB");
	    } else if (contacto instanceof Grupo) {
	        System.out.println("\nEl contacto es un grupo - actualizando grupo directamente");
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

	/**
	 * Actualiza un contacto existente marcándolo como añadido manualmente y
	 * cambiando su nombre
	 * 
	 * @param contacto    El contacto a actualizar
	 * @param nuevoNombre El nuevo nombre para el contacto
	 * @return true si la operación fue exitosa
	 */
	public boolean actualizarContacto(ContactoIndividual contacto, String nuevoNombre) {
		if (contacto == null || nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
			return false;
		}

		contacto.setNombre(nuevoNombre);
		contacto.setAñadidoManualmente(true);

		contactoDAO.modificarContacto(contacto);
		usuarioDAO.modificarUsuario(user);

		return true;
	}
	
	public void logout() {
		this.user = null;
	}
	
	/**
	 * Devuelve el contacto asociado al usuario dado, si existe en la lista de contactos
	 * del usuario actualmente autenticado.
	 *
	 * @param usuario El usuario cuyo contacto se desea obtener.
	 * @return El ContactoIndividual asociado, o null si no se encuentra.
	 */
	public ContactoIndividual getContactoDeUsuario(Usuario usuario) {
	    if (usuario == null || user == null) {
	        return null;
	    }

	    return user.getContactos().stream()
	        .filter(c -> c instanceof ContactoIndividual)
	        .map(c -> (ContactoIndividual) c)
	        .filter(c -> c.getUsuario() != null && c.getUsuario().getPhone().equals(usuario.getPhone()))
	        .findFirst()
	        .orElse(null);
	}
	
	// Métodos para añadir en la clase AppChat

	/**
	 * Obtiene la imagen del usuario actual
	 * @return ImageIcon con la imagen del usuario actual o null si no tiene
	 */
	public ImageIcon getImagenUsuarioActual() {
	    if (user == null) return null;
	    
	    String rutaImagen = user.getImagenURL();
	    if (rutaImagen == null || rutaImagen.isEmpty()) {
	        return null;
	    }
	    
	    try {
	        return new ImageIcon(rutaImagen);
	    } catch (Exception e) {
	        System.err.println("Error al cargar la imagen del usuario: " + e.getMessage());
	        return null;
	    }
	}

	/**
	 * Cambia la imagen del usuario actual
	 * @param rutaImagen Ruta a la imagen en el sistema de archivos
	 * @return true si se ha cambiado correctamente, false en caso contrario
	 */
	public boolean cambiarImagenUsuario(String rutaImagen) {
	    if (user == null) return false;
	    
	    try {
	        // Verificar que la imagen existe
	        File archivo = new File(rutaImagen);
	        if (!archivo.exists() || !archivo.isFile()) {
	            return false;
	        }
	        
	        // Actualizar la ruta de la imagen en el objeto Usuario
	        user.setImagenURL(rutaImagen);
	        
	        // Persistir el cambio
	        usuarioDAO.modificarUsuario(user);
	        return true;
	    } catch (Exception e) {
	        System.err.println("Error al cambiar la imagen del usuario: " + e.getMessage());
	        return false;
	    }
	}
	
	/**
	 * Calcula los descuentos disponibles para un usuario según diferentes estrategias.
	 *
	 * @param usuario el usuario al que se le evaluarán los descuentos disponibles
	 * @return mapa con los nombres de cada tipo de descuento como clave y su valor numérico como valor
	 */
	public Map<String, Double> obtenerDescuentosDisponibles() {
	    DescuentoFecha descFecha = new DescuentoFecha(DESC_INICIO, DESC_FIN);
	    DescuentoMensajes descMensaje = new DescuentoMensajes(DESC_MIN_MENSAJES);

	    double valorFecha = descFecha.calcularDescuento(user);
	    double valorMensaje = descMensaje.calcularDescuento(user);

	    Map<String, Double> descuentos = new HashMap<>();
	    descuentos.put("Descuento por fecha de nacimiento", valorFecha);
	    descuentos.put("Descuento por mensajes enviados", valorMensaje);

	    return descuentos;
	}

}
