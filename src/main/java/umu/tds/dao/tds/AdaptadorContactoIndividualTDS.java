package umu.tds.dao.tds;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.LinkedList;
import java.util.StringTokenizer;

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
	private static final String MENSAJES_FIELD = "mensaje";

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

		if (servPersistencia.recuperarEntidad(contactoIndividual.getId()) != null)
			return;

		AdaptadorUsuarioTDS adapterU = AdaptadorUsuarioTDS.getInstance();
		AdaptadorMensajeTDS adapterM = AdaptadorMensajeTDS.getUnicaInstancia();

		// Asegurar usuario y mensajes registrados

		// adapterU.registrarUsuario(contactoIndividual.getUsuario());

		for (Mensaje mensaje : contactoIndividual.getMensajes()) {
			adapterM.registrarMensaje(mensaje);
		}

		Entidad entContactoIndividual = new Entidad();
		entContactoIndividual.setNombre(ENTITY_TYPE);

		entContactoIndividual.setPropiedades(
				new ArrayList<Propiedad>(Arrays.asList(new Propiedad(NAME_FIELD, contactoIndividual.getNombre()),
						new Propiedad(PHONE_FIELD, contactoIndividual.getMovil()),
						new Propiedad(USER_FIELD, String.valueOf(contactoIndividual.getUsuario().getId())),
						new Propiedad(MENSAJES_FIELD, obtenerCodigosMensajes(contactoIndividual.getMensajes())))));

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

		Entidad eContactoIndividual = servPersistencia.recuperarEntidad(contactoIndividual.getId());

		for (Propiedad prop : eContactoIndividual.getPropiedades()) {
			if (prop.getNombre().equals("nombre")) {
				prop.setValor(String.valueOf(contactoIndividual.getNombre()));
			} else if (prop.getNombre().equals("movil")) {
				prop.setValor(String.valueOf(contactoIndividual.getMovil()));
			} else if (prop.getNombre().equals("usuario")) {
				prop.setValor(String.valueOf(contactoIndividual.getId()));
			} else if (prop.getNombre().equals("mensajes")) {
				String mensajes = obtenerCodigosMensajes(contactoIndividual.getMensajes());
				prop.setValor(mensajes);
			}
			servPersistencia.modificarPropiedad(prop);
		}

	}

	@Override
	public ContactoIndividual recuperarContactoIndividual(int id) {
		if (PoolDAO.getInstance().contains(id)) {
			return (ContactoIndividual) PoolDAO.getInstance().getObject(id);
		}

		Entidad entContactoIndividual = servPersistencia.recuperarEntidad(id);

		AdaptadorUsuarioDAO adapterU = DAOFactory.getInstance().getUsuarioDAO();

		String name = servPersistencia.recuperarPropiedadEntidad(entContactoIndividual, NAME_FIELD);
		String phone = servPersistencia.recuperarPropiedadEntidad(entContactoIndividual, PHONE_FIELD);

		String userContactId = servPersistencia.recuperarPropiedadEntidad(entContactoIndividual, USER_FIELD);
		Usuario userContact = adapterU.recuperarUsuario(Integer.valueOf(userContactId));

		ContactoIndividual contacto = new ContactoIndividual(name, phone, userContact);
		contacto.setId(id);
		
		PoolDAO.getInstance().addObject(id, contacto);

		// TODO: Recuperar mensajes

		List<Mensaje> mensajes = obtenerMensajesDesdeIds(
				servPersistencia.recuperarPropiedadEntidad(entContactoIndividual, MENSAJES_FIELD));
		for (Mensaje message : mensajes) {
			contacto.addMensaje(message);
		}

		

		return contacto;

	}

	// Método para obtener ids a partir de la lista de objetos al registrar

	private String obtenerCodigosMensajes(List<Mensaje> listaMensajes) {
		return "";
	}

	// Método para obtener lista de objetos a partir de un string con una lista de
	// ids al recuperar

	private List<Mensaje> obtenerMensajesDesdeIds(String mensajes) {
		List<Mensaje> listaMensajes = new LinkedList<Mensaje>();
		StringTokenizer strTok = new StringTokenizer(mensajes, " ");
		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaMensajes.add(adaptadorMensaje.recuperarMensaje(Integer.valueOf((String) strTok.nextElement())));
		}
		return listaMensajes;
	}

}
