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
import java.util.Collections;
import java.util.List;

import umu.tds.controlador.AppChat;
import umu.tds.model.Contacto;
import umu.tds.model.ContactoIndividual;
import umu.tds.model.Mensaje;
import umu.tds.model.Usuario;

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
	private static final String NOMBRE_VENTANA = "AppChat";
	private static ImageIcon ICON = new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/logo128x128.png"));

	private JPanel panelChat, panelDer;
	private JTextField textField_1;
	private List<Contacto> contactos;
	private DefaultListModel<Contacto> modelo = new DefaultListModel<>();
	private JList<Contacto> listaContactos;
	private JScrollPane chatScrollPane;
	private final Usuario usuarioAct = AppChat.getInstance().getCurrentUser();
	private TitledBorder titledBorder;
	private JComboBox<String> comboBox;

	public VentanaMain() {
		initComponents();
	}

	public void initComponents() {
		setTitle(NOMBRE_VENTANA);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(ICON.getImage());
		setLayout(new BorderLayout(0, 0));

		crearModelosVista();
		add(crearPanelNorte(), BorderLayout.NORTH);
		add(crearPanelMain(), BorderLayout.CENTER);

		pack();
		setResizable(true);
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
	}

	public void crearModelosVista() {
		List<Contacto> contactos = usuarioAct.getContactos();
		modelo.clear(); // Limpiar el modelo antes de añadir los contactos
		contactos.stream().filter(c -> !modelo.contains(c)).forEach(modelo::addElement);
		if (listaContactos == null) {
			listaContactos = new JList<>(modelo);
		}
		//listaContactos = new JList<>(modelo);
	}

	public JPanel crearPanelNorte() {
		JPanel panelNorte = new JPanel();
		panelNorte.setBorder(new LineBorder(new Color(0, 0, 0)));

		GridBagLayout gbl1 = new GridBagLayout();
		gbl1.columnWidths = new int[] { 209, 0, 0, 0, 0, 0, 0 };
		gbl1.rowHeights = new int[] { 0, 0, 0 };
		gbl1.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl1.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		panelNorte.setLayout(gbl1);

		comboBox = new JComboBox<>();
		for (int i = 0; i < modelo.size(); i++) {
			Contacto contacto = modelo.getElementAt(i); // Obtener el contacto
			comboBox.addItem(contacto.getNombre()); // Añadir el nombre al JComboBox
		}
		
		comboBox.addActionListener(e -> {
			String seleccionado = (String) comboBox.getSelectedItem();

			for (int i = 0; i < modelo.getSize(); i++) {
				Contacto contacto = modelo.getElementAt(i);
				if (contacto.getNombre().equals(seleccionado)) {
					listaContactos.setSelectedIndex(i);
					cargarConversacion(contacto);
					break;
				}
			}
		});

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
		btnLupa.addActionListener(e -> openBuscar());

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
		btnPremium.addActionListener(e -> openPremium());
		panelNorte.add(btnPremium, gbcB4);
		

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.anchor = GridBagConstraints.EAST;
		gbc2.insets = new Insets(0, 0, 5, 0);
		gbc2.fill = GridBagConstraints.VERTICAL;
		gbc2.gridx = 5;
		gbc2.gridy = 0;
		panelNorte.add(panel_2, gbc2);

		String user = usuarioAct.getName();
		JLabel labelUsuario = new JLabel(user);
		panel_2.add(labelUsuario);

		JButton btn5 = new JButton("");
		btn5.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/usuario.png")));
		panel_2.add(btn5);
		btn5.addActionListener(e -> openRegister());

		return panelNorte;

	}

	public JPanel crearPanelMain() {

		JPanel panelMain = new JPanel();
		GridBagLayout gbl4 = new GridBagLayout();
		gbl4.columnWidths = new int[] { 303, 0, 0 };
		gbl4.rowHeights = new int[] { 0, 0 };
		gbl4.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl4.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelMain.setLayout(gbl4);

		JPanel panelIzq = new JPanel();
		panelIzq.setBorder(new TitledBorder(null, " Contactos ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc6 = new GridBagConstraints();
		gbc6.insets = new Insets(0, 0, 0, 5);
		gbc6.fill = GridBagConstraints.BOTH;
		gbc6.gridx = 0;
		gbc6.gridy = 0;
		panelMain.add(panelIzq, gbc6);
		panelIzq.setLayout(new BorderLayout(0, 0));

		listaContactos.setCellRenderer(new ContactoIndividualCellRenderer());

		panelDer = new JPanel();
		GridBagConstraints gbc5 = new GridBagConstraints();
		gbc5.fill = GridBagConstraints.BOTH;
		gbc5.gridx = 1;
		gbc5.gridy = 0;
		panelMain.add(panelDer, gbc5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_5.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_5.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		panelDer.setLayout(gbl_panel_5);

		titledBorder = new TitledBorder(null, " Chat ", TitledBorder.LEADING, TitledBorder.TOP, null,
				null);
		panelDer.setBorder(titledBorder);

		// Agregar un MouseListener para deseleccionar
		listaContactos.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				System.out.println("Contacto seleccionado: " + listaContactos.getSelectedValue().getNombre());
				Contacto contactoSeleccionado = listaContactos.getSelectedValue();
				if (contactoSeleccionado != null) {
					cargarConversacion(contactoSeleccionado);
					panelDer.repaint();
					pack();
				} else {
					panelChat.setBackground(Color.WHITE); // Restaurar color original si no hay selección
					titledBorder.setTitle(" Chat ");
					panelDer.repaint(); // Actualiza el panel para reflejar el título original
				}
			}
		});

		listaContactos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Obtener la celda donde ocurrió el clic
				int index = listaContactos.locationToIndex(e.getPoint());
				if (index != -1) { // Si el clic fue en una celda válida
					Rectangle cellBounds = listaContactos.getCellBounds(index, index);

					if (cellBounds.contains(e.getPoint())) { // Verifica que el clic esté dentro de la celda
						int x = e.getX(); // Coordenada X del clic
						int y = e.getY(); // Coordenada Y del clic

						// Define el área del botón "+"
						int buttonWidth = 60; // Ancho del botón
						int buttonHeight = 20; // Alto del botón

						int ajusteX = -13; // Cambia este valor para mover el botón horizontalmente
						int ajusteY = -17; // Cambia este valor para mover el botón verticalmente

						int buttonX = cellBounds.x + cellBounds.width - buttonWidth + ajusteX; // Posición X del botón
						int buttonY = cellBounds.y + (cellBounds.height - buttonHeight) / 2 + ajusteY; // Posición Y
																										// centrada

						Rectangle botonArea = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);

						// Verifica si el clic fue dentro del botón "+"
						if (botonArea.contains(x, y)) {
							AñadirContactos ventanaContacto = new AñadirContactos(
									(JFrame) SwingUtilities.getWindowAncestor(listaContactos));
							ventanaContacto.setVisible(true);
						} else {
							Contacto contacto = listaContactos.getModel().getElementAt(index);
							cargarConversacion(contacto);
						}
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(listaContactos);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelIzq.add(scrollPane, BorderLayout.CENTER);
		panelIzq.setVisible(true);

		chatScrollPane = new JScrollPane();
		chatScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		chatScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chatScrollPane.setBorder(null);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panelDer.add(chatScrollPane, gbc_scrollPane_1);

		panelChat = new JPanel();
		panelChat.setLayout(new BoxLayout(panelChat, BoxLayout.Y_AXIS));
		chatScrollPane.setViewportView(panelChat); // Asegúrate de que el panelChat esté en el JScrollPane
		panelChat.setBackground(Color.WHITE); // Establecer el color original

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
				System.out.println("Botón enviar pulsado");
				enviarMensajes(panelChat, textField_1, listaContactos.getSelectedValue());
				List<Contacto> contactos_usr = usuarioAct.getContactos();
				for (Contacto contacto : contactos_usr) {
					List<Mensaje> mensajes = contacto.getMensajes();

					for (Mensaje msj : mensajes) {
						System.out.println(msj.getTexto());
					}
				}
			}
		});

		return panelMain;

	}

	private void openRegister() {
		VentanaRegister ventanaRegister = new VentanaRegister(this);
		ventanaRegister.setVisible(true);
	}

	private void openContacts() {
		VentanaContactos ventanaContactos = new VentanaContactos(this);
		ventanaContactos.setVisible(true);

		crearModelosVista();
		actualizarComboBox(comboBox);
	}

	private void enviarMensajes(JPanel chat, JTextField texto, Contacto contacto) {

		if (texto.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "El mensaje no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		boolean enviado = AppChat.getInstance().sendMessage(texto.getText(), contacto);

		if (enviado) {
			BubbleText burbuja;
			burbuja = new BubbleText(chat, texto.getText(), Color.GREEN, "Yo", BubbleText.SENT);
			chat.add(burbuja);
			texto.setText(null);

			chat.revalidate();
			chat.repaint();

			SwingUtilities.invokeLater(() -> {
				JScrollBar vertical = chatScrollPane.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
			});
			
			//listaContactos.updateUI();
			actualizarVista();
		}

	}
	
	public void actualizarVista() {
		//crearModelosVista();
		listaContactos.repaint();
		panelChat.revalidate();
		panelChat.repaint();
	}

	private void cargarConversacion(Contacto contacto) {
		if (contacto == null) {
			return;
		}

		System.out.println("Cargando conversación con: " + contacto.getNombre());
		panelChat.removeAll(); 
		
		titledBorder.setTitle(" Chat con " + contacto.getNombre());
		panelDer.repaint();

		List<Mensaje> mensajes = contacto.getMensajes();
		if (mensajes == null || mensajes.isEmpty()) {
	        System.out.println("No se encontraron mensajes");
	        mensajes = Collections.emptyList(); // Asegúrate de que la lista no sea null
	    } else {
	        System.out.println("Mensajes encontrados: " + mensajes.size());
	        
	        // Imprimir mensajes para depuración
	        for (Mensaje m : mensajes) {
	            System.out.println("Mensaje: " + m.getTexto() + 
	                             ", Emisor: " + m.getEmisor().getName() + 
	                             ", Receptor: " + (m.getReceptor() != null ? m.getReceptor().getNombre() : "null"));
	        }
	    }

		mensajes.stream().map(m -> {
			if (m.getEmisor().equals(usuarioAct)) {
				return new BubbleText(panelChat, m.getTexto(), Color.GREEN, "Yo", BubbleText.SENT);
			} else {
				return new BubbleText(panelChat, m.getTexto(), Color.GREEN, m.getEmisor().getName(),
						BubbleText.RECEIVED);
			}
		}).forEach(b -> panelChat.add(b));

		panelChat.revalidate();
		panelChat.repaint();

		SwingUtilities.invokeLater(() -> {
			JScrollBar vertical = chatScrollPane.getVerticalScrollBar();
			vertical.setValue(vertical.getMaximum());
		});
	}
	
	private void openPremium() {
		VentanaPremium ventanaPremium = new VentanaPremium(this);
		ventanaPremium.setVisible(true);
	}
	
	private void actualizarComboBox(JComboBox<String> comboBox) {
		comboBox.removeAllItems();
		for (int i = 0; i < modelo.size(); i++) {
			comboBox.addItem(modelo.getElementAt(i).getNombre());
		}
	}
	
	private void openBuscar() {
		BuscarMensajes ventanaBuscar = new BuscarMensajes();
		ventanaBuscar.setVisible(true);
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
