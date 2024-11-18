package umu.tds.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import com.toedter.calendar.JDateChooser;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.ScrollPaneConstants;

public class VentanaRegister extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String NOMBRE_VENTANA = "Registrarse en AppChat";
	private JPanel registerPanel;
	private JLabel lblFirstName;
	private JTextField firstNameField;
	private JLabel lblLastName;
	private JTextField lastNameField;
	private JLabel lblPhone;
	private JTextField phoneField;
	private JLabel lblPassword;
	private JPasswordField passwordField;
	private JLabel lblContrasea_3;
	private JPasswordField confirmPasswordField;
	private JLabel lblDate;
	private JDateChooser dateChooser_1;
	private JLabel lblGreeting;
	private JScrollPane scrollPaneGreeting;
	private JLabel lblProfilePicture;
	private JPanel panelBtnCancel;
	private JButton btnCancel;
	private JButton btnConfirmRegister;
	private JPanel panelFormulario;
	private JPanel panelBotones;
	private JPanel panelBtnConfirmRegister;
	private JPanel panelLogo;
	private JLabel lblAppchat;
	private JPanel panelWrapperFormulario;
	private JTextArea txtGreeting;
	private JPanel panelGreeting;

	public VentanaRegister() {
		initComponents();
	}

	public void initComponents() {
		setTitle(NOMBRE_VENTANA);
		ImageIcon img = new ImageIcon("/umu/tds/resources/logo128x128.png");
		setIconImage(img.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		panelLogo = new JPanel();
		getContentPane().add(panelLogo, BorderLayout.NORTH);
		panelLogo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblAppchat = new JLabel("");
		lblAppchat.setIcon(new ImageIcon(VentanaRegister.class.getResource("/umu/tds/resources/logo128x128.png")));
		panelLogo.add(lblAppchat);
		
		panelFormulario = new JPanel();
		panelFormulario.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panelFormulario, BorderLayout.CENTER);
		panelFormulario.setLayout(new BorderLayout(0, 0));
		
		panelWrapperFormulario = new JPanel();
		panelWrapperFormulario.setBorder(new TitledBorder(null, "Create account", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(46, 52, 54)));
		panelFormulario.add(panelWrapperFormulario, BorderLayout.CENTER);
		panelWrapperFormulario.setLayout(new BorderLayout(0, 0));
		
		registerPanel = new JPanel();
		panelWrapperFormulario.add(registerPanel, BorderLayout.CENTER);
		registerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagLayout gbl_registerPanel = new GridBagLayout();
		gbl_registerPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_registerPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_registerPanel.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_registerPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		registerPanel.setLayout(gbl_registerPanel);
		
		lblPhone = new JLabel("Phone number");
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.anchor = GridBagConstraints.EAST;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone.gridx = 0;
		gbc_lblPhone.gridy = 0;
		registerPanel.add(lblPhone, gbc_lblPhone);
		
		phoneField = new JTextField();
		phoneField.setColumns(10);
		GridBagConstraints gbc_phoneField = new GridBagConstraints();
		gbc_phoneField.fill = GridBagConstraints.HORIZONTAL;
		gbc_phoneField.insets = new Insets(0, 0, 5, 5);
		gbc_phoneField.gridx = 1;
		gbc_phoneField.gridy = 0;
		registerPanel.add(phoneField, gbc_phoneField);
		
		lblFirstName = new JLabel("First name");
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.EAST;
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName.gridx = 2;
		gbc_lblFirstName.gridy = 0;
		registerPanel.add(lblFirstName, gbc_lblFirstName);
		
		firstNameField = new JTextField();
		firstNameField.setColumns(10);
		GridBagConstraints gbc_firstNameField = new GridBagConstraints();
		gbc_firstNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_firstNameField.insets = new Insets(0, 0, 5, 0);
		gbc_firstNameField.gridx = 3;
		gbc_firstNameField.gridy = 0;
		registerPanel.add(firstNameField, gbc_firstNameField);
		
		lblLastName = new JLabel("Last name");
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.EAST;
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 0;
		gbc_lblLastName.gridy = 1;
		registerPanel.add(lblLastName, gbc_lblLastName);
		
		lastNameField = new JTextField();
		lastNameField.setColumns(10);
		GridBagConstraints gbc_lastNameField = new GridBagConstraints();
		gbc_lastNameField.gridwidth = 3;
		gbc_lastNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_lastNameField.insets = new Insets(0, 0, 5, 0);
		gbc_lastNameField.gridx = 1;
		gbc_lastNameField.gridy = 1;
		registerPanel.add(lastNameField, gbc_lastNameField);
		
		lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 2;
		registerPanel.add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 2;
		registerPanel.add(passwordField, gbc_passwordField);
		
		lblContrasea_3 = new JLabel("Confirm password");
		GridBagConstraints gbc_lblContrasea_3 = new GridBagConstraints();
		gbc_lblContrasea_3.anchor = GridBagConstraints.EAST;
		gbc_lblContrasea_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea_3.gridx = 2;
		gbc_lblContrasea_3.gridy = 2;
		registerPanel.add(lblContrasea_3, gbc_lblContrasea_3);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setColumns(10);
		GridBagConstraints gbc_confirmPasswordField = new GridBagConstraints();
		gbc_confirmPasswordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_confirmPasswordField.insets = new Insets(0, 0, 5, 0);
		gbc_confirmPasswordField.gridx = 3;
		gbc_confirmPasswordField.gridy = 2;
		registerPanel.add(confirmPasswordField, gbc_confirmPasswordField);
		
		lblDate = new JLabel("Date");
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.anchor = GridBagConstraints.EAST;
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridx = 0;
		gbc_lblDate.gridy = 3;
		registerPanel.add(lblDate, gbc_lblDate);
		
		dateChooser_1 = new JDateChooser();
		GridBagConstraints gbc_dateChooser_1 = new GridBagConstraints();
		gbc_dateChooser_1.fill = GridBagConstraints.BOTH;
		gbc_dateChooser_1.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser_1.gridx = 1;
		gbc_dateChooser_1.gridy = 3;
		registerPanel.add(dateChooser_1, gbc_dateChooser_1);
		
		lblProfilePicture = new JLabel("Profile picture");
		GridBagConstraints gbc_lblProfilePicture = new GridBagConstraints();
		gbc_lblProfilePicture.anchor = GridBagConstraints.EAST;
		gbc_lblProfilePicture.insets = new Insets(0, 0, 5, 5);
		gbc_lblProfilePicture.gridx = 2;
		gbc_lblProfilePicture.gridy = 3;
		registerPanel.add(lblProfilePicture, gbc_lblProfilePicture);
		
				// Crear JTextArea y agregarlo al JScrollPane
				txtGreeting = new JTextArea();
				GridBagConstraints gbc_txtGreeting = new GridBagConstraints();
				gbc_txtGreeting.gridwidth = 3;
				gbc_txtGreeting.insets = new Insets(0, 0, 5, 0);
				gbc_txtGreeting.gridx = 1;
				gbc_txtGreeting.gridy = 4;
				registerPanel.add(txtGreeting, gbc_txtGreeting);
		
		lblGreeting = new JLabel("Greeting");
		GridBagConstraints gbc_lblGreeting = new GridBagConstraints();
		gbc_lblGreeting.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblGreeting.insets = new Insets(0, 0, 5, 5);
		gbc_lblGreeting.gridx = 0;
		gbc_lblGreeting.gridy = 5;
		registerPanel.add(lblGreeting, gbc_lblGreeting);
		
				// Crear JScrollPane y agregarlo al panel
				scrollPaneGreeting = new JScrollPane();
				GridBagConstraints gbc_scrollPaneGreeting = new GridBagConstraints();
				gbc_scrollPaneGreeting.gridwidth = 3;
				gbc_scrollPaneGreeting.insets = new Insets(0, 0, 5, 5);
				gbc_scrollPaneGreeting.gridx = 1;
				gbc_scrollPaneGreeting.gridy = 5;
				registerPanel.add(scrollPaneGreeting, gbc_scrollPaneGreeting);
				scrollPaneGreeting.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
				scrollPaneGreeting.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		
		panelGreeting = new JPanel();
		GridBagConstraints gbc_panelGreeting = new GridBagConstraints();
		gbc_panelGreeting.gridwidth = 3;
		gbc_panelGreeting.insets = new Insets(0, 0, 5, 0);
		gbc_panelGreeting.fill = GridBagConstraints.BOTH;
		gbc_panelGreeting.gridx = 1;
		gbc_panelGreeting.gridy = 6;
		registerPanel.add(panelGreeting, gbc_panelGreeting);
		panelGreeting.setLayout(new BorderLayout());

		// Configurar el JTextArea
		//txtrNk.setLineWrap(true); // Ajuste de líneas automático
		//txtrNk.setWrapStyleWord(true); // Ajusta líneas por palabras
		
		panelBotones = new JPanel();
		panelBotones.setBorder(new EmptyBorder(0, 10, 10, 10));
		getContentPane().add(panelBotones, BorderLayout.SOUTH);
		panelBotones.setLayout(new BorderLayout(0, 0));
		
		panelBtnCancel = new JPanel();
		panelBotones.add(panelBtnCancel, BorderLayout.WEST);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelBtnCancel.add(btnCancel);
		
		panelBtnConfirmRegister = new JPanel();
		panelBotones.add(panelBtnConfirmRegister, BorderLayout.EAST);
		
		btnConfirmRegister = new JButton("Confirm");
		btnConfirmRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelBtnConfirmRegister.add(btnConfirmRegister);
		
		pack();
		setResizable(true);
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
	}

}
