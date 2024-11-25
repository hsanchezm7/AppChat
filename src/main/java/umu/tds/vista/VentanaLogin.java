package umu.tds.vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;

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
        panelWrapperForm.setLayout(new BorderLayout(0, 0));
        
        JPanel registerPanel = new JPanel();
        registerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelWrapperForm.add(registerPanel, BorderLayout.CENTER);
        GridBagLayout gbl_registerPanel = new GridBagLayout();
        gbl_registerPanel.columnWidths = new int[]{0, 0, 0};
        gbl_registerPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
        gbl_registerPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_registerPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};	
        registerPanel.setLayout(gbl_registerPanel);
        
        JLabel lblPhone = new JLabel("Phone: ");
        GridBagConstraints gbc_lblPhone = new GridBagConstraints();
        gbc_lblPhone.anchor = GridBagConstraints.EAST;
        gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
        gbc_lblPhone.gridx = 0;
        gbc_lblPhone.gridy = 0;
        registerPanel.add(lblPhone, gbc_lblPhone);
        lblPhone.setHorizontalAlignment(SwingConstants.RIGHT);
        
        phoneField = new JTextField(15);
        GridBagConstraints gbc_phoneField = new GridBagConstraints();
        gbc_phoneField.fill = GridBagConstraints.HORIZONTAL;
        gbc_phoneField.insets = new Insets(0, 0, 5, 0);
        gbc_phoneField.gridx = 1;
        gbc_phoneField.gridy = 0;
        registerPanel.add(phoneField, gbc_phoneField);
        
        JLabel lblPassword_1 = new JLabel("Password: ");
        GridBagConstraints gbc_lblPassword_1 = new GridBagConstraints();
        gbc_lblPassword_1.anchor = GridBagConstraints.EAST;
        gbc_lblPassword_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblPassword_1.gridx = 0;
        gbc_lblPassword_1.gridy = 1;
        registerPanel.add(lblPassword_1, gbc_lblPassword_1);
        lblPassword_1.setHorizontalAlignment(SwingConstants.RIGHT);
        
        passwordField = new JPasswordField();
        GridBagConstraints gbc_passwordField = new GridBagConstraints();
        gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
        gbc_passwordField.insets = new Insets(0, 0, 5, 0);
        gbc_passwordField.gridx = 1;
        gbc_passwordField.gridy = 1;
        registerPanel.add(passwordField, gbc_passwordField);
        passwordField.setColumns(15);
        
        JPanel panelBotones = crearPanelBotones();
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
        
        
        pack();
        setResizable(true);
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
