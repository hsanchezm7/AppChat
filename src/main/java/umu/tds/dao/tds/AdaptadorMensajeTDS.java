package umu.tds.dao.tds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

import umu.tds.model.Mensaje;
import umu.tds.model.Usuario;
import umu.tds.dao.AdaptadorMensajeDAO;
import umu.tds.dao.DAOFactory;
import umu.tds.model.Contacto;
import umu.tds.model.ContactoIndividual;
import umu.tds.model.Grupo;

public class AdaptadorMensajeTDS implements AdaptadorMensajeDAO {

	private static final String ENTITY_TYPE = "Mensaje";

	private static final String TEXT_FIELD = "texto";
	private static final String EMISOR_FIELD = "emisor";
	private static final String RECEPTOR_FIELD = "receptor";
	private static final String FECHAHORA_FIELD = "fechaHora";
	private static final String EMOTICONO_FIELD = "emoticono";

	private static AdaptadorMensajeTDS unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;

	private DateTimeFormatter dateTimeFormat;

	private AdaptadorMensajeTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	}

	public static AdaptadorMensajeTDS getUnicaInstancia() {

		if (unicaInstancia == null) {
			return new AdaptadorMensajeTDS();
		} else {
			return unicaInstancia;
		}

	}

	@Override
	public void registrarMensaje(Mensaje mensaje) {

		Entidad entMensaje;

		if (servPersistencia.recuperarEntidad(mensaje.getId()) != null)
			return;

		// volver a registrar un usuario? debería ser persistente y estar ya registrado.
		// lo hace el repoUsuarios llamando a su adaptador
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getInstance();
		if (servPersistencia.recuperarEntidad(mensaje.getEmisor().getId()) == null) {
            adaptadorUsuario.registrarUsuario(mensaje.getEmisor());
        }

		// lo mismo con el grupo
		AdaptadorContactoIndividualTDS adaptadorContactoIndividual = AdaptadorContactoIndividualTDS.getInstance();
		AdaptadorGrupoTDS adaptadorGrupo = AdaptadorGrupoTDS.getInstance();
		
		Contacto contacto = mensaje.getReceptor();
		if (contacto instanceof ContactoIndividual) {
			adaptadorContactoIndividual.registrarContactoIndividual((ContactoIndividual) contacto);
		} else if (contacto instanceof Grupo) {
			adaptadorGrupo.registrarGrupo((Grupo) contacto);
		}
	
		entMensaje = new Entidad();
		entMensaje.setNombre(ENTITY_TYPE);
		entMensaje.setPropiedades(new ArrayList<>(Arrays.asList(
		    new Propiedad(TEXT_FIELD, mensaje.getTexto()),
		    new Propiedad(EMISOR_FIELD, String.valueOf(mensaje.getEmisor().getId())),
		    new Propiedad(RECEPTOR_FIELD, String.valueOf(mensaje.getReceptor().getId())),
		    new Propiedad(FECHAHORA_FIELD, dateTimeFormat.format(mensaje.getFechaHora())),
		    new Propiedad(EMOTICONO_FIELD, String.valueOf(mensaje.getEmoticono()))
		)));


		entMensaje = servPersistencia.registrarEntidad(entMensaje);
		
		if (entMensaje == null) {
		    throw new RuntimeException("Error al registrar el mensaje en la persistencia.");
		}
		
		System.out.println("Mensaje " + mensaje.getTexto() + " de " + mensaje.getEmisor().getName() + " a " + mensaje.getReceptor().getNombre() + " enviado con éxito");
		
		mensaje.setId(entMensaje.getId());
		
		PoolDAO.getInstance().addObject(mensaje.getId(), mensaje);

	}

	@Override
	public void borrarMensaje(Mensaje mensaje) {

		Entidad entMensaje = servPersistencia.recuperarEntidad(mensaje.getId());
		servPersistencia.borrarEntidad(entMensaje);

	}

	@Override
	public void modificarMensaje(Mensaje mensaje) {

		Entidad entMensaje = servPersistencia.recuperarEntidad(mensaje.getId()); // mirar esto

		for (Propiedad prop : entMensaje.getPropiedades()) {
		    switch (prop.getNombre()) {
		        case TEXT_FIELD:
		            prop.setValor(String.valueOf(mensaje.getTexto()));
		            break;
		        case EMISOR_FIELD:
		            prop.setValor(String.valueOf(mensaje.getEmisor().getId()));
		            break;
		        case RECEPTOR_FIELD:
		            prop.setValor(String.valueOf(mensaje.getReceptor().getId()));
		            break;
		        case FECHAHORA_FIELD:
		            prop.setValor(dateTimeFormat.format(mensaje.getFechaHora()));
		            break;
		        case EMOTICONO_FIELD:
		            prop.setValor(String.valueOf(mensaje.getEmoticono()));
		            break;
		        default:
		            break;
		    }
		    servPersistencia.modificarPropiedad(prop);
		}
	}

	@Override
	public Mensaje recuperarMensaje(int id) {
		if (PoolDAO.getInstance().contains(id)) {
			return (Mensaje) PoolDAO.getInstance().getObject(id);
		}
		
		System.out.println("Recuperando mensaje...");
		
		Entidad entMensaje = servPersistencia.recuperarEntidad(id);
		if (entMensaje == null) return null;

		String texto = servPersistencia.recuperarPropiedadEntidad(entMensaje, TEXT_FIELD);
		int idEmisor = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(entMensaje, EMISOR_FIELD));
		int idReceptor = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(entMensaje, RECEPTOR_FIELD));
		LocalDateTime fechaHora = LocalDateTime
				.parse(servPersistencia.recuperarPropiedadEntidad(entMensaje, FECHAHORA_FIELD), dateTimeFormat);
		int emoticono = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(entMensaje, EMOTICONO_FIELD));

		Mensaje mensaje = new Mensaje(texto, null, null, fechaHora, emoticono);
		mensaje.setId(id);
		
		PoolDAO.getInstance().addObject(id, mensaje);
		
		AdaptadorUsuarioTDS userDAO = AdaptadorUsuarioTDS.getInstance();
		Usuario user = userDAO.recuperarUsuario(idEmisor);
		mensaje.setEmisor(user);
	
	    Entidad entidadReceptor = servPersistencia.recuperarEntidad(idReceptor);
	    if (entidadReceptor != null && AdaptadorGrupoTDS.ENTITY_TYPE.equals(entidadReceptor.getNombre())) {
	        AdaptadorGrupoTDS adaptadorGrupo = AdaptadorGrupoTDS.getInstance();
	        Grupo grupo = adaptadorGrupo.recuperarGrupo(idReceptor);
	        mensaje.setReceptor(grupo);
	    } else {
	        AdaptadorContactoIndividualTDS adaptadorContacto = AdaptadorContactoIndividualTDS.getInstance();
	        ContactoIndividual contacto = adaptadorContacto.recuperarContactoIndividual(idReceptor);
	        mensaje.setReceptor(contacto);
	    }

		return mensaje;
	}

	@Override
	public List<Mensaje> recuperarAllMensajes() {
		return servPersistencia.recuperarEntidades(ENTITY_TYPE).stream()
	            .map(entidad -> recuperarMensaje(entidad.getId()))
	            .collect(Collectors.toCollection(LinkedList::new));
	}
	
	

}
