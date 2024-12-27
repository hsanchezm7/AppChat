package umu.tds.dao.tds;

import beans.Entidad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dao.AdaptadorUsuarioDAO;
import umu.tds.model.Usuario;

public class AdaptadorUsuarioTDS implements AdaptadorUsuarioDAO {
	
	private static AdaptadorUsuarioTDS unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;
	
	private AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public static AdaptadorUsuarioTDS getUnicaInstancia() {
		
		if (unicaInstancia == null) {
			return new AdaptadorUsuarioTDS();
		}
		else {
			return unicaInstancia;
		}
		
	}
	
	public void registrarUsuario(Usuario usuario) {
		
		Entidad entUsuario;
		
		if (servPersistencia.recuperarEntidad(usuario.getCodigo()) != null)
			return;
		
	}
	
	public void borrarUsuario(Usuario usuario) {
		
		
		
	}
	
	public void modificarUsuario(Usuario usuario) {
		
		
		
	}
	
	public Usuario recuperarUsuario(int codigo) {
		
		
		
	}

}
