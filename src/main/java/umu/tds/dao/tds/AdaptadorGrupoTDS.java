package umu.tds.dao.tds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dao.AdaptadorGrupoDAO;
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
		
		/* Asegurar MIEMBROS registrados */
		List<Usuario> miembros = grupo.getMiembros();
		
		AdaptadorUsuarioDAO adapterU = DAOFactory.getInstance().getUsuarioDAO();
		
		for (Usuario miembro : miembros)
			adapterU.registrarUsuario(miembro);
				
		/* TODO: REGISTRAR MENSAJES ASOCIADOS */
		
		Entidad entGrupo = new Entidad();
		entGrupo.setNombre(ENTITY_TYPE);

		entGrupo.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
		        new Propiedad(ADMIN_FIELD, String.valueOf(grupo.getAdministrador().getId())),
		        new Propiedad(MIEMBROS_FIELD, getIdsFromUsuarios(miembros)), // Convertir lista de miembros a IDs
		        new Propiedad(IMAGENURL_FIELD, grupo.getImagenGrupoURL()),
		        new Propiedad(NOMBRE_FIELD, grupo.getNombre()),
		        new Propiedad(MENSAJES_FIELD, getIdsFromMensajes(grupo.getMensajes())) // Convertir lista de mensajes a IDs
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
		
		String administradorId = servPersistencia.recuperarPropiedadEntidad(entGrupo, ADMIN_FIELD);
		Usuario administrador = DAOFactory.getInstance().getUsuarioDAO().recuperarUsuario(Integer.parseInt(administradorId));

		String miembrosIds = servPersistencia.recuperarPropiedadEntidad(entGrupo, MIEMBROS_FIELD);
		List<Usuario> miembros = getMembersFromConcatenatedIds(Arrays.stream(miembrosIds.split(","))
		        .map(Integer::parseInt)
		        .collect(Collectors.toList())); // Método auxiliar para obtener usuarios por sus IDs

		String imagenGrupoURL = servPersistencia.recuperarPropiedadEntidad(entGrupo, IMAGENURL_FIELD);
		String nombre = servPersistencia.recuperarPropiedadEntidad(entGrupo, NOMBRE_FIELD);

		String mensajesIds = servPersistencia.recuperarPropiedadEntidad(entGrupo, MENSAJES_FIELD);
		List<Mensaje> mensajes = obtenerMensajesPorIds(Arrays.stream(mensajesIds.split(","))
		        .map(Integer::parseInt)
		        .collect(Collectors.toList())); // Método auxiliar para obtener mensajes por sus IDs

		int id = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(entGrupo, ID_FIELD));

		// Crear el objeto Grupo con las propiedades recuperadas
		Grupo grupo = new Grupo(administrador, miembros, imagenGrupoURL, nombre, mensajes, id);

	}
	
	private String getIdsFromUsuarios(List<Usuario> usuarios) {
	    return usuarios.stream()
	            .map(usuario -> String.valueOf(usuario.getId()))
	            .collect(Collectors.joining(", "));
	}
	
	private String getIdsFromMensajes(List<Mensaje> mensajes) {
	    return mensajes.stream()
	            .map(mensaje -> String.valueOf(mensaje.getId()))
	            .collect(Collectors.joining(", "));
	}
	
	private List<Contacto> getUsersFromConcatenatedIds(String concatenatedIds) {
		if (concatenatedIds == null || concatenatedIds.trim().isEmpty()) {
	        return new ArrayList<>(); // Retorna una lista vacía si la cadena está vacía
	    }
		
		AdaptadorUsuarioDAO adapterU = DAOFactory.getInstance().getUsuarioDAO();

		/* TODO: ¿Esta función debe devolver Contactos, o Usuarios?  */
		
	    return Arrays.stream(concatenatedIds.split(", "))
	            .map(id -> adapterU.recuperarUsuario(Integer.parseInt(id.trim()))) // Asegura que no haya espacios en blanco
	            .collect(Collectors.toList());
	}


}
