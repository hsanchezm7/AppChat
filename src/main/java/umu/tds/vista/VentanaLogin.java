package umu.tds.vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaLogin extends JFrame {
	private static final long serialVersionUID = -7659074376023869511L;
	private static final String NOMBRE_VENTANA = "Iniciar sesión en AppChat";
	private JTextField phoneField;
	private JPasswordField passwordField;
	
	public VentanaLogin() {
		initComponents();
	}
	
	public void initComponents() {
		setTitle(NOMBRE_VENTANA);
		setResizable(false);
		ImageIcon img = new ImageIcon("/umu/tds/resources/logo128x128.png");
		setIconImage(img.getImage());
		
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        JPanel panelLogo = new JPanel();
        getContentPane().add(panelLogo, BorderLayout.NORTH);
        panelLogo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel lblAppchat = new JLabel("");
        lblAppchat.setIcon(new ImageIcon(VentanaLogin.class.getResource("/umu/tds/resources/logo128x128.png")));
        panelLogo.add(lblAppchat);
        
        JPanel panelCentro = new JPanel();
        getContentPane().add(panelCentro, BorderLayout.CENTER);
        panelCentro.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelCentro.setLayout(new BorderLayout(0, 0));
        
        JPanel panelWrapperDatos = new JPanel();
        panelWrapperDatos.setBorder(new TitledBorder(null, "Login", TitledBorder.CENTER, TitledBorder.TOP, null, null));
        panelCentro.add(panelWrapperDatos, BorderLayout.CENTER);
        panelWrapperDatos.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        
        JPanel panelDatos = new JPanel();
        panelWrapperDatos.add(panelDatos);
        panelDatos.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        
        JPanel panelPhone = new JPanel();
        panelDatos.add(panelPhone);
        panelPhone.setLayout(new BorderLayout());
        
        JLabel lblPhone = new JLabel("Phone: ");
        lblPhone.setHorizontalAlignment(SwingConstants.RIGHT);
        panelPhone.add(lblPhone);
        
        phoneField = new JTextField(15);
        
        panelPhone.add(phoneField, BorderLayout.EAST);
        
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
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
        panelBotones.setBorder(new EmptyBorder(5, 10, 5, 10));
        panelBotones.setLayout(new BorderLayout(0, 0));
        
        JPanel panelBotonRegistro = new JPanel();
        panelBotones.add(panelBotonRegistro, BorderLayout.WEST);
        
        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(
                event -> {
                  VentanaRegister registerWindow = new VentanaRegister();
                  registerWindow.setVisible(true);
                });
        btnRegister.setVerticalAlignment(SwingConstants.BOTTOM);
        panelBotonRegistro.add(btnRegister);
        
        JPanel panel = new JPanel();
        panelBotones.add(panel, BorderLayout.EAST);
        
        JButton btnExit = new JButton("Exit");
        panel.add(btnExit);
        btnExit.setVerticalAlignment(SwingConstants.BOTTOM);
        
        JButton btnLogin = new JButton("Login");
        btnLogin.setVerticalAlignment(SwingConstants.BOTTOM);
        panel.add(btnLogin);
        
        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
	}
}
