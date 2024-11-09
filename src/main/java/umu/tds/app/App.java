package umu.tds.app;

import umu.tds.vista.VentanaLoginWB;

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
