package umu.tds.app;

import javax.swing.UIManager;

import umu.tds.vista.VentanaLogin;
import umu.tds.vista.VentanaLoginWB;

public class App {
    public static void main(String[] args) {
    	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    		VentanaLogin login = new VentanaLogin();
			login.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
