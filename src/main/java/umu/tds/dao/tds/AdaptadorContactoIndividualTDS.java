package umu.tds.dao.tds;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dao.AdaptadorContactoIndividualDAO;
import umu.tds.model.ContactoIndividual;
import umu.tds.model.Mensaje;
import umu.tds.model.Usuario;



public class AdaptadorContactoIndividualTDS implements AdaptadorContactoIndividualDAO {
	
	private static AdaptadorContactoIndividualTDS unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;
	
	private AdaptadorContactoIndividualTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public static AdaptadorContactoIndividualTDS getUnicaInstancia() {
		
		if (unicaInstancia == null) {
			return new AdaptadorContactoIndividualTDS();
		}
		else {
			return unicaInstancia;
		}
		
	}
	
	@Override
	public void registrarContactoIndividual(ContactoIndividual contactoIndividual) {
		
		Entidad entContactoIndividual;
		
		if (servPersistencia.recuperarEntidad(contactoIndividual.getCodigo()) != null)
			return;
		
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getUnicaInstancia();
		
		adaptadorUsuario.registrarUsuario(contactoIndividual.getUsuario());
		for (Mensaje mensaje : contactoIndividual.getMensajes()) {
			adaptadorMensaje.registrarMensaje(mensaje);
		}
		
		eContactoIndividual = new Entidad();
		eContactoIndividual.setNombre("contactoIndividual");
		eContactoIndividual.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("nombre", contactoIndividual.getNombre()),
				new Propiedad("movil", contactoIndividual.getMovil()),
				new Propiedad("usuario", contactoIndividual.getCodigo()),
				new Propiedad("mensajes", contactoIndividual.getMensajes())
		))
		);
		
		eContactoIndividual = servPersistencia.registrarEntidad(eContactoIndividual);
		contactoIndividual.setId(eContactoIndividual.getId()); //mirar esto
		
	}
	
	@Override
	public void borrarContactoIndividual(ContactoIndividual contactoIndividual) {
		
		Entidad eContactoIndividual;
		eContactoIndividual = servPersistencia.recuperarEntidad(contactoIndividual.getCodigo()); //mirar esto
		
		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getUnicaInstancia();
		
		for (Mensaje mensaje : contactoIndividual.getMensajes()) {
			adaptadorMensaje.borrarMensaje(mensaje);
		}
		servPersistencia.borrarEntidad(eContactoIndividual);
		
	}
	
	@Override
	public void modificarContactoIndividual(ContactoIndividual contactoIndividual) {
	
		Entidad eContactoIndividual = servPersistencia.recuperarEntidad(contactoIndividual.getCodigo()); //mirar esto
		
		for (Propiedad prop : eContactoIndividual.getPropiedades()) {
			if(prop.getNombre().equals("nombre")) {
				prop.setValor(String.valueOf(contactoIndividual.getNombre()));
			}
			else if (prop.getNombre().equals("movil")) {
				prop.setValor(String.valueOf(contactoIndividual.getMovil()));
			}
			else if (prop.getNombre().equals("usuario")) {
				prop.setValor(String.valueOf(contactoIndividual.getCodigo()));
			}
			else if (prop.getNombre().equals("mensajes")){
				String mensajes = obtenerCodigosMensajes(contactoIndividual.getMensajes());
				prop.setValor(mensajes);
			}
			servPersistencia.modificarPropiedad(prop);
		}
		
	}
	
	@Override
	public ContactoIndividual recuperarContactoIndividual(int codigo) {
		
		if ( PoolDAO.getUnicaInstancia().contiene(codigo)) {
			return PoolDAO.getObjeto(codigo);
		}
		
		String nombre;
		String movil;
		Usuario usuario;
		List<Mensaje> mensajes;
		
		Entidad eContactoIndividual = servPersistencia.recuperarEntidad(codigo);
		nombre = servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, "nombre");
		movil = servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, "movil");
		
		ContactoIndividual contactoIndividual = new ContactoIndividual(nombre, movil);
		contactoIndividual.setCodigo(codigo);
		
		PoolDAO.addObjeto(codigo, contactoIndividual);
		
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		int idUsuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, "usuario"));
		
		usuario = adaptadorUsuario.recuperarUsuario(idUsuario);
		contactoIndividual.setUsuario(usuario);
		
		mensajes =  obtenerMensajesDesdeIds(servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, "mensajes"));
		for (Mensaje mensaje : mensajes) {
			contactoIndividual.addMensaje(mensaje);
		}
		
		return contactoIndividual;
		
	}
	
	
	//Método para obtener ids a partir de la lista de objetos al registrar
	
	private String obtenerCodigosMensajes(List<Mensaje> listaMensajes) {
		// líneas de venta ya tienen el código dado por el servicio de persistencia
		String lineas = "";
		for (Mensaje mensaje: listaMensajes)
				lineas += mensaje.getCodigo() + " ";
		return lineas.trim();
		}
	
	//Método para obtener lista de objetos a partir de un string con una lista de ids al recuperar
	
	private List<Mensaje> obtenerMensajesDesdeIds(String mensajes) {
		List<Mensaje> listaMensajes = new LinkedList<Mensaje>();
		StringTokenizer strTok = new StringTokenizer(mensajes, " ");
		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaMensajes.add(adaptadorMensaje.recuperarMensaje(
							Integer.valueOf((String) strTok.nextElement())));
		}
		return listaMensajes;
	}
	
}
