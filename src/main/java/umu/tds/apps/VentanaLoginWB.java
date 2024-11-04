package umu.tds.apps;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;

public class VentanaLoginWB extends JFrame {
	private static final long serialVersionUID = -7659074376023869511L;
	private static final String NOMBRE_VENTANA = "Iniciar sesión en AppChat";
	private static final int SIZE_X = 400;
	private static final int SIZE_Y = 400;
	private JPasswordField passwordField;
	private JTextField textField;
	
	public VentanaLoginWB() {
		initComponents();
	}
	
	public void initComponents() {
		setTitle(NOMBRE_VENTANA);
		setSize(SIZE_X, SIZE_Y);
		setResizable(false);
		
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.NORTH);
        
        JLabel lblAppchat = new JLabel("AppChat");
        panel.add(lblAppchat);
        
        JPanel panel_1 = new JPanel();
        getContentPane().add(panel_1, BorderLayout.CENTER);
        
        panel_1.setBorder(new TitledBorder(null, "Iniciar sesión", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JButton btnLogin = new JButton("login");
        panel_1.add(btnLogin);
        
        textField = new JTextField();
        panel_1.add(textField);
        textField.setColumns(10);
        
        passwordField = new JPasswordField();
        panel_1.add(passwordField);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
