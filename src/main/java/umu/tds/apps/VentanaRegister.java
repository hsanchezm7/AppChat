package umu.tds.apps;

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
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.JTextArea;

public class VentanaRegister extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JDateChooser dateChooser;
	private JLabel lblSaludo;
	private JPanel panel;
	private JButton btnCancelar;
	private JButton btnAceptar;
	private Component horizontalGlue;
	private JScrollPane scrollPane;
	private JLabel lblImagen;
	private JPanel panel_1;
	private JButton btnCancelar_1;
	private JButton btnAceptar_1;
	private Component horizontalStrut;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRegister frame = new VentanaRegister();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaRegister() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 774, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 20, 0};
		gbl_contentPane.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 20, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNombre = new JLabel("Nombre");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		contentPane.add(lblNombre, gbc_lblNombre);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 3;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblApellidos = new JLabel("Apellidos");
		GridBagConstraints gbc_lblApellidos = new GridBagConstraints();
		gbc_lblApellidos.anchor = GridBagConstraints.EAST;
		gbc_lblApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidos.gridx = 1;
		gbc_lblApellidos.gridy = 2;
		contentPane.add(lblApellidos, gbc_lblApellidos);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 3;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 2;
		contentPane.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblTelfono = new JLabel("Teléfono");
		GridBagConstraints gbc_lblTelfono = new GridBagConstraints();
		gbc_lblTelfono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelfono.anchor = GridBagConstraints.EAST;
		gbc_lblTelfono.gridx = 1;
		gbc_lblTelfono.gridy = 3;
		contentPane.add(lblTelfono, gbc_lblTelfono);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 3;
		contentPane.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JLabel lblContrasea = new JLabel("Contraseña");
		GridBagConstraints gbc_lblContrasea = new GridBagConstraints();
		gbc_lblContrasea.anchor = GridBagConstraints.EAST;
		gbc_lblContrasea.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea.gridx = 1;
		gbc_lblContrasea.gridy = 4;
		contentPane.add(lblContrasea, gbc_lblContrasea);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 4;
		contentPane.add(passwordField, gbc_passwordField);
		
		JLabel lblContrasea_1 = new JLabel("Contraseña");
		GridBagConstraints gbc_lblContrasea_1 = new GridBagConstraints();
		gbc_lblContrasea_1.anchor = GridBagConstraints.EAST;
		gbc_lblContrasea_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea_1.gridx = 3;
		gbc_lblContrasea_1.gridy = 4;
		contentPane.add(lblContrasea_1, gbc_lblContrasea_1);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setColumns(10);
		GridBagConstraints gbc_passwordField_1 = new GridBagConstraints();
		gbc_passwordField_1.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_1.gridx = 4;
		gbc_passwordField_1.gridy = 4;
		contentPane.add(passwordField_1, gbc_passwordField_1);
		
		JLabel lblFecha = new JLabel("Fecha");
		GridBagConstraints gbc_lblFecha = new GridBagConstraints();
		gbc_lblFecha.anchor = GridBagConstraints.EAST;
		gbc_lblFecha.insets = new Insets(0, 0, 5, 5);
		gbc_lblFecha.gridx = 1;
		gbc_lblFecha.gridy = 5;
		contentPane.add(lblFecha, gbc_lblFecha);
		
		dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 2;
		gbc_dateChooser.gridy = 5;
		contentPane.add(dateChooser, gbc_dateChooser);
		
		lblSaludo = new JLabel("Saludo");
		GridBagConstraints gbc_lblSaludo = new GridBagConstraints();
		gbc_lblSaludo.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblSaludo.insets = new Insets(0, 0, 5, 5);
		gbc_lblSaludo.gridx = 1;
		gbc_lblSaludo.gridy = 6;
		contentPane.add(lblSaludo, gbc_lblSaludo);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 6;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		lblImagen = new JLabel("Imagen");
		GridBagConstraints gbc_lblImagen = new GridBagConstraints();
		gbc_lblImagen.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblImagen.insets = new Insets(0, 0, 5, 5);
		gbc_lblImagen.gridx = 3;
		gbc_lblImagen.gridy = 6;
		contentPane.add(lblImagen, gbc_lblImagen);
		
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 2;
		gbc_panel_1.gridy = 7;
		contentPane.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnCancelar_1 = new JButton("Cancelar");
		panel_1.add(btnCancelar_1);
		
		horizontalStrut = Box.createHorizontalStrut(30);
		panel_1.add(horizontalStrut);
		
		btnAceptar_1 = new JButton("Aceptar");
		panel_1.add(btnAceptar_1);
	}

}
