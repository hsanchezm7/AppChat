package umu.tds.vista;

import java.awt.EventQueue;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.LineBorder;
import java.awt.Color;

import javax.swing.border.TitledBorder;

import java.util.ArrayList;
import java.util.List;

import umu.tds.controlador.AppChat;
import umu.tds.model.Contacto;
import umu.tds.model.ContactoIndividual;
import umu.tds.model.Mensaje;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import tds.BubbleText;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;


public class VentanaMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private static ImageIcon ICON = new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/logo128x128.png"));
	
	private JPanel contentPane;
	private JTextField txtUsuarioActual;
	private JTextField textField;
	private JTextField textField_1;
	private List<Contacto> contactos;
	private DefaultListModel<Contacto> modeloSeleccionado = new DefaultListModel<>();
	private JList<Contacto> listaContactos;
	private JPanel panelChat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMain frame = new VentanaMain();
					frame.setIconImage(ICON.getImage());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public VentanaMain() {
		initComponents();
	}
	
	public void initComponents(){
	
		setTitle("AppChat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setIconImage(ICON.getImage());
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		//Devuelvo los contactos del usuario actual
		contactos = AppChat.getInstance().getCurrentUser().getContactos();
		//Los añado al modelo
		contactos.stream().forEach(c -> modeloSeleccionado.addElement(c));
		
		listaContactos = new JList<>(modeloSeleccionado);
		
		
		//quitar cuando funcione
		DefaultListModel<ContactoIndividual> modelo = new DefaultListModel<>();
		modelo.addElement(new ContactoIndividual("Jose", "612345678", null));
		modelo.addElement(new ContactoIndividual("Ana", "623456789", null));
		modelo.addElement(new ContactoIndividual("Maria", "634567890", null));
		
		
		JPanel panelNorte = crearPanelNorte(modeloSeleccionado);
		getContentPane().add(panelNorte, BorderLayout.NORTH);
		
		JPanel panelMain = crearPanelMain(modeloSeleccionado);
		getContentPane().add(panelMain, BorderLayout.CENTER);
		
		pack();
		setResizable(true);
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
	}
	
	public JPanel crearPanelNorte(DefaultListModel<Contacto> modeloSeleccionado2) {
	
		JPanel panelNorte = new JPanel();
		panelNorte.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagLayout gbl1 = new GridBagLayout();
		gbl1.columnWidths = new int[]{209, 0, 0, 0, 0, 0, 0};
		gbl1.rowHeights = new int[]{0, 0, 0};
		gbl1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl1.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panelNorte.setLayout(gbl1);
		
		JComboBox comboBox = new JComboBox();
		for (int i = 0; i < modeloSeleccionado2.size(); i++) {
		    Contacto contacto = modeloSeleccionado2.getElementAt(i);  // Obtener el contacto
		    comboBox.addItem(contacto.getNombre());  // Añadir el nombre al JComboBox
		}
		
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 0;
		panelNorte.add(comboBox, gbc_comboBox);
		
		JButton btnEnviar = new JButton("");
		btnEnviar.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/enviar.png")));
		GridBagConstraints gbcB1 = new GridBagConstraints();
		gbcB1.insets = new Insets(0, 0, 5, 5);
		gbcB1.gridx = 1;
		gbcB1.gridy = 0;
		panelNorte.add(btnEnviar, gbcB1);
		
		JButton btnLupa = new JButton("");
		btnLupa.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/analisis-de-busqueda.png")));
		GridBagConstraints gbcB2 = new GridBagConstraints();
		gbcB2.insets = new Insets(0, 0, 5, 5);
		gbcB2.gridx = 2;
		gbcB2.gridy = 0;
		panelNorte.add(btnLupa, gbcB2);
		
		JButton btnContacts = new JButton("Contactos");
		btnContacts.addActionListener(e -> openContacts());
		
		btnContacts.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/contacto-2.png")));
		GridBagConstraints gbcB3 = new GridBagConstraints();
		gbcB3.insets = new Insets(0, 0, 5, 5);
		gbcB3.gridx = 3;
		gbcB3.gridy = 0;
		panelNorte.add(btnContacts, gbcB3);
		
		JButton btnPremium = new JButton("Premium");
		btnPremium.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/calidad-premium.png")));
		GridBagConstraints gbcB4 = new GridBagConstraints();
		gbcB4.insets = new Insets(0, 0, 5, 5);
		gbcB4.gridx = 4;
		gbcB4.gridy = 0;
		panelNorte.add(btnPremium, gbcB4);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.anchor = GridBagConstraints.EAST;
		gbc2.insets = new Insets(0, 0, 5, 0);
		gbc2.fill = GridBagConstraints.VERTICAL;
		gbc2.gridx = 5;
		gbc2.gridy = 0;
		panelNorte.add(panel_2, gbc2);
		
		JLabel lblNewLabel_1 = new JLabel("usuario actual");
		panel_2.add(lblNewLabel_1);
		
		JButton btn5 = new JButton("");
		btn5.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/usuario.png")));
		panel_2.add(btn5);
		btn5.addActionListener(e -> openRegister());
		//Cambiar para crear una ventana en la que se muestre la info del usuario y la posibilidad de cambiar la foto de perfil
		
		return panelNorte;
		
	}
	
	public JPanel crearPanelMain(DefaultListModel<Contacto> modelo) {
		
		JPanel panelMain = new JPanel();
		GridBagLayout gbl4 = new GridBagLayout();
		gbl4.columnWidths = new int[]{303, 0, 0};
		gbl4.rowHeights = new int[]{0, 0};
		gbl4.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl4.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelMain.setLayout(gbl4);
		
		
		JPanel panelIzq = new JPanel();
		panelIzq.setBorder(new TitledBorder(null, "mensajes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc6 = new GridBagConstraints();
		gbc6.insets = new Insets(0, 0, 0, 5);
		gbc6.fill = GridBagConstraints.BOTH;
		gbc6.gridx = 0;
		gbc6.gridy = 0;
		panelMain.add(panelIzq, gbc6);
		panelIzq.setLayout(new BorderLayout(0, 0));
		
		// Crear el JList basado en el modelo
		JList<Contacto> lista = new JList<>(modelo);
		lista.setCellRenderer(new ContactoIndividualCellRenderer());
		
		
		lista.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        // Obtener la celda donde ocurrió el clic
		        int index = lista.locationToIndex(e.getPoint());
		        if (index != -1) { // Si el clic fue en una celda válida
		            Rectangle cellBounds = lista.getCellBounds(index, index);

		            if (cellBounds.contains(e.getPoint())) { // Verifica que el clic esté dentro de la celda
		                int x = e.getX(); // Coordenada X del clic
		                int y = e.getY(); // Coordenada Y del clic

		                // Define el área del botón "+"
		                int buttonWidth = 60; // Ancho del botón
		                int buttonHeight = 20; // Alto del botón
		                
		                int ajusteX = -13; // Cambia este valor para mover el botón horizontalmente
		                int ajusteY = -17; // Cambia este valor para mover el botón verticalmente
		                
		                
		                int buttonX = cellBounds.x + cellBounds.width - buttonWidth + ajusteX; // Posición X del botón
		                int buttonY = cellBounds.y + (cellBounds.height - buttonHeight) / 2 + ajusteY; // Posición Y centrada
		                
		                Rectangle botonArea = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
		    
		                // Verifica si el clic fue dentro del botón "+"
		                if (botonArea.contains(x, y)) {
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
		panelIzq.add(scrollPane, BorderLayout.CENTER);
        panelIzq.setVisible(true);
        
        JPanel panelDer = new JPanel();
        panelDer.setBorder(new TitledBorder(null, "mensajes con x", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc5 = new GridBagConstraints();
		gbc5.fill = GridBagConstraints.BOTH;
		gbc5.gridx = 1;
		gbc5.gridy = 0;
		panelMain.add(panelDer, gbc5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{0, 0, 0};
		gbl_panel_5.rowHeights = new int[]{0, 0, 0};
		gbl_panel_5.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panelDer.setLayout(gbl_panel_5);
		
		
		JScrollPane chat = new JScrollPane();
		chat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		chat.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chat.setBorder(null);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panelDer.add(chat, gbc_scrollPane_1);
		
		listaContactos.setSelectedIndex(0);
		
		panelChat = new JPanel();
        panelChat.setLayout(new BoxLayout(panelChat, BoxLayout.Y_AXIS));
        chat.add(panelChat);
		
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.anchor = GridBagConstraints.SOUTH;
		gbc_textField_1.insets = new Insets(0, 0, 0, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 0;
		gbc_textField_1.gridy = 1;
		panelDer.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JButton btnSend = new JButton("");
		btnSend.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/send.png")));
		GridBagConstraints gbcB4 = new GridBagConstraints();
		gbcB4.anchor = GridBagConstraints.SOUTH;
		gbcB4.gridx = 1;
		gbcB4.gridy = 1;
		panelDer.add(btnSend, gbcB4);
		
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enviarMensajes(panelChat, textField_1, listaContactos.getSelectedValue());
			}
		});
		
		return panelMain;
		
	}
	
	private void openRegister() {
		VentanaRegister ventanaRegister = new VentanaRegister(this);
		ventanaRegister.setVisible(true);
	}
	
	private void openContacts() {
		VentanaContactos ventanaContactos = new VentanaContactos();
		ventanaContactos.setVisible(true);
	}
	
	private void enviarMensajes(JPanel chat, JTextField texto, Contacto contacto) {
		
		boolean enviado = AppChat.getInstance().sendMessage(texto.getText(), contacto);
		
		if (enviado) {
			JOptionPane.showMessageDialog(this, "Mensaje enviado correctamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		}
		BubbleText burbuja;
		burbuja=new BubbleText(chat, texto.getText(), Color.GREEN, "Yo", BubbleText.SENT);
		chat.add(burbuja);
		texto.setText(null);
		listaContactos.updateUI();//
		
	}
	
	
	
	private class ContactoIndividualCellRenderer extends JPanel implements ListCellRenderer<Contacto> {
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
	        
	     // Añadir un ActionListener al botón "+"
	        btnPlus.addActionListener(e -> {
	            AñadirContactos ventanaContacto = new AñadirContactos((JFrame) SwingUtilities.getWindowAncestor(this));
	            ventanaContacto.setVisible(true);
	        });
	        

		}

		@Override
		public Component getListCellRendererComponent(JList<? extends Contacto> list, Contacto persona, int index,
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
