package umu.tds.vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;

public class VentanaLogin extends JFrame {
	private static final long serialVersionUID = -7659074376023869511L;
	private static final String NOMBRE_VENTANA = "Iniciar sesión en AppChat";
	private static final int SIZE_X = 400;
	private static final int SIZE_Y = 400;
	private JTextField textField;
	private JPasswordField passwordField;
	
	public VentanaLogin() {
		initComponents();
	}
	
	public void initComponents() {
		setTitle(NOMBRE_VENTANA);
		setSize(SIZE_X, SIZE_Y);
		setResizable(false);
		
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        JPanel panelNorte = new JPanel();
        getContentPane().add(panelNorte, BorderLayout.NORTH);
        panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel lblAppchat = new JLabel("AppChat");
        panelNorte.add(lblAppchat);
        
        JPanel panelCentro = new JPanel();
        getContentPane().add(panelCentro, BorderLayout.CENTER);
        panelCentro.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelCentro.setLayout(new BorderLayout(0, 0));
        
        JPanel panelDatosWrapper = new JPanel();
        panelCentro.add(panelDatosWrapper, BorderLayout.NORTH);
        panelDatosWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JPanel panelDatos = new JPanel();
        panelDatosWrapper.add(panelDatos);
        panelDatos.setBorder(new TitledBorder(null, "Login", TitledBorder.LEADING, TitledBorder.TOP));
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        
        JPanel panelUsername = new JPanel();
        panelDatos.add(panelUsername);
        panelUsername.setLayout(new BorderLayout());
        
        JLabel lblUsername = new JLabel("Username: ");
        lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
        panelUsername.add(lblUsername);
        
        textField = new JTextField(15);
        
        panelUsername.add(textField, BorderLayout.EAST);
        
        JPanel panelPassword = new JPanel();
        panelDatos.add(panelPassword);
        panelPassword.setLayout(new BorderLayout());
        
        JLabel lblPassword = new JLabel("Password: ");
        lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
        panelPassword.add(lblPassword);
        
        passwordField = new JPasswordField();
        passwordField.setColumns(15);
        panelPassword.add(passwordField, BorderLayout.EAST);
        
        JPanel panelBotones = new JPanel();
        panelCentro.add(panelBotones, BorderLayout.SOUTH);
	}
}
