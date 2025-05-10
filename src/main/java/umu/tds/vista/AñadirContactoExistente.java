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
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import umu.tds.model.ContactoIndividual;

public class AñadirContactoExistente extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JLabel phoneValueLabel;
	private String phone; // Almacena el número de teléfono del contacto

	/**
	 * Crea un diálogo para añadir un contacto existente del que ya se conoce el número.
	 * Permite asignar un nombre personalizado al contacto y marcarlo como añadido manualmente.
	 * @param owner El frame propietario
	 * @param phone El número de teléfono del contacto a añadir
	 */
	public AñadirContactoExistente(Window owner, String phone) {
		super(owner);
		this.phone = phone;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelMensaje = new JPanel();
		contentPane.add(panelMensaje, BorderLayout.NORTH);
		panelMensaje.setLayout(new BorderLayout(0, 0));
		
		JLabel Mensaje = new JLabel("Asigne un nombre para este contacto");
		Mensaje.setIcon(new ImageIcon(AñadirContactoExistente.class.getResource("/umu/tds/resources/peligro-2.png")));
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
		gbl_panel_4.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_4.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_4.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		
		phoneValueLabel = new JLabel(phone); // Se muestra el número de teléfono como texto
		GridBagConstraints gbc_phoneValueLabel = new GridBagConstraints();
		gbc_phoneValueLabel.insets = new Insets(0, 0, 5, 0);
		gbc_phoneValueLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_phoneValueLabel.gridx = 2;
		gbc_phoneValueLabel.gridy = 2;
		panel_4.add(phoneValueLabel, gbc_phoneValueLabel);
		
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
		    public void actionPerformed(ActionEvent e) {
		        String name = nameField.getText().trim();

		        // Validación del campo de nombre
		        if (name.isEmpty()) {
		            JOptionPane.showMessageDialog(AñadirContactoExistente.this, 
		                "El campo 'Name' no puede estar vacío.", 
		                "Advertencia", 
		                JOptionPane.WARNING_MESSAGE);
		            return; // Salir del método si no pasa la validación
		        }
		        
		        if(phone.equals(AppChat.getInstance().getCurrentUser().getPhone())) {
		            JOptionPane.showMessageDialog(AñadirContactoExistente.this, 
		                "No puedes añadirte como contacto.", 
		                "Advertencia", 
		                JOptionPane.WARNING_MESSAGE);
		            return; 
		        }
		        
		        // Buscar si el contacto ya existe en la lista
		        ContactoIndividual contactoExistente = AppChat.getInstance().getCurrentUser().getContactos().stream()
		                .filter(c -> c instanceof ContactoIndividual)
		                .map(c -> (ContactoIndividual) c)
		                .filter(c -> c.getMovil().equals(phone))
		                .findFirst()
		                .orElse(null);
		        
		        boolean operacionExitosa;
		        
		        if (contactoExistente != null) {
		            // Si el contacto ya existe, actualizar su nombre y marcarlo como añadido manualmente
		            operacionExitosa = AppChat.getInstance().actualizarContacto(contactoExistente, name);
		            
		            if (operacionExitosa) {
		                JOptionPane.showMessageDialog(AñadirContactoExistente.this, 
		                    "Contacto actualizado correctamente.", 
		                    "Éxito",
		                    JOptionPane.INFORMATION_MESSAGE);
		            }
		        } else {
		            // Si el contacto no existe, añadirlo normalmente
		            operacionExitosa = AppChat.getInstance().addContacto(name, phone);
		            
		            if (operacionExitosa) {
		                JOptionPane.showMessageDialog(AñadirContactoExistente.this, 
		                    "Contacto añadido correctamente.", 
		                    "Éxito",
		                    JOptionPane.INFORMATION_MESSAGE);
		            }
		        }
		        
		        dispose();
		    }
		});

		getRootPane().setDefaultButton(btnAdd);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel_7.add(btnCancel);
		
		setSize(400, 200); // Configura un tamaño fijo que permita mostrar todo
		setResizable(false);
		
		// Posicionamiento centrado respecto a la ventana padre
		setLocationRelativeTo(owner);
	}
}