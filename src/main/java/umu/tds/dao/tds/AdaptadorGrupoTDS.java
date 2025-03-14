package umu.tds.dao.tds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dao.AdaptadorContactoDAO;
import umu.tds.dao.AdaptadorContactoIndividualDAO;
import umu.tds.dao.AdaptadorGrupoDAO;
import umu.tds.dao.AdaptadorMensajeDAO;
import umu.tds.dao.AdaptadorUsuarioDAO;
import umu.tds.dao.DAOFactory;
import umu.tds.model.Contacto;
import umu.tds.model.ContactoIndividual;
import umu.tds.model.Grupo;
import umu.tds.model.Mensaje;
import umu.tds.model.Usuario;

public class AdaptadorGrupoTDS implements AdaptadorGrupoDAO {

	public static final String ENTITY_TYPE = "Grupo";

	private static final String ADMIN_FIELD = "administrador";
	private static final String MIEMBROS_FIELD = "miembros";
	private static final String IMAGENURL_FIELD = "imagenURL";
	private static final String NOMBRE_FIELD = "nombre";
	private static final String MENSAJES_FIELD = "mensajes";
	private static final String ID_FIELD = "id"; //Hace falta para algo??

	private static AdaptadorGrupoTDS unicaInstancia = null;

	private static ServicioPersistencia servPersistencia;

	private AdaptadorGrupoTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public static AdaptadorGrupoTDS getInstance() {

		if (unicaInstancia == null) {
			return new AdaptadorGrupoTDS();
		} else {
			return unicaInstancia;
		}

	}

	@Override
	public void registrarGrupo(Grupo grupo) {

		// Verificar si el grupo está ya registrado en el sistema
		if (servPersistencia.recuperarEntidad(grupo.getId()) != null) {
			System.out.println("El grupo ya estaba registrado"); // Para depurar
			return;
		}

		/* Asegurar ADMINISTRADOR registrado */
		AdaptadorUsuarioDAO adapterU = DAOFactory.getInstance().getUsuarioDAO();
		Usuario administrador = grupo.getAdministrador();
		adapterU.registrarUsuario(administrador);

		/* Asegurar MIEMBROS registrados */
		AdaptadorContactoIndividualDAO adapterCI = DAOFactory.getInstance().getContactoIndividualDAO();

		List<ContactoIndividual> miembros = grupo.getMiembros();

		for (ContactoIndividual miembro : miembros)
			adapterCI.registrarContactoIndividual(miembro);

		/* TODO: REGISTRAR MENSAJES ASOCIADOS */
		AdaptadorMensajeDAO adapterM = DAOFactory.getInstance().getMensajeDAO();

		// ¿Usar LinkedList<>?
		List<Mensaje> mensajes = grupo.getMensajes();

		for (Mensaje m : mensajes)
			adapterM.registrarMensaje(m);

		/* Crear y registrar entidad */
		Entidad entGrupo = new Entidad();
		entGrupo.setNombre(ENTITY_TYPE);

		entGrupo.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(ADMIN_FIELD, String.valueOf(grupo.getAdministrador().getId())),
						new Propiedad(MIEMBROS_FIELD, getIdsFromMembers(miembros)), // Convertir lista de miembros a IDs
						new Propiedad(IMAGENURL_FIELD, grupo.getImagenGrupoURL()),
						new Propiedad(NOMBRE_FIELD, grupo.getNombre()),
						new Propiedad(MENSAJES_FIELD, obtenerCodigosMensajes(mensajes)) // Convertir lista de mensajes a IDs
				)));

		entGrupo = servPersistencia.registrarEntidad(entGrupo);

		grupo.setId(entGrupo.getId());

		PoolDAO.getInstance().addObject(grupo.getId(), grupo);
	}

	@Override
	public void borrarGrupo(Grupo grupo) {

	}

	@Override
	public void modificarGrupo(Grupo grupo) {
		
		System.out.println("Modificando grupo con id " + grupo.getId());

		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getId());

		for (Propiedad prop : eGrupo.getPropiedades()) {
			if (prop.getNombre().equals("nombre")) {
				prop.setValor(grupo.getNombre());
			} else if (prop.getNombre().equals("miembros")) {
				prop.setValor(getIdsFromMembers(grupo.getMiembros()));
			} else if (prop.getNombre().equals("administrador")) {
				prop.setValor(String.valueOf(grupo.getAdministrador().getId()));
			} else if (prop.getNombre().equals("mensajes")) {
				String mensajes = obtenerCodigosMensajes(grupo.getMensajes());
				prop.setValor(mensajes);
			}
			servPersistencia.modificarPropiedad(prop);
		}
		
		System.out.println("La nueva lista de mensajes es ..." + obtenerCodigosMensajes(grupo.getMensajes()));

	}

	@Override
	public Grupo recuperarGrupo(int id) {
		if (PoolDAO.getInstance().contains(id)) {
			return (Grupo) PoolDAO.getInstance().getObject(id);
		}

		// Se recupera la entidad del grupo utilizando su id
		Entidad entGrupo = servPersistencia.recuperarEntidad(id);

		// Se recupera el nombre del grupo
		String nombre = servPersistencia.recuperarPropiedadEntidad(entGrupo, NOMBRE_FIELD);

		// Se recupera la imagen del grupo
		String imagenGrupoURL = servPersistencia.recuperarPropiedadEntidad(entGrupo, IMAGENURL_FIELD);

		/* Crear el objeto Grupo con las propiedades recuperadas */
		Grupo grupo = new Grupo(nombre, null, new LinkedList<ContactoIndividual>(), imagenGrupoURL);
		grupo.setId(id);

		PoolDAO.getInstance().addObject(id, grupo);

		// Se recupera el administrador del grupo
		AdaptadorUsuarioDAO adapterU = DAOFactory.getInstance().getUsuarioDAO();
		Usuario administrador = adapterU
				.recuperarUsuario(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(entGrupo, ADMIN_FIELD)));

		// Se recupera los miembros del grupo
		List<ContactoIndividual> miembros = getMembersFromConcatenatedIds(
				servPersistencia.recuperarPropiedadEntidad(entGrupo, MIEMBROS_FIELD));

		// Se recupera los mensajes del grupo
		List<Mensaje> mensajes = obtenerMensajesDesdeIds(
				servPersistencia.recuperarPropiedadEntidad(entGrupo, MENSAJES_FIELD));
		for (Mensaje message : mensajes) {
			grupo.addMensaje(message);
		}
		grupo.setAdministrador(administrador);
		grupo.setMensajes(mensajes);
		grupo.setMiembros(miembros);

		return grupo;
	}

	private String getIdsFromMembers(List<ContactoIndividual> miembros) {
		return miembros.stream().map(miembro -> String.valueOf(miembro.getId())).collect(Collectors.joining(" "));
	}

	private String obtenerCodigosMensajes(List<Mensaje> mensajes) {
		return mensajes.stream().map(mensaje -> String.valueOf(mensaje.getId())).collect(Collectors.joining(" "));
	}

	private List<ContactoIndividual> getMembersFromConcatenatedIds(String concatenatedIds) {
		List<ContactoIndividual> contactos = new LinkedList<>();
		StringTokenizer strTok = new StringTokenizer(concatenatedIds, " ");
		AdaptadorContactoIndividualTDS adaptadorCI = AdaptadorContactoIndividualTDS.getInstance();
		while (strTok.hasMoreTokens()) {
			contactos.add(adaptadorCI.recuperarContactoIndividual(Integer.valueOf((String) strTok.nextElement())));
		}
		return contactos;
	}

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
