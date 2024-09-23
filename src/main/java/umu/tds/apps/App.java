package umu.tds.apps;

public class App {
    public static void main(String[] args) {
    	try {
    		VentanaLogin login = new VentanaLogin();
			login.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
