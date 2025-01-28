package umu.tds.vista;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.border.EmptyBorder;

import umu.tds.controlador.AppChat;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class AñadirContactos extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField phoneField;

	public AñadirContactos(JFrame parent) {
		super(parent, "Añadir contacto", true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("Introduzca el nombre del contacto y su teléfono");
		lblNewLabel_2.setIcon(new ImageIcon(AñadirContactos.class.getResource("/umu/tds/resources/peligro-2.png")));
		panel.add(lblNewLabel_2, BorderLayout.WEST);
		
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.CENTER);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_4.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_4.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		JLabel lblNewLabel = new JLabel("Name");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		panel_4.add(lblNewLabel, gbc_lblNewLabel);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		panel_4.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Phone");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 2;
		panel_4.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		phoneField = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 2;
		panel_4.add(phoneField, gbc_textField_1);
		phoneField.setColumns(10);
		
		
		JPanel panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 2;
		gbc_panel_6.gridy = 3;
		panel_4.add(panel_6, gbc_panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_6.add(panel_7, BorderLayout.EAST);
		
		JButton btnNewButton_2 = new JButton("Add");
		panel_7.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String name = textField.getText().trim();
		        String phone = phoneField.getText().trim();

		        // Validación del campo de nombre
		        if (name.isEmpty()) {
		            JOptionPane.showMessageDialog(AñadirContactos.this, 
		                "El campo 'Name' no puede estar vacío.", 
		                "Advertencia", 
		                JOptionPane.WARNING_MESSAGE);
		            return; // Salir del método si no pasa la validación
		        }

		        // Validación del campo de teléfono
		        if (phone.isEmpty() || !phone.matches("\\d+")) {
		            JOptionPane.showMessageDialog(AñadirContactos.this, 
		                "El campo 'Phone' debe contener solo números y no puede estar vacío.", 
		                "Advertencia", 
		                JOptionPane.WARNING_MESSAGE);
		            return; // Salir del método si no pasa la validación
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

		
		JButton btnNewButton_3 = new JButton("Cancel");
		btnNewButton_3.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel_7.add(btnNewButton_3);
		
			
		setSize(400, 200); // Configura un tamaño fijo que permita mostrar todo
		setResizable(false);
		
		
	}
	

}
