package umu.tds.vista;

import java.awt.EventQueue;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;

import javax.swing.border.TitledBorder;
import javax.swing.JScrollBar;

import java.util.ArrayList;
import java.util.List;
import umu.tds.model.ContactoIndividual;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import tds.BubbleText;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;


public class intentoVentanaMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private static ImageIcon ICON = new ImageIcon(intentoVentanaMain.class.getResource("/umu/tds/resources/logo128x128.png"));
	
	private JPanel contentPane;
	private JTextField txtUsuarioActual;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					intentoVentanaMain frame = new intentoVentanaMain();
					frame.setIconImage(ICON.getImage());
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
	public intentoVentanaMain() {
		this.setTitle("AppChat");
		this.setIconImage(ICON.getImage());
		
		DefaultListModel<ContactoIndividual> modelo = new DefaultListModel<>();
		modelo.addElement(new ContactoIndividual("Jose", "612345678", null));
		modelo.addElement(new ContactoIndividual("Ana", "623456789", null));
		modelo.addElement(new ContactoIndividual("Maria", "634567890", null));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 652, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		//Panel de arriba
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_3.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{209, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JComboBox comboBox = new JComboBox();
		//comboBox.setModel(new DefaultComboBoxModel(new String[] {"Contacto1", "Contacto2", "Contacto3", "Contacto4"}));
		for (int i = 0; i < modelo.size(); i++) {
		    ContactoIndividual contacto = modelo.getElementAt(i);  // Obtener el contacto
		    comboBox.addItem(contacto.getNombre());  // Añadir el nombre al JComboBox
		}
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 0;
		panel_1.add(comboBox, gbc_comboBox);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(intentoVentanaMain.class.getResource("/umu/tds/resources/enviar.png")));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 0;
		panel_1.add(btnNewButton, gbc_btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(intentoVentanaMain.class.getResource("/umu/tds/resources/analisis-de-busqueda.png")));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 0;
		panel_1.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Contactos");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setIcon(new ImageIcon(intentoVentanaMain.class.getResource("/umu/tds/resources/contacto-2.png")));
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 3;
		gbc_btnNewButton_2.gridy = 0;
		panel_1.add(btnNewButton_2, gbc_btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Premium");
		btnNewButton_3.setIcon(new ImageIcon(intentoVentanaMain.class.getResource("/umu/tds/resources/calidad-premium.png")));
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_3.gridx = 4;
		gbc_btnNewButton_3.gridy = 0;
		panel_1.add(btnNewButton_3, gbc_btnNewButton_3);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.EAST;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.VERTICAL;
		gbc_panel_2.gridx = 5;
		gbc_panel_2.gridy = 0;
		panel_1.add(panel_2, gbc_panel_2);
		
		JLabel lblNewLabel_1 = new JLabel("usuario actual");
		panel_2.add(lblNewLabel_1);
		
		JButton btnNewButton_5 = new JButton("");
		btnNewButton_5.setIcon(new ImageIcon(intentoVentanaMain.class.getResource("/umu/tds/resources/usuario.png")));
		panel_2.add(btnNewButton_5);
		
		//Cambiar para crear una ventana en la que se muestre la info del usuario y la posibilidad de cambiar la foto de perfil
		
		btnNewButton_5.addActionListener(e -> openRegister());
		
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.CENTER);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{303, 0, 0};
		gbl_panel_4.rowHeights = new int[]{0, 0};
		gbl_panel_4.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		//Panel izquierda (contactos)
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "mensajes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.insets = new Insets(0, 0, 0, 5);
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 0;
		panel_4.add(panel_6, gbc_panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		
		
		

		// Crear el JList basado en el modelo
		JList<ContactoIndividual> lista = new JList<>(modelo);
		lista.setCellRenderer(new ContactoIndividualCellRenderer());
		
		lista.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        // Obtener la celda en la que se hizo clic
		        int index = lista.locationToIndex(e.getPoint());
		        
		        // Verificar si el clic fue dentro del área del botón "+"
		        if (index != -1) {
		            ContactoIndividual persona = lista.getModel().getElementAt(index);
		            Rectangle cellBounds = lista.getCellBounds(index, index);
		            if (cellBounds.contains(e.getPoint())) {
		                // Verifica si el clic fue dentro del área del botón "+"
		                int x = e.getX();
		                int y = e.getY();

		                // Si el clic fue dentro del área del botón "+" (por ejemplo, en el lado derecho de la celda)
		                // Es posible ajustar esta área según el tamaño del botón.
		                if (x > cellBounds.x + 100) {  // Ajusta el valor según el tamaño del botón
		                    AñadirContactos ventanaContacto = new AñadirContactos((JFrame) SwingUtilities.getWindowAncestor(lista));
		                    ventanaContacto.setVisible(true);
		                }
		            }
		        }
		    }
		});
		
		JScrollPane scrollPane = new JScrollPane(lista);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_6.add(scrollPane, BorderLayout.CENTER);
        panel_6.setVisible(true);
        
		
		//Panel derecha (conversación)
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "mensajes con x", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 1;
		gbc_panel_5.gridy = 0;
		panel_4.add(panel_5, gbc_panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{0, 0, 0};
		gbl_panel_5.rowHeights = new int[]{0, 0, 0};
		gbl_panel_5.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		
		JPanel chat = new JPanel();
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.gridwidth = 2;
		gbc_panel_7.insets = new Insets(0, 0, 5, 5);
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 0;
		panel_5.add(chat, gbc_panel_7);
		chat.setLayout(new BoxLayout(chat,BoxLayout.Y_AXIS)); chat.setSize(400,700);
		chat.setMinimumSize(new Dimension(400,700)); chat.setMaximumSize(new Dimension(400,700)); chat.setPreferredSize(new Dimension(400,700));
		BubbleText burbuja;
		burbuja=new BubbleText(chat,"Hola grupo!!", Color.GREEN, "J.Ramón", BubbleText.SENT); chat.add(burbuja);
		
		BubbleText burbuja2; burbuja2=new BubbleText(chat,
				"Hola, ¿Está seguro de que la burbuja usa varias lineas si es necesario?",
				Color.LIGHT_GRAY, "Alumno", BubbleText.RECEIVED); chat.add(burbuja2);
		BubbleText burbuja3=new BubbleText(chat, 0, Color.GREEN, "J.Ramón", BubbleText.SENT,18); chat.add(burbuja3);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.anchor = GridBagConstraints.SOUTH;
		gbc_textField_1.insets = new Insets(0, 0, 0, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 0;
		gbc_textField_1.gridy = 1;
		panel_5.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.setIcon(new ImageIcon(intentoVentanaMain.class.getResource("/umu/tds/resources/send.png")));
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.anchor = GridBagConstraints.SOUTH;
		gbc_btnNewButton_4.gridx = 1;
		gbc_btnNewButton_4.gridy = 1;
		panel_5.add(btnNewButton_4, gbc_btnNewButton_4);
		
	
		pack();
		setResizable(true);
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
		
		
	}
	
	private void openRegister() {
		VentanaRegister ventanaRegister = new VentanaRegister(this);
		ventanaRegister.setVisible(true);
	}
	
	private class ContactoIndividualCellRenderer extends JPanel implements ListCellRenderer<ContactoIndividual> {
		private JLabel nameLabel;
		private JLabel imageLabel;
		private JButton btnPlus;
		private JTextField txtMensaje;

		public ContactoIndividualCellRenderer() {
			setLayout(new GridBagLayout());
	        setBorder(new LineBorder(Color.BLACK)); 
	        
			nameLabel = new JLabel();
			imageLabel = new JLabel();
			btnPlus = new JButton("+");
			txtMensaje = new JTextField("mensaje...");
			
			GridBagConstraints gbc = new GridBagConstraints();
			
			// Imagen (izquierda)
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        gbc.gridheight = 2; // Abarca dos filas
	        gbc.insets = new Insets(5, 5, 5, 5); // Margen
	        gbc.anchor = GridBagConstraints.WEST;
	        add(imageLabel, gbc);

	        // Botón "+" (arriba derecha)
	        gbc.gridx = 2;
	        gbc.gridy = 0;
	        gbc.gridheight = 1; // Solo una fila
	        gbc.anchor = GridBagConstraints.NORTHEAST;
	        add(btnPlus, gbc);

	        // Nombre (arriba, centro)
	        gbc.gridx = 1;
	        gbc.gridy = 0;
	        gbc.gridheight = 1;
	        gbc.fill = GridBagConstraints.HORIZONTAL;
	        gbc.weightx = 1.0;
	        gbc.anchor = GridBagConstraints.CENTER;
	        add(nameLabel, gbc);

	        // Cuadro de texto (abajo, centro)
	        gbc.gridx = 1;
	        gbc.gridy = 1;
	        gbc.gridheight = 1;
	        gbc.fill = GridBagConstraints.HORIZONTAL;
	        add(txtMensaje, gbc);
	        
	        
	        

		}

		@Override
		public Component getListCellRendererComponent(JList<? extends ContactoIndividual> list, ContactoIndividual persona, int index,
				boolean isSelected, boolean cellHasFocus) {
			// Set the text
			nameLabel.setText(persona.getNombre());

			// Load the image from a random URL (for example, using "https://robohash.org")
			try {
				URL imageUrl = new URL("https://robohash.org/" + persona.getNombre() + "?size=50x50");
				Image image = ImageIO.read(imageUrl);
				ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
				imageLabel.setIcon(imageIcon);
			} catch (IOException e) {
				e.printStackTrace();
				imageLabel.setIcon(null); // Default to no image if there was an issue
			}

			// Set background and foreground based on selection
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			return this;
		}
	}

}
