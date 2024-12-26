package umu.tds.dao.tds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

import umu.tds.model.Mensaje;
import umu.tds.model.Usuario;
import umu.tds.model.Contacto;
import umu.tds.model.ContactoIndividual;
import umu.tds.model.Grupo;


public class AdaptadorMensajeDAO implements umu.tds.dao.IAdaptadorMensajeDAO{
	
	private static AdaptadorMensajeDAO unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;
	private SimpleDateFormat dateFormat;
	
	private AdaptadorMensajeDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	public static AdaptadorMensajeDAO getUnicaInstancia() {
		
		if (unicaInstancia == null) {
			return new AdaptadorMensajeDAO();
		}
		else {
			return unicaInstancia;
		}
		
	}
	
	public void registrarMensaje(Mensaje mensaje) {
		
		Entidad eMensaje;
		boolean existe = true;
		try {
			eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		
		if (existe) {
			return;
		}
		
		AdaptadorUsuarioDAO adaptadorUsuario = AdaptadorUsuarioDAO.getUnicaInstancia();
		adaptadorUsuario.registrarUsuario(mensaje.getEmisor());
		
		AdaptadorContactoIndividual adaptadorContactoIndividual = AdaptadorContactoIndividual.getUnicaInstancia();
		AdaptadorGrupoDAO adaptadorGrupo = AdaptadorGrupoDAO.getUnicaInstancia();

		Contacto contacto = mensaje.getReceptor();
		if (contacto instanceof ContactoIndividual) {
			adaptadorContactoIndividual.registrarContactoIndividual((ContactoIndividual)contacto);
		}
		else if(contacto instanceof Grupo) {
			adaptadorGrupo.registrarGrupo((Grupo) contacto);
		}
		
		eMensaje = new Entidad();
		eMensaje.setNombre("mensaje");
		eMensaje.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("texto", mensaje.getTexto()),
				new Propiedad("emisor", String.valueOf(mensaje.getEmisor().getCodigo())),
				new Propiedad("receptor", String.valueOf(mensaje.getReceptor().getCodigo())),
				new Propiedad("fechaHora", dateFormat.format(mensaje.getFechaHora())),
				new Propiedad("emoticono", String.valueOf(mensaje.getEmoticono()))
		))
		);
		
		eMensaje = servPersistencia.registrarEntidad(eMensaje);
		mensaje.setCodigo(eMensaje.getId()); //mirar esto
		
	}
	
	public void borrarMensaje(Mensaje mensaje) {
		
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		servPersistencia.borrarEntidad(eMensaje);
		
	}
	
	public void modificarMensaje(Mensaje mensaje) {

		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo()); // mirar esto

		for (Propiedad prop : eMensaje.getPropiedades()) {
			if (prop.getNombre().equals("texto")) {
				prop.setValor(String.valueOf(mensaje.getTexto()));
			} else if (prop.getNombre().equals("emisor")) {
				prop.setValor(String.valueOf(mensaje.getEmisor().getCodigo()));
			} else if (prop.getNombre().equals("receptor")) {
				prop.setValor(String.valueOf(mensaje.getReceptor().getCodigo()));
			} else if (prop.getNombre().equals("fechaHora")) {
				prop.setValor(dateFormat.format(mensaje.getFechaHora()));
			} else if (prop.getNombre().equals("emoticono")) {
				prop.setValor(String.valueOf(mensaje.getEmoticono()));
			}
			servPersistencia.modificarPropiedad(prop);
		}

	}
	
	public Mensaje recuperarMensaje(int codigo) {
		
		if ( PoolDAO.getUnicaInstancia().contiene(codigo)) {
			return PoolDAO.getObjeto(codigo);
		}
		
		String texto;
		Usuario emisor;
		String receptor;
		LocalDateTime fechaHora;
		int emoticono;
		
		Entidad eMensaje = servPersistencia.recuperarEntidad(codigo);
		
		texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, "texto");
		fechaHora = LocalDateTime.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, "fechaHora"));
		emoticono = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "emoticono"));
		
		Mensaje mensaje = new Mensaje(texto, fechaHora, emoticono);//
		mensaje.setCodigo(codigo);
		
		PoolDAO.addObjeto(codigo, mensaje);//
		
		AdaptadorUsuarioDAO adaptadorUsuario = AdaptadorUsuarioDAO.getUnicaInstancia();
		emisor = adaptadorUsuario.recuperarUsuario(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "emisor")));
		
		int codigoReceptor = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "receptor"));
		
		if(servPersistencia.recuperarEntidad(codigoReceptor).getNombre().equals("grupo")) {
			Grupo grupo = AdaptadorGrupoDAO.getUnicaInstncia().recuperarGrupo(codigoReceptor);
			mensaje.setReceptor(grupo);
		}
		else {
			ContactoIndividual contactoIndividual = AdaptadorContactoIndividual.getUnicaInstancia().recuperarContactoIndividual(codigoReceptor);
			mensaje.setReceptor(contactoIndividual);
		}
		
		
		
		return mensaje;
		
		
	}
	
	public List<Mensaje> recuperarTodosMensajes(){
		
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("mensaje");
		
		for (Entidad eMensaje : entidades) {
			mensajes.add(recuperarMensaje(eMensaje.getId()));
		}
		
		return mensajes;
	}

}
