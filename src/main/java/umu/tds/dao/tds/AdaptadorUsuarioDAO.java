package umu.tds.dao.tds;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

import umu.tds.model.Usuario;

public class AdaptadorUsuarioDAO implements umu.tds.dao.IAdaptadorUsuarioDAO {
	
	private static AdaptadorUsuarioDAO unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;
	
	private AdaptadorUsuarioDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public static AdaptadorUsuarioDAO getUnicaInstancia() {
		
		if (unicaInstancia == null) {
			return new AdaptadorUsuarioDAO();
		}
		else {
			return unicaInstancia;
		}
		
	}
	
	public void registrarUsuario(Usuario usuario) {
		
		
		
	}
	
	public void borrarUsuario(Usuario usuario) {
		
		
		
	}
	
	public void modificarUsuario(Usuario usuario) {
		
		
		
	}
	
	public Usuario recuperarUsuario(int codigo) {
		
		
		
	}

}
