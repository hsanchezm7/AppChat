package umu.tds.dao.tds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dao.AdaptadorContactoIndividualDAO;
import umu.tds.dao.AdaptadorUsuarioDAO;
import umu.tds.dao.DAOFactory;
import umu.tds.model.ContactoIndividual;
import umu.tds.model.Mensaje;
import umu.tds.model.Usuario;

public class AdaptadorContactoIndividualTDS implements AdaptadorContactoIndividualDAO {

	public static final String ENTITY_TYPE = "ContactoIndividual";

	private static final String NAME_FIELD = "name";
	private static final String PHONE_FIELD = "phone";
	private static final String USER_FIELD = "user";
	private static final String MENSAJES_FIELD = "mensajes";
	private static final String MANUALLY_ADDED_FIELD = "anadidoManualmente";

	private static AdaptadorContactoIndividualTDS unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;

	private AdaptadorContactoIndividualTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public static AdaptadorContactoIndividualTDS getInstance() {

		if (unicaInstancia == null) {
			return new AdaptadorContactoIndividualTDS();
		} else {
			return unicaInstancia;
		}

	}

	@Override
	public void registrarContactoIndividual(ContactoIndividual contactoIndividual) {

		if (servPersistencia.recuperarEntidad(contactoIndividual.getId()) != null) {
			return;
		}

		AdaptadorUsuarioTDS adapterU = AdaptadorUsuarioTDS.getInstance();
		AdaptadorMensajeTDS adapterM = AdaptadorMensajeTDS.getUnicaInstancia();

		// Asegurar usuario y mensajes registrados

		// adapterU.registrarUsuario(contactoIndividual.getUsuario());

		for (Mensaje mensaje : contactoIndividual.getMensajes()) {
			adapterM.registrarMensaje(mensaje);
		}

		Entidad entContactoIndividual = new Entidad();
		entContactoIndividual.setNombre(ENTITY_TYPE);

		entContactoIndividual.setPropiedades(new ArrayList<>(Arrays.asList(
				new Propiedad(NAME_FIELD, contactoIndividual.getNombre()),
				new Propiedad(PHONE_FIELD, contactoIndividual.getMovil()),
				new Propiedad(USER_FIELD, String.valueOf(contactoIndividual.getUsuario().getId())),
				new Propiedad(MENSAJES_FIELD, obtenerCodigosMensajes(contactoIndividual.getMensajes())),
				new Propiedad(MANUALLY_ADDED_FIELD, String.valueOf(contactoIndividual.isAñadidoManualmente())))));

		entContactoIndividual = servPersistencia.registrarEntidad(entContactoIndividual);

		contactoIndividual.setId(entContactoIndividual.getId());

		PoolDAO.getInstance().addObject(contactoIndividual.getId(), contactoIndividual);
	}

	@Override
	public void borrarContactoIndividual(ContactoIndividual contactoIndividual) {

		Entidad eContactoIndividual = servPersistencia.recuperarEntidad(contactoIndividual.getId());

		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getUnicaInstancia();

		for (Mensaje mensaje : contactoIndividual.getMensajes()) {
			adaptadorMensaje.borrarMensaje(mensaje);
		}
		servPersistencia.borrarEntidad(eContactoIndividual);

	}

	@Override
	public void modificarContactoIndividual(ContactoIndividual contactoIndividual) {

		System.out.println("Modificando contacto con id " + contactoIndividual.getId());

		Entidad eContactoIndividual = servPersistencia.recuperarEntidad(contactoIndividual.getId());

		for (Propiedad prop : eContactoIndividual.getPropiedades()) {
			if (prop.getNombre().equals(NAME_FIELD)) {
				prop.setValor(String.valueOf(contactoIndividual.getNombre()));
			} else if (prop.getNombre().equals(PHONE_FIELD)) {
				prop.setValor(String.valueOf(contactoIndividual.getMovil()));
			} else if (prop.getNombre().equals(USER_FIELD)) {
				prop.setValor(String.valueOf(contactoIndividual.getUsuario().getId()));
			} else if (prop.getNombre().equals(MENSAJES_FIELD)) {
				String mensajes = obtenerCodigosMensajes(contactoIndividual.getMensajes());
				prop.setValor(mensajes);
			} else if (prop.getNombre().equals(MANUALLY_ADDED_FIELD)) {
				// Actualizar el valor del nuevo campo
				prop.setValor(String.valueOf(contactoIndividual.isAñadidoManualmente()));
			}
			servPersistencia.modificarPropiedad(prop);
		}

		System.out.println(
				"La nueva lista de mensajes es ..." + obtenerCodigosMensajes(contactoIndividual.getMensajes()));

	}

	@Override
	public ContactoIndividual recuperarContactoIndividual(int id) {
		System.out.println("Recuperando contacto " + id + "...");
		if (PoolDAO.getInstance().contains(id)) {
			return (ContactoIndividual) PoolDAO.getInstance().getObject(id);
		}

		Entidad entContactoIndividual = servPersistencia.recuperarEntidad(id);

		String name = servPersistencia.recuperarPropiedadEntidad(entContactoIndividual, NAME_FIELD);
		String phone = servPersistencia.recuperarPropiedadEntidad(entContactoIndividual, PHONE_FIELD);
		String userContactId = servPersistencia.recuperarPropiedadEntidad(entContactoIndividual, USER_FIELD);
		String anadidoManualmenteStr = servPersistencia.recuperarPropiedadEntidad(entContactoIndividual,
				MANUALLY_ADDED_FIELD);
		boolean anadidoManualmente = false;
		if (anadidoManualmenteStr != null && !anadidoManualmenteStr.isEmpty()) {
			anadidoManualmente = Boolean.parseBoolean(anadidoManualmenteStr);
		}

		ContactoIndividual contacto = new ContactoIndividual(name, phone, null, anadidoManualmente);
		contacto.setId(id);

		PoolDAO.getInstance().addObject(id, contacto);

		AdaptadorUsuarioDAO adapterU = DAOFactory.getInstance().getUsuarioDAO();
		Usuario userContact = adapterU.recuperarUsuario(Integer.valueOf(userContactId));
		System.out.println(
				"Intnetando recuperar usuario con ID: " + userContactId + " para el contacto " + contacto.getNombre());
		contacto.setUsuario(userContact);

		System.out.println("Recuperando mensajes para el contacto " + id + "...");
		// TODO: Recuperar mensajes
		List<Mensaje> mensajes = obtenerMensajesDesdeIds(
				servPersistencia.recuperarPropiedadEntidad(entContactoIndividual, MENSAJES_FIELD));
		for (Mensaje message : mensajes) {
			contacto.addMensaje(message);
		}

		return contacto;

	}

	// Método para obtener ids a partir de la lista de objetos al registrar

	private String obtenerCodigosMensajes(List<Mensaje> mensajes) {
		return mensajes.stream().map(mensaje -> String.valueOf(mensaje.getId())).collect(Collectors.joining(" "));
	}

	// Método para obtener lista de objetos a partir de un string con una lista de
	// ids al recuperar

	private List<Mensaje> obtenerMensajesDesdeIds(String mensajes) {
		System.out.println("Mensajes del campo mensajes: " + mensajes);
		List<Mensaje> listaMensajes = new LinkedList<>();
		StringTokenizer strTok = new StringTokenizer(mensajes, " ");
		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaMensajes.add(adaptadorMensaje.recuperarMensaje(Integer.valueOf((String) strTok.nextElement())));
		}
		return listaMensajes;
	}

}
