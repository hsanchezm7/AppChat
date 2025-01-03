package umu.tds.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import umu.tds.controlador.AppChat;

public class VentanaRegister extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final String NOMBRE_VENTANA = "Registrarse en AppChat";

	private JTextField phoneField;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JDateChooser fechaNacimChooser;
	private JTextArea greetingTextArea;

	private JScrollPane scrollPaneGreeting;
	private JLabel lblImageLabel;

	public VentanaRegister(JFrame owner) {
		super(owner, NOMBRE_VENTANA, true); // Bloquea la ventana padre hasta que ésta se cierre

		initComponents();
	}

	public void initComponents() {
		/* Window properties */
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		ImageIcon img = new ImageIcon("/umu/tds/resources/logo128x128.png");
		setIconImage(img.getImage());

		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelLogo = crearPanelLogo();
		getContentPane().add(panelLogo, BorderLayout.NORTH);

		JPanel panelCentro = crearPanelFormulario();
		getContentPane().add(panelCentro, BorderLayout.CENTER);

		JPanel panelBotones = crearPanelBotones();
		getContentPane().add(panelBotones, BorderLayout.SOUTH);

		pack();
		setResizable(true);
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
	}

	public JPanel crearPanelLogo() {
		JPanel panelLogo = new JPanel();
		getContentPane().add(panelLogo, BorderLayout.NORTH);
		panelLogo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblAppchat = new JLabel("");
		lblAppchat.setIcon(new ImageIcon(VentanaRegister.class.getResource("/umu/tds/resources/logo128x128.png")));
		panelLogo.add(lblAppchat);

		return panelLogo;
	}

	public JPanel crearPanelFormulario() {
		JPanel panelFormulario = new JPanel();
		panelFormulario.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelFormulario.setLayout(new BorderLayout(0, 0));

		JPanel panelWrapperFormulario = new JPanel();
		panelWrapperFormulario.setBorder(
				new TitledBorder(null, "  Create account  ", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelFormulario.add(panelWrapperFormulario, BorderLayout.CENTER);
		panelWrapperFormulario.setLayout(new BorderLayout(0, 0));

		JPanel registerPanel = new JPanel();
		panelWrapperFormulario.add(registerPanel, BorderLayout.CENTER);
		registerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagLayout gbl_registerPanel = new GridBagLayout();
		gbl_registerPanel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_registerPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_registerPanel.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_registerPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		registerPanel.setLayout(gbl_registerPanel);

		JLabel lblPhone = new JLabel("Phone number");
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.anchor = GridBagConstraints.EAST;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone.gridx = 0;
		gbc_lblPhone.gridy = 0;
		registerPanel.add(lblPhone, gbc_lblPhone);

		phoneField = new JTextField();
		phoneField.setColumns(15);
		GridBagConstraints gbc_phoneField = new GridBagConstraints();
		gbc_phoneField.fill = GridBagConstraints.HORIZONTAL;
		gbc_phoneField.insets = new Insets(0, 0, 5, 5);
		gbc_phoneField.gridx = 1;
		gbc_phoneField.gridy = 0;
		registerPanel.add(phoneField, gbc_phoneField);

		JLabel lblFirstName = new JLabel("First name");
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

		JLabel lblLastName = new JLabel("Last name");
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

		JLabel lblPassword = new JLabel("Password");
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

		JLabel lblConfirmPassword = new JLabel("Confirm password");
		GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
		gbc_lblConfirmPassword.anchor = GridBagConstraints.EAST;
		gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmPassword.gridx = 2;
		gbc_lblConfirmPassword.gridy = 2;
		registerPanel.add(lblConfirmPassword, gbc_lblConfirmPassword);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setColumns(10);
		GridBagConstraints gbc_confirmPasswordField = new GridBagConstraints();
		gbc_confirmPasswordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_confirmPasswordField.insets = new Insets(0, 0, 5, 0);
		gbc_confirmPasswordField.gridx = 3;
		gbc_confirmPasswordField.gridy = 2;
		registerPanel.add(confirmPasswordField, gbc_confirmPasswordField);

		JLabel lblDate = new JLabel("Date");
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.anchor = GridBagConstraints.EAST;
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridx = 0;
		gbc_lblDate.gridy = 3;
		registerPanel.add(lblDate, gbc_lblDate);

		fechaNacimChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser_1 = new GridBagConstraints();
		gbc_dateChooser_1.fill = GridBagConstraints.BOTH;
		gbc_dateChooser_1.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser_1.gridx = 1;
		gbc_dateChooser_1.gridy = 3;
		registerPanel.add(fechaNacimChooser, gbc_dateChooser_1);

		JLabel lblProfilePicture = new JLabel("Profile picture");
		GridBagConstraints gbc_lblProfilePicture = new GridBagConstraints();
		gbc_lblProfilePicture.anchor = GridBagConstraints.EAST;
		gbc_lblProfilePicture.insets = new Insets(0, 0, 5, 5);
		gbc_lblProfilePicture.gridx = 2;
		gbc_lblProfilePicture.gridy = 3;
		registerPanel.add(lblProfilePicture, gbc_lblProfilePicture);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 3;
		gbc_panel.gridy = 3;
		registerPanel.add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));

		JButton btnPictureGen = new JButton("Generate");
		panel.add(btnPictureGen, BorderLayout.CENTER);

		btnPictureGen.addActionListener(event -> {
			try {
				@SuppressWarnings("deprecation")
				URL imageUrl = new URL("https://robohash.org/" + firstNameField.getText() + "?size=50x50");
				Image image = ImageIO.read(imageUrl);
				ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
				lblImageLabel.setText("");
				lblImageLabel.setIcon(imageIcon);
				pack();
				setMinimumSize(getSize());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		JLabel lblImageLabel = new JLabel("No image");
		lblImageLabel.setFont(new Font("Tahoma", Font.ITALIC, 11));
		GridBagConstraints gbc_lblImageLabel = new GridBagConstraints();
		gbc_lblImageLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblImageLabel.gridx = 3;
		gbc_lblImageLabel.gridy = 4;
		registerPanel.add(lblImageLabel, gbc_lblImageLabel);

		JLabel lblGreeting = new JLabel("Greeting");
		GridBagConstraints gbc_lblGreeting = new GridBagConstraints();
		gbc_lblGreeting.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblGreeting.insets = new Insets(0, 0, 0, 5);
		gbc_lblGreeting.gridx = 0;
		gbc_lblGreeting.gridy = 5;
		registerPanel.add(lblGreeting, gbc_lblGreeting);

		JPanel panelGreeting = new JPanel();
		GridBagConstraints gbc_panelGreeting = new GridBagConstraints();
		gbc_panelGreeting.gridwidth = 3;
		gbc_panelGreeting.fill = GridBagConstraints.BOTH;
		gbc_panelGreeting.gridx = 1;
		gbc_panelGreeting.gridy = 5;
		registerPanel.add(panelGreeting, gbc_panelGreeting);
		panelGreeting.setLayout(new BorderLayout());

		greetingTextArea = new JTextArea("This is an editable JTextArea. "
				+ "A text area is a \"plain\" text component, " + "which means that although it can display text "
				+ "in any font, all of the text is in the same font.");
		greetingTextArea.setDragEnabled(true);
		greetingTextArea.setFont(new Font("Serif", Font.ITALIC, 16));
		greetingTextArea.setLineWrap(true);
		greetingTextArea.setWrapStyleWord(true);

		// Crear JScrollPane y agregarlo al panel
		scrollPaneGreeting = new JScrollPane(greetingTextArea);
		scrollPaneGreeting.setViewportBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelGreeting.add(scrollPaneGreeting);
		scrollPaneGreeting.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		return panelFormulario;
	}

	public JPanel crearPanelBotones() {
		JPanel panelBotones = new JPanel();
		panelBotones.setBorder(new EmptyBorder(0, 10, 10, 10));
		panelBotones.setLayout(new BorderLayout(0, 0));

		JPanel panelBtnCancel = new JPanel();
		panelBotones.add(panelBtnCancel, BorderLayout.WEST);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelBtnCancel.add(btnCancel);
		btnCancel.addActionListener(e -> handleCancel());

		JPanel panelBtnConfirmRegister = new JPanel();
		panelBotones.add(panelBtnConfirmRegister, BorderLayout.EAST);

		JButton btnConfirmRegister = new JButton("Confirm");
		btnConfirmRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelBtnConfirmRegister.add(btnConfirmRegister);
		btnConfirmRegister.addActionListener(e -> handleRegister());
		
		getRootPane().setDefaultButton(btnConfirmRegister);

		return panelBotones;
	}

	private void handleCancel() {
		int respuesta = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas cancelar el registro?",
				"Confirmar cancelación", JOptionPane.YES_NO_OPTION);

		if (respuesta == JOptionPane.YES_OPTION) {
			dispose();
		}
	}

	private void handleRegister() {
		if (!fieldsCheck())
			return;

		// Convertir Date a LocalDate
		Instant instant = fechaNacimChooser.getDate().toInstant();
		LocalDate fechaNacim = instant.atZone(ZoneId.systemDefault()).toLocalDate();

		boolean register = AppChat.getInstance().register(phoneField.getText(), firstNameField.getText(),
				lastNameField.getText(), passwordField.getPassword(), fechaNacim, "test.org",
				greetingTextArea.getText());

		if (register) {
			JOptionPane.showMessageDialog(this, "¡Registro completado! Ahora puedes iniciar sesión.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			this.dispose(); // Cerrar la ventana actual
		} else {
			JOptionPane.showMessageDialog(this, "No se ha podido crear la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean fieldsCheck() {
		if (phoneField.getText().isEmpty() || !phoneField.getText().matches("[0-9]+")) {
			JOptionPane.showMessageDialog(this, "El contenido del campo 'Phone number' no es válido.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// TODO: Comprobar que es una cadena válida
		if (firstNameField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "El contenido del campo 'First name' no es válido.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// TODO: Comprobar que es una cadena válida
		if (lastNameField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "El contenido del campo 'Last name' no es válido.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (passwordField.getPassword().length == 0) {
			JOptionPane.showMessageDialog(this, "El contenido del campo 'Contraseña' no es válido.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		String password = new String(passwordField.getPassword());
		String confirmPassword = new String(confirmPasswordField.getPassword());
		if (confirmPasswordField.getPassword().length == 0 || !confirmPassword.equals(password)) {
			JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (fechaNacimChooser.getDate() == null) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha válida.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

}
