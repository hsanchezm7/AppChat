package umu.tds.vista;

import java.awt.EventQueue;

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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Rectangle;

public class VentanaRegister extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String NOMBRE_VENTANA = "Registrarse en AppChat";
	private JPanel panel;
	private JButton btnCancelar;
	private JButton btnAceptar;
	private Component horizontalGlue;
	private JPanel registerPanel;
	private JLabel lblNombre_1;
	private JTextField textField_3;
	private JLabel lblApellidos_1;
	private JTextField textField_4;
	private JLabel lblTelfono_1;
	private JTextField textField_5;
	private JLabel lblContrasea_2;
	private JPasswordField passwordField_2;
	private JLabel lblContrasea_3;
	private JPasswordField passwordField_3;
	private JLabel lblFecha_1;
	private JDateChooser dateChooser_1;
	private JLabel lblSaludo_1;
	private JScrollPane scrollPane_1;
	private JLabel lblImagen_1;
	private JPanel panel_2;
	private JButton btnCancel;
	private JButton btnRegister;
	private JPanel panelFormulario;
	private JPanel panelBotones;
	private JPanel panel_1;
	private JPanel panelLogo;
	private JLabel lblAppchat;
	private JPanel panelWrapperFormulario;
	private JTextArea txtrNk;
	private JPanel panel_3;

	public VentanaRegister() {
		initComponents();
	}

	/**
	 * Create the frame.
	 */
	public void initComponents() {
		setTitle(NOMBRE_VENTANA);
		setResizable(true);
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
		panelWrapperFormulario.setBorder(new TitledBorder(null, "Crear cuenta", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelFormulario.add(panelWrapperFormulario, BorderLayout.CENTER);
		panelWrapperFormulario.setLayout(new BorderLayout(0, 0));
		
		registerPanel = new JPanel();
		panelWrapperFormulario.add(registerPanel, BorderLayout.CENTER);
		registerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagLayout gbl_registerPanel = new GridBagLayout();
		gbl_registerPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_registerPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_registerPanel.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_registerPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		registerPanel.setLayout(gbl_registerPanel);
		
		lblTelfono_1 = new JLabel("Teléfono");
		GridBagConstraints gbc_lblTelfono_1 = new GridBagConstraints();
		gbc_lblTelfono_1.anchor = GridBagConstraints.EAST;
		gbc_lblTelfono_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelfono_1.gridx = 0;
		gbc_lblTelfono_1.gridy = 0;
		registerPanel.add(lblTelfono_1, gbc_lblTelfono_1);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.insets = new Insets(0, 0, 5, 5);
		gbc_textField_5.gridx = 1;
		gbc_textField_5.gridy = 0;
		registerPanel.add(textField_5, gbc_textField_5);
		
		lblNombre_1 = new JLabel("Nombre");
		GridBagConstraints gbc_lblNombre_1 = new GridBagConstraints();
		gbc_lblNombre_1.anchor = GridBagConstraints.EAST;
		gbc_lblNombre_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre_1.gridx = 2;
		gbc_lblNombre_1.gridy = 0;
		registerPanel.add(lblNombre_1, gbc_lblNombre_1);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 0;
		registerPanel.add(textField_3, gbc_textField_3);
		
		lblApellidos_1 = new JLabel("Apellidos");
		GridBagConstraints gbc_lblApellidos_1 = new GridBagConstraints();
		gbc_lblApellidos_1.anchor = GridBagConstraints.EAST;
		gbc_lblApellidos_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidos_1.gridx = 0;
		gbc_lblApellidos_1.gridy = 1;
		registerPanel.add(lblApellidos_1, gbc_lblApellidos_1);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.gridwidth = 3;
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.insets = new Insets(0, 0, 5, 0);
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 1;
		registerPanel.add(textField_4, gbc_textField_4);
		
		lblContrasea_2 = new JLabel("Contraseña");
		GridBagConstraints gbc_lblContrasea_2 = new GridBagConstraints();
		gbc_lblContrasea_2.anchor = GridBagConstraints.EAST;
		gbc_lblContrasea_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea_2.gridx = 0;
		gbc_lblContrasea_2.gridy = 3;
		registerPanel.add(lblContrasea_2, gbc_lblContrasea_2);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setColumns(10);
		GridBagConstraints gbc_passwordField_2 = new GridBagConstraints();
		gbc_passwordField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_2.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_2.gridx = 1;
		gbc_passwordField_2.gridy = 3;
		registerPanel.add(passwordField_2, gbc_passwordField_2);
		
		lblContrasea_3 = new JLabel("Confirmar contraseña");
		GridBagConstraints gbc_lblContrasea_3 = new GridBagConstraints();
		gbc_lblContrasea_3.anchor = GridBagConstraints.EAST;
		gbc_lblContrasea_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea_3.gridx = 2;
		gbc_lblContrasea_3.gridy = 3;
		registerPanel.add(lblContrasea_3, gbc_lblContrasea_3);
		
		passwordField_3 = new JPasswordField();
		passwordField_3.setColumns(10);
		GridBagConstraints gbc_passwordField_3 = new GridBagConstraints();
		gbc_passwordField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_3.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField_3.gridx = 3;
		gbc_passwordField_3.gridy = 3;
		registerPanel.add(passwordField_3, gbc_passwordField_3);
		
		lblFecha_1 = new JLabel("Fecha");
		GridBagConstraints gbc_lblFecha_1 = new GridBagConstraints();
		gbc_lblFecha_1.anchor = GridBagConstraints.EAST;
		gbc_lblFecha_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblFecha_1.gridx = 0;
		gbc_lblFecha_1.gridy = 5;
		registerPanel.add(lblFecha_1, gbc_lblFecha_1);
		
		dateChooser_1 = new JDateChooser();
		GridBagConstraints gbc_dateChooser_1 = new GridBagConstraints();
		gbc_dateChooser_1.fill = GridBagConstraints.BOTH;
		gbc_dateChooser_1.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser_1.gridx = 1;
		gbc_dateChooser_1.gridy = 5;
		registerPanel.add(dateChooser_1, gbc_dateChooser_1);
		
		lblImagen_1 = new JLabel("Imagen");
		GridBagConstraints gbc_lblImagen_1 = new GridBagConstraints();
		gbc_lblImagen_1.anchor = GridBagConstraints.EAST;
		gbc_lblImagen_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblImagen_1.gridx = 2;
		gbc_lblImagen_1.gridy = 5;
		registerPanel.add(lblImagen_1, gbc_lblImagen_1);
		
		lblSaludo_1 = new JLabel("Saludo");
		GridBagConstraints gbc_lblSaludo_1 = new GridBagConstraints();
		gbc_lblSaludo_1.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblSaludo_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSaludo_1.gridx = 0;
		gbc_lblSaludo_1.gridy = 7;
		registerPanel.add(lblSaludo_1, gbc_lblSaludo_1);
		
		panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 3;
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 7;
		registerPanel.add(panel_3, gbc_panel_3);
		
		scrollPane_1 = new JScrollPane();
		panel_3.add(scrollPane_1);
		
		txtrNk = new JTextArea();
		scrollPane_1.add(txtrNk);
		txtrNk.setLineWrap(true); // Ajuste de líneas automático
		txtrNk.setWrapStyleWord(true); // Ajusta líneas por palabras
		
		panelBotones = new JPanel();
		panelBotones.setBorder(new EmptyBorder(0, 10, 10, 10));
		getContentPane().add(panelBotones, BorderLayout.SOUTH);
		panelBotones.setLayout(new BorderLayout(0, 0));
		
		panel_2 = new JPanel();
		panelBotones.add(panel_2, BorderLayout.WEST);
		
		btnCancel = new JButton("Cancelar");
		btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(btnCancel);
		
		panel_1 = new JPanel();
		panelBotones.add(panel_1, BorderLayout.EAST);
		
		btnRegister = new JButton("Continuar");
		btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(btnRegister);
		
		pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
	}

}
