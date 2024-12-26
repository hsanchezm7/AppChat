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

import umu.tds.model.ContactoIndividual;
import umu.tds.model.Mensaje;
import umu.tds.model.Usuario;



public class AdaptadorContactoIndividual implements umu.tds.dao.IAdaptadorContactoIndividualDAO{
	
	private static AdaptadorContactoIndividual unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;
	
	private AdaptadorContactoIndividual() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public static AdaptadorContactoIndividual getUnicaInstancia() {
		
		if (unicaInstancia == null) {
			return new AdaptadorContactoIndividual();
		}
		else {
			return unicaInstancia;
		}
		
	}
	
	public void registrarContactoIndividual(ContactoIndividual contactoIndividual) {
		
		Entidad eContactoIndividual;
		boolean existe = true;
		try {
			eContactoIndividual = servPersistencia.recuperarEntidad(contactoIndividual.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		
		if (existe) {
			return;
		}
		
		AdaptadorUsuarioDAO adaptadorUsuario = AdaptadorUsuarioDAO.getUnicaInstancia();
		AdaptadorMensajeDAO adaptadorMensaje = AdaptadorMensajeDAO.getUnicaInstancia();
		
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
		contactoIndividual.setCodigo(eContactoIndividual.getId()); //mirar esto
		
	}
	
	public void borrarContactoIndividual(ContactoIndividual contactoIndividual) {
		
		Entidad eContactoIndividual;
		eContactoIndividual = servPersistencia.recuperarEntidad(contactoIndividual.getCodigo()); //mirar esto
		
		AdaptadorMensajeDAO adaptadorMensaje = AdaptadorMensajeDAO.getUnicaInstancia();
		
		for (Mensaje mensaje : contactoIndividual.getMensajes()) {
			adaptadorMensaje.borrarMensaje(mensaje);
		}
		servPersistencia.borrarEntidad(eContactoIndividual);
		
	}
	
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
		
		AdaptadorUsuarioDAO adaptadorUsuario = AdaptadorUsuarioDAO.getUnicaInstancia();
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
		AdaptadorMensajeDAO adaptadorMensaje = AdaptadorMensajeDAO.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaMensajes.add(adaptadorMensaje.recuperarMensaje(
							Integer.valueOf((String) strTok.nextElement())));
		}
		return listaMensajes;
	}
	
}
