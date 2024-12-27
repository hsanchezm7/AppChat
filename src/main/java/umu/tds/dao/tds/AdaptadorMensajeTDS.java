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
import umu.tds.dao.AdaptadorMensajeDAO;
import umu.tds.model.Contacto;
import umu.tds.model.ContactoIndividual;
import umu.tds.model.Grupo;


public class AdaptadorMensajeTDS implements AdaptadorMensajeDAO {
	
	private static AdaptadorMensajeTDS unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;
	private SimpleDateFormat dateFormat;
	
	private AdaptadorMensajeTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	public static AdaptadorMensajeTDS getUnicaInstancia() {
		
		if (unicaInstancia == null) {
			return new AdaptadorMensajeTDS();
		}
		else {
			return unicaInstancia;
		}
		
	}
	
	@Override
	public void registrarMensaje(Mensaje mensaje) {
		
		Entidad entMensaje;
		
		if (servPersistencia.recuperarEntidad(mensaje.getCodigo()) != null)
			return;
		
		// volver a registrar un usuario? debería ser persistente y estar ya registrado. lo hace el repoUsuarios llamando a su adaptador
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		adaptadorUsuario.registrarUsuario(mensaje.getEmisor());
		
		// lo mismo con el grupo
		AdaptadorContactoIndividualTDS adaptadorContactoIndividual = AdaptadorContactoIndividualTDS.getUnicaInstancia();
		AdaptadorGrupoTDS adaptadorGrupo = AdaptadorGrupoTDS.getUnicaInstancia();
		Contacto contacto = mensaje.getReceptor();
		if (contacto instanceof ContactoIndividual) {
			adaptadorContactoIndividual.registrarContactoIndividual((ContactoIndividual)contacto);
		}
		else if(contacto instanceof Grupo) {
			adaptadorGrupo.registrarGrupo((Grupo) contacto);
		}
		
		entMensaje = new Entidad();
		entMensaje.setNombre("mensaje");
		entMensaje.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("texto", mensaje.getTexto()),
				new Propiedad("emisor", String.valueOf(mensaje.getEmisor().getCodigo())),
				new Propiedad("receptor", String.valueOf(mensaje.getReceptor().getCodigo())),
				new Propiedad("fechaHora", dateFormat.format(mensaje.getFechaHora())),
				new Propiedad("emoticono", String.valueOf(mensaje.getEmoticono()))
		))
		);
		
		entMensaje = servPersistencia.registrarEntidad(entMensaje);
		mensaje.setCodigo(entMensaje.getId()); //mirar esto
		
	}
	
	@Override
	public void borrarMensaje(Mensaje mensaje) {
		
		Entidad entMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		servPersistencia.borrarEntidad(entMensaje);
		
	}
	
	@Override
	public void modificarMensaje(Mensaje mensaje) {

		Entidad entMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo()); // mirar esto

		for (Propiedad prop : entMensaje.getPropiedades()) {
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
	
	@Override
	public Mensaje recuperarMensaje(int codigo) {
		
		if ( PoolDAO.getUnicaInstancia().contiene(codigo)) {
			return PoolDAO.getObjeto(codigo);
		}
		
		String texto;
		Usuario emisor;
		String receptor;
		LocalDateTime fechaHora;
		int emoticono;
		
		Entidad entMensaje = servPersistencia.recuperarEntidad(codigo);
		
		texto = servPersistencia.recuperarPropiedadEntidad(entMensaje, "texto");
		fechaHora = LocalDateTime.parse(servPersistencia.recuperarPropiedadEntidad(entMensaje, "fechaHora"));
		emoticono = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(entMensaje, "emoticono"));
		
		Mensaje mensaje = new Mensaje(texto, fechaHora, emoticono);//
		mensaje.setCodigo(codigo);
		
		PoolDAO.addObjeto(codigo, mensaje);//
		
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		emisor = adaptadorUsuario.recuperarUsuario(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(entMensaje, "emisor")));
		
		int codigoReceptor = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(entMensaje, "receptor"));
		
		if(servPersistencia.recuperarEntidad(codigoReceptor).getNombre().equals("grupo")) {
			Grupo grupo = AdaptadorGrupoTDS.getUnicaInstancia().recuperarGrupo(codigoReceptor);
			mensaje.setReceptor(grupo);
		}
		else {
			ContactoIndividual contactoIndividual = AdaptadorContactoIndividualTDS.getUnicaInstancia().recuperarContactoIndividual(codigoReceptor);
			mensaje.setReceptor(contactoIndividual);
		}
		
		
		
		return mensaje;
		
		
	}
	
	@Override
	public List<Mensaje> recuperarTodosMensajes(){
		
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("mensaje");
		
		for (Entidad entMensaje : entidades) {
			mensajes.add(recuperarMensaje(entMensaje.getId()));
		}
		
		return mensajes;
	}

}
