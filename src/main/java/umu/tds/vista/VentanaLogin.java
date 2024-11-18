package umu.tds.vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaLogin extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String NOMBRE_VENTANA = "Iniciar sesión en AppChat";
	private JTextField phoneField;
	private JPasswordField passwordField;
	
	public VentanaLogin() {
		initComponents();
	}
	
	public void initComponents() {
		
		/* Window properties */
		setTitle(NOMBRE_VENTANA);

		ImageIcon img = new ImageIcon("/umu/tds/resources/logo128x128.png");
		setIconImage(img.getImage());
		
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        JPanel panelLogo = crearPanelLogo();
        getContentPane().add(panelLogo, BorderLayout.NORTH);
        
        
        JPanel panelCentro = new JPanel();
        getContentPane().add(panelCentro, BorderLayout.CENTER);
        panelCentro.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelCentro.setLayout(new BorderLayout(0, 0));
        
        JPanel panelWrapperForm = new JPanel();
        panelWrapperForm.setBorder(new TitledBorder(null, "Login", TitledBorder.CENTER, TitledBorder.TOP, null, null));
        panelCentro.add(panelWrapperForm, BorderLayout.CENTER);
        panelWrapperForm.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        
        JPanel panelFormulario = crearPanelFormulario();
        panelWrapperForm.add(panelFormulario);
        
        JPanel panelBotones = crearPanelBotones();
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
        
        
        pack();
        setResizable(false);
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
	}
	
	public JPanel crearPanelLogo() {
		JPanel panelLogo = new JPanel();
		panelLogo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel lblAppchat = new JLabel("");
        lblAppchat.setIcon(new ImageIcon(VentanaLogin.class.getResource("/umu/tds/resources/logo128x128.png")));
        panelLogo.add(lblAppchat);
        
        return panelLogo;
	}
	
	public JPanel crearPanelFormulario() {
		JPanel panelFormulario = new JPanel();
        panelFormulario.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        
        JPanel panelPhone = new JPanel();
        panelFormulario.add(panelPhone);
        panelPhone.setLayout(new BorderLayout());
        
        JLabel lblPhone = new JLabel("Phone: ");
        lblPhone.setHorizontalAlignment(SwingConstants.RIGHT);
        panelPhone.add(lblPhone);
        
        phoneField = new JTextField(15);
        
        panelPhone.add(phoneField, BorderLayout.EAST);
        
        JPanel panelPassword = new JPanel();
        panelFormulario.add(panelPassword);
        panelPassword.setLayout(new BorderLayout());
        
        JLabel lblPassword = new JLabel("Password: ");
        lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
        panelPassword.add(lblPassword);
        
        passwordField = new JPasswordField();
        passwordField.setColumns(15);
        panelPassword.add(passwordField, BorderLayout.EAST);
        
        return panelFormulario;
	}
	
	
	public JPanel crearPanelBotones() {
		JPanel panelBotones = new JPanel();
        panelBotones.setBorder(new EmptyBorder(5, 10, 5, 10));
        panelBotones.setLayout(new BorderLayout(0, 0));
        
        JPanel panelBotonRegistro = new JPanel();
        panelBotones.add(panelBotonRegistro, BorderLayout.WEST);
        
        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(
                event -> {
                  VentanaRegister registerWindow = new VentanaRegister();
                  this.setVisible(false);
                  registerWindow.setVisible(true);
                });
        btnRegister.setVerticalAlignment(SwingConstants.BOTTOM);
        panelBotonRegistro.add(btnRegister);
        
        JPanel panelOtrosBotones = new JPanel();
        panelBotones.add(panelOtrosBotones, BorderLayout.EAST);
        
        JButton btnExit = new JButton("Exit");
        panelOtrosBotones.add(btnExit);
        btnExit.setVerticalAlignment(SwingConstants.BOTTOM);
        
        JButton btnLogin = new JButton("Login");
        btnLogin.setVerticalAlignment(SwingConstants.BOTTOM);
        panelOtrosBotones.add(btnLogin);
        
        return panelBotones;
	}
}
