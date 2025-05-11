package umu.tds.vista;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import umu.tds.controlador.AppChat;
import umu.tds.model.ContactoIndividual;

public class AñadirContactos extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField phoneField;

	public AñadirContactos(Window owner) {
		super(owner);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panelMensaje = new JPanel();
		contentPane.add(panelMensaje, BorderLayout.NORTH);
		panelMensaje.setLayout(new BorderLayout(0, 0));

		JLabel Mensaje = new JLabel("Introduzca el nombre del contacto y su teléfono");
		Mensaje.setIcon(new ImageIcon(AñadirContactos.class.getResource("/umu/tds/resources/peligro-2.png")));
		panelMensaje.add(Mensaje, BorderLayout.WEST);

		JPanel panelMain = new JPanel();
		contentPane.add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panelMain.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.CENTER);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		JLabel NameLabel = new JLabel("Name");
		GridBagConstraints gbc_NameLabel = new GridBagConstraints();
		gbc_NameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_NameLabel.anchor = GridBagConstraints.EAST;
		gbc_NameLabel.gridx = 1;
		gbc_NameLabel.gridy = 1;
		panel_4.add(NameLabel, gbc_NameLabel);

		nameField = new JTextField();
		GridBagConstraints gbc_nameField = new GridBagConstraints();
		gbc_nameField.insets = new Insets(0, 0, 5, 0);
		gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameField.gridx = 2;
		gbc_nameField.gridy = 1;
		panel_4.add(nameField, gbc_nameField);
		nameField.setColumns(10);

		JLabel PhoneLabel = new JLabel("Phone");
		GridBagConstraints gbc_PhoneLabel = new GridBagConstraints();
		gbc_PhoneLabel.anchor = GridBagConstraints.EAST;
		gbc_PhoneLabel.insets = new Insets(0, 0, 5, 5);
		gbc_PhoneLabel.gridx = 1;
		gbc_PhoneLabel.gridy = 2;
		panel_4.add(PhoneLabel, gbc_PhoneLabel);

		phoneField = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 2;
		panel_4.add(phoneField, gbc_textField_1);
		phoneField.setColumns(10);

		JPanel panelBotones = new JPanel();
		GridBagConstraints gbc_panelBotones = new GridBagConstraints();
		gbc_panelBotones.fill = GridBagConstraints.BOTH;
		gbc_panelBotones.gridx = 2;
		gbc_panelBotones.gridy = 3;
		panel_4.add(panelBotones, gbc_panelBotones);
		panelBotones.setLayout(new BorderLayout(0, 0));

		JPanel panel_7 = new JPanel();
		panelBotones.add(panel_7, BorderLayout.EAST);

		JButton btnAdd = new JButton("Add");
		panel_7.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText().trim();
				String phone = phoneField.getText().trim();

				// Validación del campo de nombre
				if (name.isEmpty()) {
					JOptionPane.showMessageDialog(AñadirContactos.this, "El campo 'Name' no puede estar vacío.",
							"Advertencia", JOptionPane.WARNING_MESSAGE);
					return; // Salir del método si no pasa la validación
				}

				// Validación del campo de teléfono
				if (phone.isEmpty() || !phone.matches("\\d+")) {
					JOptionPane.showMessageDialog(AñadirContactos.this,
							"El campo 'Phone' debe contener solo números y no puede estar vacío.", "Advertencia",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (phone.equals(AppChat.getInstance().getCurrentUser().getPhone())) {
					JOptionPane.showMessageDialog(AñadirContactos.this, "No puedes añadirte como contacto.",
							"Advertencia", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (!AppChat.getInstance().isPhoneRegistered(phone)) {
					JOptionPane.showMessageDialog(AñadirContactos.this,
							"El teléfono introducido no está registrado en AppChat.", "Advertencia",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				boolean contactoExiste = AppChat.getInstance().getCurrentUser().getContactos().stream().anyMatch(
						contacto -> contacto.getNombre().equals(name) || (contacto instanceof ContactoIndividual
								&& ((ContactoIndividual) contacto).getMovil().equals(phone)));

				if (contactoExiste) {
					JOptionPane.showMessageDialog(AñadirContactos.this, "El contacto ya existe en la lista.",
							"Advertencia", JOptionPane.WARNING_MESSAGE);
					return;
				}

				boolean addContacto = AppChat.getInstance().addContacto(name, phone);

				if (addContacto) {
					// Si pasa las validaciones, mostrar un mensaje de éxito o realizar otra acción
					JOptionPane.showMessageDialog(AñadirContactos.this, "Contacto añadido correctamente.", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
				}
				dispose();
			}
		});

		getRootPane().setDefaultButton(btnAdd);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel_7.add(btnCancel);

		setSize(400, 200); // Configura un tamaño fijo que permita mostrar todo
		setResizable(false);

	}

}
