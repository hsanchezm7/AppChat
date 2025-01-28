package umu.tds.dao.tds;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dao.AdaptadorContactoDAO;
import umu.tds.dao.AdaptadorContactoIndividualDAO;
import umu.tds.dao.AdaptadorGrupoDAO;
import umu.tds.dao.AdaptadorUsuarioDAO;
import umu.tds.dao.DAOFactory;
import umu.tds.model.Contacto;
import umu.tds.model.ContactoIndividual;
import umu.tds.model.Grupo;
import umu.tds.model.Usuario;

public class AdaptadorUsuarioTDS implements AdaptadorUsuarioDAO {

	public static final String ENTITY_TYPE = "Usuario";

	private static final String PHONE_FIELD = "phone";
	private static final String PASSWORD_FIELD = "password";
	private static final String NAME_FIELD = "name";
	private static final String BIRTH_FIELD = "fechaNacim";
	private static final String IMAGENURL_FIELD = "imagenURL";
	private static final String SALUDO_FIELD = "saludo";
	private static final String CONTACTOS_FIELD = "contactos";
	private static final String PREMIUM_FIELD = "premium";
	private static final String FECHAREG_FIELD = "fechaRegistro";

	private static AdaptadorUsuarioTDS unicaInstancia = null;
	
	private static ServicioPersistencia servPersistencia;
	
	private DateTimeFormatter dateFormat;

	private AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		
		dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	}

	public static AdaptadorUsuarioTDS getInstance() {

		if (unicaInstancia == null) {
			return new AdaptadorUsuarioTDS();
		} else {
			return unicaInstancia;
		}

	}

	@Override
	public void registrarUsuario(Usuario usuario) {
		if (servPersistencia.recuperarEntidad(usuario.getId()) != null)
			return;
		
		/* Asegurar contactos registrados */
		List<Contacto> contactos = usuario.getContactos();
		
		AdaptadorGrupoDAO adapterG = DAOFactory.getInstance().getGrupoDAO();
		AdaptadorContactoIndividualDAO adapterCI = DAOFactory.getInstance().getContactoIndividualDAO();
		
        for (Contacto contacto : contactos) {
            if (contacto instanceof ContactoIndividual) {
                adapterCI.registrarContactoIndividual((ContactoIndividual) contacto);
            } else if (contacto instanceof Grupo) {
            	adapterG.registrarGrupo((Grupo) contacto);
            }
        }

		Entidad entUsuario = new Entidad();
		entUsuario.setNombre(ENTITY_TYPE);

		entUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad(PHONE_FIELD, usuario.getPhone()),
				new Propiedad(PASSWORD_FIELD, new String(usuario.getPassword())),
				new Propiedad(NAME_FIELD, usuario.getName()),
				new Propiedad(BIRTH_FIELD, usuario.getFechaNacimiento().format(dateFormat)),
				new Propiedad(IMAGENURL_FIELD, usuario.getImagenURL()),
				new Propiedad(SALUDO_FIELD, usuario.getSaludo()),
				new Propiedad(CONTACTOS_FIELD, getIdsFromContacts(contactos)),
				new Propiedad(PREMIUM_FIELD, String.valueOf(usuario.isPremium())),
				new Propiedad(FECHAREG_FIELD, usuario.getFechaRegistro().format(dateFormat)))));

		entUsuario = servPersistencia.registrarEntidad(entUsuario);
		
		usuario.setId(entUsuario.getId());
	}

	@Override
	public void borrarUsuario(Usuario usuario) {
		/* TODO: BORRAR CONTACTOS ASOCIADOS */

		Entidad entUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		servPersistencia.borrarEntidad(entUsuario);
	}

	@Override
	public void modificarUsuario(Usuario usuario) {
		Entidad entUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		
		for (Propiedad prop : entUsuario.getPropiedades()) {
			switch (prop.getNombre()) {
	        case PHONE_FIELD:
	            prop.setValor(usuario.getPhone());
	            break;
	        case PASSWORD_FIELD:
	            prop.setValor(new String(usuario.getPassword()));
	            break;
	        case NAME_FIELD:
	            prop.setValor(usuario.getName());
	            break;
	        case BIRTH_FIELD:
	            prop.setValor(usuario.getFechaNacimiento().format(dateFormat));
	            break;
	        case IMAGENURL_FIELD:
	            prop.setValor(usuario.getImagenURL());
	            break;
	        case SALUDO_FIELD:
	            prop.setValor(usuario.getSaludo());
	            break;
	        case CONTACTOS_FIELD:
	            prop.setValor(getIdsFromContacts(usuario.getContactos()));
	            break;
	        case PREMIUM_FIELD:
	            prop.setValor(String.valueOf(usuario.isPremium()));
	            break;
	        case FECHAREG_FIELD:
	            prop.setValor(usuario.getFechaRegistro().format(dateFormat));
	            break;
	        default:
	            break;
	    }
		    servPersistencia.modificarPropiedad(prop);
		}
		
	}
	
	@Override
	public Usuario recuperarUsuario(int id) {
		Entidad entUsuario = servPersistencia.recuperarEntidad(id);

		String phone = servPersistencia.recuperarPropiedadEntidad(entUsuario, PHONE_FIELD);
		char[] password = servPersistencia.recuperarPropiedadEntidad(entUsuario, PASSWORD_FIELD).toCharArray();
		String name = servPersistencia.recuperarPropiedadEntidad(entUsuario, NAME_FIELD);
		LocalDate birthDate = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(entUsuario, BIRTH_FIELD),
				dateFormat);
		String imagenURL = servPersistencia.recuperarPropiedadEntidad(entUsuario, IMAGENURL_FIELD);
		String saludo = servPersistencia.recuperarPropiedadEntidad(entUsuario, SALUDO_FIELD);
		boolean premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(entUsuario, PREMIUM_FIELD));
		LocalDate fechaReg = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(entUsuario, FECHAREG_FIELD),
				dateFormat);
		List<Contacto> contactos = getContactsFromConcatenatedIds(servPersistencia.recuperarPropiedadEntidad(entUsuario, CONTACTOS_FIELD));
		

		Usuario usuario = new Usuario(phone, password, name, birthDate, imagenURL, saludo, fechaReg);
		usuario.setPremium(premium);
		/* TODO: CREAR CONSTRUCTOR QUE ACEPTE CONTACTOS */
		usuario.setContactos(contactos);
		usuario.setId(id);

		return usuario;
	}

	@Override
	public List<Usuario> recuperarAllUsuarios() {
	    return servPersistencia.recuperarEntidades(ENTITY_TYPE).stream()
	            .map(entidad -> recuperarUsuario(entidad.getId()))
	            .collect(Collectors.toCollection(LinkedList::new));
	}

	/* ¿DEBERÍA IR EN LA CLASE USUARIO? */
	private String getIdsFromContacts(List<Contacto> contactos) {
		return contactos.stream()
		        .map(contacto -> String.valueOf(contacto.getId()))
		        .collect(Collectors.joining(", "));
	}

  
	private List<Contacto> getContactsFromConcatenatedIds(String concatenatedIds) {
		if (concatenatedIds == null || concatenatedIds.trim().isEmpty()) {
	        return new ArrayList<>(); // Retorna una lista vacía si la cadena está vacía
	    }
		
		AdaptadorContactoDAO adapterC = DAOFactory.getInstance().getContactoDAO();
		
		return Arrays.stream(concatenatedIds.split(", "))
			    .map(id -> adapterC.recuperarContacto(Integer.parseInt(id.trim())))
			    .collect(Collectors.toList());
	}
}
