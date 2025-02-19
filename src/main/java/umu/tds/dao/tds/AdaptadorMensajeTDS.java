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
		adaptadorUsuario.registrarUsuario(mensaje.getEmisor());

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
		entMensaje.setNombre("mensaje");
		entMensaje.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("texto", mensaje.getTexto()),
				new Propiedad("emisor", String.valueOf(mensaje.getEmisor().getId())),
				new Propiedad("receptor", String.valueOf(mensaje.getReceptor().getId())),
				new Propiedad("fechaHora", dateTimeFormat.format(mensaje.getFechaHora())),
				new Propiedad("emoticono", String.valueOf(mensaje.getEmoticono())))));

		entMensaje = servPersistencia.registrarEntidad(entMensaje);
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
			if (prop.getNombre().equals("texto")) {
				prop.setValor(String.valueOf(mensaje.getTexto()));
			} else if (prop.getNombre().equals("emisor")) {
				prop.setValor(String.valueOf(mensaje.getEmisor().getId()));
			} else if (prop.getNombre().equals("receptor")) {
				prop.setValor(String.valueOf(mensaje.getReceptor().getId()));
			} else if (prop.getNombre().equals("fechaHora")) {
				prop.setValor(dateTimeFormat.format(mensaje.getFechaHora()));
			} else if (prop.getNombre().equals("emoticono")) {
				prop.setValor(String.valueOf(mensaje.getEmoticono()));
			}
			servPersistencia.modificarPropiedad(prop);
		}

	}

	@Override
	public Mensaje recuperarMensaje(int id) {
		if (PoolDAO.getInstance().contains(id)) {
			return (Mensaje) PoolDAO.getInstance().getObject(id);
		}
		
		Entidad entMensaje = servPersistencia.recuperarEntidad(id);

		String texto = servPersistencia.recuperarPropiedadEntidad(entMensaje, TEXT_FIELD);
		int idEmisor = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(entMensaje, EMISOR_FIELD));
		int idReceptor = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(entMensaje, RECEPTOR_FIELD));
		LocalDateTime fechaHora = LocalDateTime
				.parse(servPersistencia.recuperarPropiedadEntidad(entMensaje, FECHAHORA_FIELD), dateTimeFormat);
		int emoticono = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(entMensaje, EMOTICONO_FIELD));

		Mensaje mensaje = new Mensaje(texto, null, null, fechaHora, emoticono);
		mensaje.setId(id);
		
		PoolDAO.getInstance().addObject(id, mensaje);
		
		Usuario emisor = DAOFactory.getInstance().getUsuarioDAO().recuperarUsuario(idEmisor);
		Contacto receptor = DAOFactory.getInstance().getContactoIndividualDAO().recuperarContactoIndividual(idReceptor);

		mensaje.setEmisor(emisor);
		mensaje.setReceptor(receptor);
		
		

		return mensaje;
	}

	@Override
	public List<Mensaje> recuperarAllMensajes() {
		return servPersistencia.recuperarEntidades(ENTITY_TYPE).stream()
	            .map(entidad -> recuperarMensaje(entidad.getId()))
	            .collect(Collectors.toCollection(LinkedList::new));
	}
	
	

}
