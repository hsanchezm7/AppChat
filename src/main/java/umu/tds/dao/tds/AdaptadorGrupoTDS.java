package umu.tds.dao.tds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private static final String ID_FIELD = "id";
	
	private static AdaptadorGrupoTDS unicaInstancia = null;
	
	private static ServicioPersistencia servPersistencia;
	
	private AdaptadorGrupoTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public static AdaptadorGrupoTDS getInstance() {
		
		if (unicaInstancia == null) {
			return new AdaptadorGrupoTDS();
		}
		else {
			return unicaInstancia;
		}
		
	}
	
	@Override
	public void registrarGrupo(Grupo grupo) {
		if (servPersistencia.recuperarEntidad(grupo.getId()) != null)
			return;
		
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

		entGrupo.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
		        new Propiedad(ADMIN_FIELD, String.valueOf(grupo.getAdministrador().getId())),
		        new Propiedad(MIEMBROS_FIELD, getIdsFromMembers(miembros)), // Convertir lista de miembros a IDs
		        new Propiedad(IMAGENURL_FIELD, grupo.getImagenGrupoURL()),
		        new Propiedad(NOMBRE_FIELD, grupo.getNombre()),
		        new Propiedad(MENSAJES_FIELD, getIdsFromMensajes(mensajes)) // Convertir lista de mensajes a IDs
		)));

		servPersistencia.registrarEntidad(entGrupo);

		grupo.setId(entGrupo.getId());		
	}
	
	@Override
	public void borrarGrupo(Grupo grupo) {
		
	}
	
	@Override
	public void modificarGrupo(Grupo grupo) {
		
	}
	
	@Override
	public Grupo recuperarGrupo(int id) {		
		Entidad entGrupo = servPersistencia.recuperarEntidad(id);
		
		String nombre = servPersistencia.recuperarPropiedadEntidad(entGrupo, NOMBRE_FIELD);
		
		AdaptadorUsuarioDAO adapterU = DAOFactory.getInstance().getUsuarioDAO();
		Usuario administrador = adapterU.recuperarUsuario(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(entGrupo, ADMIN_FIELD)));

		List<ContactoIndividual> miembros = getMembersFromConcatenatedIds(servPersistencia.recuperarPropiedadEntidad(entGrupo, MIEMBROS_FIELD));
		
		List<Mensaje> mensajes = getMessagesFromConcatenatedIds(servPersistencia.recuperarPropiedadEntidad(entGrupo, MENSAJES_FIELD));
		
		String imagenGrupoURL = servPersistencia.recuperarPropiedadEntidad(entGrupo, IMAGENURL_FIELD);
		
		/* Crear el objeto Grupo con las propiedades recuperadas */
		Grupo grupo = new Grupo(nombre, administrador, miembros, imagenGrupoURL);
		grupo.setMensajes(mensajes);
		grupo.setId(id);
		
		return grupo;
	}

	private String getIdsFromMembers(List<ContactoIndividual> miembros) {
	    return miembros.stream()
	            .map(miembro -> String.valueOf(miembro.getId()))
	            .collect(Collectors.joining(", "));
	}
	
	private String getIdsFromMensajes(List<Mensaje> mensajes) {
	    return mensajes.stream()
	            .map(mensaje -> String.valueOf(mensaje.getId()))
	            .collect(Collectors.joining(", "));
	}
	
	private List<ContactoIndividual> getMembersFromConcatenatedIds(String concatenatedIds) {
		if (concatenatedIds == null || concatenatedIds.trim().isEmpty()) {
	        return new ArrayList<>(); // Retorna una lista vacía si la cadena está vacía
	    }
		
		AdaptadorContactoIndividualDAO adapterCI = DAOFactory.getInstance().getContactoIndividualDAO();
		
	    return Arrays.stream(concatenatedIds.split(", "))
	            .map(id -> adapterCI.recuperarContactoIndividual(Integer.parseInt(id.trim()))) // Asegura que no haya espacios en blanco
	            .collect(Collectors.toList());
	}
	
	private List<Mensaje> getMessagesFromConcatenatedIds(String concatenatedIds) {
		if (concatenatedIds == null || concatenatedIds.trim().isEmpty()) {
	        return new ArrayList<>(); // Retorna una lista vacía si la cadena está vacía
	    }
		
		AdaptadorMensajeDAO adapterM = DAOFactory.getInstance().getMensajeDAO();
		
	    return Arrays.stream(concatenatedIds.split(", "))
	            .map(id -> adapterM.recuperarMensaje(Integer.parseInt(id.trim()))) // Asegura que no haya espacios en blanco
	            .collect(Collectors.toList());
	}
}
