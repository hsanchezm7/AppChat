package umu.tds.dao.tds;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dao.AdaptadorUsuarioDAO;
import umu.tds.model.Usuario;

public class AdaptadorUsuarioTDS implements AdaptadorUsuarioDAO {

	private static final String ENTITY_TYPE = "Usuario";
	
	private static final String PHONE_FIELD = "phone";
	private static final String PASSWORD_FIELD = "password";
	private static final String NAME_FIELD = "name";
	private static final String BIRTH_FIELD = "fechaNacim";
	private static final String IMAGENURL_FIELD = "imagenURL";
	private static final String SALUDO_FIELD = "saludo";
	private static final String CONTACTOS_FIELD = "contactos";
	private static final String PREMIUM_FIELD = "premium";
	private static final String FECHAREG_FIELD = "fechaRegistro";

	private static AdaptadorUsuarioTDS unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;
	private DateTimeFormatter dateFormat;

	private AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	}

	public static AdaptadorUsuarioTDS getUnicaInstancia() {

		if (unicaInstancia == null) {
			return new AdaptadorUsuarioTDS();
		} else {
			return unicaInstancia;
		}

	}

	@Override
	public void registrarUsuario(Usuario usuario) {

		if (servPersistencia.recuperarEntidad(usuario.getId()) != null)
			return;

		Entidad entUsuario = new Entidad();
		entUsuario.setNombre(ENTITY_TYPE);

		entUsuario.setPropiedades(
				new ArrayList<Propiedad>(Arrays.asList(
						new Propiedad(PHONE_FIELD, usuario.getPhone()),
						new Propiedad(PASSWORD_FIELD, new String(usuario.getPassword())),
						new Propiedad(NAME_FIELD, usuario.getName()),
						new Propiedad(BIRTH_FIELD, usuario.getFechaNacimiento().format(dateFormat)),
						new Propiedad(IMAGENURL_FIELD, usuario.getImagenURL()),
						new Propiedad(SALUDO_FIELD, usuario.getSaludo()),
						// new Propiedad(CONTACTOS_FIELD, /* COMPLETAR parseContactsIds() */ ),
						new Propiedad(PREMIUM_FIELD, String.valueOf(usuario.isPremium())),
						new Propiedad(FECHAREG_FIELD, usuario.getFechaRegistro().format(dateFormat)))));
		
		servPersistencia.registrarEntidad(entUsuario);
		usuario.setId(entUsuario.getId());
		
	}

	@Override
	public void borrarUsuario(Usuario usuario) {
		/* TODO: BORRAR CONTACTOS ASOCIADOS */
		
		Entidad entUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		servPersistencia.borrarEntidad(entUsuario);
	}

	@Override
	public void modificarUsuario(Usuario usuario) {

	}
	
	@Override
	public List<Usuario> recuperarAllUsuarios() {
		List<Usuario> listaUsuarios = new LinkedList<Usuario>();
		List<Entidad> listaEntUsuarios = new ArrayList<Entidad>();
		listaEntUsuarios = servPersistencia.recuperarEntidades(ENTITY_TYPE);
		
		for (Entidad entUsuario : listaEntUsuarios) {
	        listaUsuarios.add(recuperarUsuario(entUsuario));
	    }
		
		return listaUsuarios;
	}

	@Override
	public Usuario recuperarUsuario(int id) {
		Entidad entUsuario = servPersistencia.recuperarEntidad(id);

		String phone = servPersistencia.recuperarPropiedadEntidad(entUsuario, PHONE_FIELD);
		char[] password = servPersistencia.recuperarPropiedadEntidad(entUsuario, PASSWORD_FIELD).toCharArray();
		String name = servPersistencia.recuperarPropiedadEntidad(entUsuario, NAME_FIELD);
		LocalDate birthDate = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(entUsuario, BIRTH_FIELD), dateFormat);
		String imagenURL = servPersistencia.recuperarPropiedadEntidad(entUsuario, IMAGENURL_FIELD);
		String saludo = servPersistencia.recuperarPropiedadEntidad(entUsuario, SALUDO_FIELD);
		boolean premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(entUsuario, PREMIUM_FIELD));
		LocalDate fechaReg = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(entUsuario, FECHAREG_FIELD), dateFormat);
		
		Usuario usuario = new Usuario(phone, password, name, birthDate, saludo, fechaReg);
		usuario.setPremium(premium);
		
		return usuario;
	}
	
	private Usuario recuperarUsuario(Entidad entUsuario) {
		String phone = servPersistencia.recuperarPropiedadEntidad(entUsuario, PHONE_FIELD);
		char[] password = servPersistencia.recuperarPropiedadEntidad(entUsuario, PASSWORD_FIELD).toCharArray();
		String name = servPersistencia.recuperarPropiedadEntidad(entUsuario, NAME_FIELD);
		LocalDate birthDate = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(entUsuario, BIRTH_FIELD), dateFormat);
		String imagenURL = servPersistencia.recuperarPropiedadEntidad(entUsuario, IMAGENURL_FIELD);
		String saludo = servPersistencia.recuperarPropiedadEntidad(entUsuario, SALUDO_FIELD);
		boolean premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(entUsuario, PREMIUM_FIELD));
		LocalDate fechaReg = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(entUsuario, FECHAREG_FIELD), dateFormat);
		
		Usuario usuario = new Usuario(phone, password, name, birthDate, saludo, fechaReg);
		usuario.setPremium(premium);
		
		return usuario;
	}
	
	// private String parseContactsIds() { }

}
