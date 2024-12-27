package umu.tds.dao;

import umu.tds.dao.tds.AdaptadorContactoIndividualTDS;
import umu.tds.dao.tds.AdaptadorGrupoTDS;
import umu.tds.dao.tds.AdaptadorMensajeTDS;
import umu.tds.dao.tds.AdaptadorUsuarioTDS;

public abstract class DAOFactory {

	/* Instancia Singleton */
	public static final String DAO_TDS = "umu.tds.dao.impl.DaoFactory";
	private static DAOFactory unicaInstancia = null;

	public static DAOFactory getInstance(String tipo) {
		if (unicaInstancia == null) {
			try {
				unicaInstancia = (DAOFactory) Class.forName(tipo).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return unicaInstancia;
	}

	public static DAOFactory getInstance() {
		return getInstance(DAOFactory.DAO_TDS);
	}

	public abstract AdaptadorUsuarioTDS getUsuarioDAO();

	public abstract AdaptadorMensajeTDS getMensajeDAO();

	public abstract AdaptadorGrupoTDS getGrupoDAO();

	public abstract AdaptadorContactoIndividualTDS getContactoIndividualDAO();

}
