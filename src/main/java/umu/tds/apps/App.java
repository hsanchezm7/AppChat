package umu.tds.apps;

public class App {
    public static void main(String[] args) {
    	try {
    		VentanaLoginWB login = new VentanaLoginWB();
			login.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
