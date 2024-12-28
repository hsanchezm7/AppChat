package umu.tds.dao;

public abstract class DAOFactory {

	/* Instancia Singleton */
	public static final String DAO_TDS = "umu.tds.dao.tds.DAOFactoryTDS";
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
		return getInstance(DAO_TDS);
	}

	public abstract AdaptadorUsuarioDAO getUsuarioDAO();

	public abstract AdaptadorMensajeDAO getMensajeDAO();

	public abstract AdaptadorGrupoDAO getGrupoDAO();

	public abstract AdaptadorContactoIndividualDAO getContactoIndividualDAO();

}
