package umu.tds.vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Collections;
import java.util.List;

import umu.tds.controlador.AppChat;
import umu.tds.model.*;
import tds.BubbleText;

/**
 * Ventana principal de la aplicación AppChat. Esta clase implementa la interfaz
 * gráfica principal de la aplicación.
 * 
 * Responsabilidades -Mostrar la lista de contactos -Mostrar y gestionar las
 * conversaciones de chat -Proporcionar acceso a otras funcionalidades
 */
public class VentanaMain extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String NOMBRE_VENTANA = "AppChat";
	private static ImageIcon ICON = new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/logo128x128.png"));

	// Panel donde se muestran los mensajes del chat actual
	private JPanel panelChat;
	// Panel derecho que contiene el chat y campo de texto
	private JPanel panelDer;
	// Campo para escribir nuevos mensajes
	private JTextField mensajeTextField;
	// Modelo de datos para la lista de contactos
	private DefaultListModel<Contacto> modelo = new DefaultListModel<>();
	// Lista visual de contactos
	private JList<Contacto> listaContactos;
	// Scroll para el panel de chat
	private JScrollPane chatScrollPane;
	// Borde con título para el panel de chat
	private TitledBorder titledBorder;
	// Selector despegable de contactos
	private JComboBox<String> comboBox;

	private AppChat controlador;

	/**
	 * Constructor de la ventana principal
	 */
	public VentanaMain() {
		this.controlador = AppChat.getInstance();
		initComponents();
	}

	/**
	 * Se inicializan todos los componentes de la interfaz gráfica Se configuran los
	 * paneles, layouts y listeners básicos
	 */
	public void initComponents() {
		setTitle(NOMBRE_VENTANA);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(ICON.getImage());
		getContentPane().setLayout(new BorderLayout(0, 0));

		crearModelosVista();
		getContentPane().add(crearPanelNorte(), BorderLayout.NORTH);
		getContentPane().add(crearPanelMain(), BorderLayout.CENTER);

		pack();
		setResizable(true);
		setMinimumSize(getSize());
		setLocationRelativeTo(null); // Centrar la pantalla
	}

	/**
	 * Crear los modelos para la vista de contactos (carga los contactos del usuario
	 * en el modelo) Inicializa el modelo de datos para la lista de contactos
	 */
	public void crearModelosVista() {
		modelo.clear();
		controlador.getContactosUsuarioActual().stream().filter(c -> !modelo.contains(c)).forEach(modelo::addElement);

		if (listaContactos == null) {
			listaContactos = new JList<>(modelo);
			listaContactos.setCellRenderer(new ContactoIndividualCellRenderer());
			configurarEventosListaContactos();
		}
	}

	/**
	 * Crear panel superior
	 * 
	 * @return
	 */
	public JPanel crearPanelNorte() {
		JPanel panelNorte = new JPanel();
		panelNorte.setBorder(new LineBorder(Color.BLACK));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridy = 0;

		GridBagLayout gbl1 = new GridBagLayout();
		gbl1.columnWidths = new int[] { 209, 0, 0, 0, 0, 0, 0 };
		gbl1.rowHeights = new int[] { 0, 0, 0 };
		gbl1.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl1.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		panelNorte.setLayout(gbl1);

		// Combobox de contactos
		comboBox = new JComboBox<>();
		actualizarComboBox();
		comboBox.addActionListener(e -> manejarSeleccionComboBox());

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 0;
		panelNorte.add(comboBox, gbc_comboBox);

		// Botón enviar
		JButton btnEnviar = new JButton("");
		btnEnviar.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/enviar.png")));
		GridBagConstraints gbcB1 = new GridBagConstraints();
		gbcB1.insets = new Insets(0, 0, 5, 5);
		gbcB1.gridx = 1;
		gbcB1.gridy = 0;
		panelNorte.add(btnEnviar, gbcB1);

		// Botones de la barra superior
		agregarBotonBarra(panelNorte, "/umu/tds/resources/analisis-de-busqueda.png", "Buscar", 2, e -> openBuscar());
		agregarBotonBarra(panelNorte, "/umu/tds/resources/contacto-2.png", "Contactos", 3, e -> openContacts());
		agregarBotonBarra(panelNorte, "/umu/tds/resources/calidad-premium.png", "Premium", 4, e -> openPremium());

		// Panel de información del usuario
		JPanel panelUsuario = new JPanel();
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.anchor = GridBagConstraints.EAST;
		gbc2.insets = new Insets(0, 0, 5, 0);
		gbc2.fill = GridBagConstraints.VERTICAL;
		gbc2.gridx = 5;
		gbc2.gridy = 0;
		panelNorte.add(panelUsuario, gbc2);

		JLabel labelUsuario = new JLabel(controlador.getNombreUsuarioActual());
		panelUsuario.add(labelUsuario);

		// Botón usuario
		JButton btnPerfil = new JButton("");
		ImageIcon imagenPerfil = controlador.getImagenUsuarioActual();
		if (imagenPerfil != null && imagenPerfil.getIconWidth() > 0 && imagenPerfil.getIconHeight() > 0) {
		    // Redimensionar la imagen para que se ajuste bien al botón
		    Image img = imagenPerfil.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
		    btnPerfil.setIcon(new ImageIcon(img));
		} else {
			btnPerfil.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/usuario.png")));
		}
		panelUsuario.add(btnPerfil);
		btnPerfil.addActionListener(e -> openRegister());

		// Botón cerrar sesión
		JButton btnCerrarSesion = new JButton("");
		btnCerrarSesion.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/salida-2.png")));
		btnCerrarSesion.setToolTipText("Cerrar sesión");
		btnCerrarSesion.addActionListener(e -> cerrarSesion());
		panelUsuario.add(btnCerrarSesion);

		return panelNorte;

	}

	private void agregarBotonBarra(JPanel panel, String iconPath, String tooltip, int posX,
			java.awt.event.ActionListener action) {
		JButton boton = new JButton();
		boton.setIcon(new ImageIcon(VentanaMain.class.getResource(iconPath)));
		boton.setToolTipText(tooltip);
		boton.addActionListener(action);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = posX;
		gbc.gridy = 0;
		panel.add(boton, gbc);
	}

	/**
	 * Crear el panel principal que contiene la lista de contactos y el chat
	 * 
	 * @return
	 */
	public JPanel crearPanelMain() {

		JPanel panelMain = new JPanel();
		GridBagLayout gbl4 = new GridBagLayout();
		gbl4.columnWidths = new int[] { 303, 0, 0 };
		gbl4.rowHeights = new int[] { 0, 0 };
		gbl4.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl4.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelMain.setLayout(gbl4);
		GridBagConstraints gbc = new GridBagConstraints();

		// Panel izquierdo (contactos)
		JPanel panelIzq = new JPanel();
		panelIzq.setBorder(new TitledBorder(null, " Contactos ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc6 = new GridBagConstraints();
		gbc6.insets = new Insets(0, 0, 0, 5);
		gbc6.fill = GridBagConstraints.BOTH;
		gbc6.gridx = 0;
		gbc6.gridy = 0;
		panelMain.add(panelIzq, gbc6);
		panelIzq.setLayout(new BorderLayout(0, 0));

		// Panel derecho (chat)
		panelDer = new JPanel();
		GridBagConstraints gbc5 = new GridBagConstraints();
		gbc5.fill = GridBagConstraints.BOTH;
		gbc5.gridx = 1;
		gbc5.gridy = 0;
		panelMain.add(panelDer, gbc5);

		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 0, 175, 0 };
		gbl_panel_5.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_5.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		panelDer.setLayout(gbl_panel_5);

		titledBorder = new TitledBorder(null, " Chat ", TitledBorder.LEADING, TitledBorder.TOP, null, null);
		panelDer.setBorder(titledBorder);

		// Scroll para la lista de contactos
		JScrollPane scrollPane = new JScrollPane(listaContactos);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelIzq.add(scrollPane, BorderLayout.CENTER);
		panelIzq.setVisible(true);

		// Pandel de chat con scroll
		chatScrollPane = new JScrollPane();
		chatScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		chatScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chatScrollPane.setBorder(null);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panelDer.add(chatScrollPane, gbc_scrollPane_1);

		panelChat = new JPanel();
		panelChat.setLayout(new BoxLayout(panelChat, BoxLayout.Y_AXIS));
		chatScrollPane.setViewportView(panelChat);
		panelChat.setBackground(Color.WHITE);

		// Panel inferior con emoji, texto y enviar
		GridBagLayout gbl_panelInferior = new GridBagLayout();
		gbl_panelInferior.columnWeights = new double[] { 0.0, 1.0, 0.0 };
		JPanel panelInferior = new JPanel(gbl_panelInferior);
		GridBagConstraints gbcInferior = new GridBagConstraints();
		gbcInferior.insets = new Insets(5, 5, 5, 5);
		gbcInferior.gridy = 1;

		// Botón emoji
		JButton btnEmoji = new JButton();
		btnEmoji.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/feliz.png")));
		GridBagConstraints gbcEmoji = new GridBagConstraints();
		gbcEmoji.gridx = 0;
		gbcEmoji.gridy = 1;
		gbcEmoji.insets = new Insets(0, 0, 0, 5);
		panelInferior.add(btnEmoji, gbcEmoji);

		// Campo de texto para escribir mensajes
		mensajeTextField = new JTextField();
		GridBagConstraints gbc_mensajeTextField = new GridBagConstraints();
		gbc_mensajeTextField.anchor = GridBagConstraints.SOUTH;
		gbc_mensajeTextField.insets = new Insets(0, 0, 0, 5);
		gbc_mensajeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_mensajeTextField.gridx = 1;
		gbc_mensajeTextField.gridy = 1;
		panelInferior.add(mensajeTextField, gbc_mensajeTextField);
		mensajeTextField.setColumns(10);

		// Botón enviar mensaje
		JButton btnSend = new JButton("");
		btnSend.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/send.png")));
		GridBagConstraints gbcB4 = new GridBagConstraints();
		gbcB4.anchor = GridBagConstraints.SOUTH;
		gbcB4.gridx = 2;
		gbcB4.gridy = 1;
		panelInferior.add(btnSend, gbcB4);
		btnSend.addActionListener(e -> enviarMensajes(panelChat, mensajeTextField, listaContactos.getSelectedValue()));

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panelDer.add(panelInferior, gbc);

		JPopupMenu popupEmojis = new JPopupMenu();
		popupEmojis.setLayout(new GridLayout(2, 4));
		for (int i = 0; i <= BubbleText.MAXICONO; i++) {
			final int emojiId = i;
			JButton emojiButton = new JButton();
			emojiButton.setIcon(BubbleText.getEmoji(i));
			emojiButton.addActionListener(e -> enviarEmoji(emojiId));
			popupEmojis.add(emojiButton);
		}
		btnEmoji.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				popupEmojis.show(e.getComponent(), e.getX(), e.getY());
			}
		});

		return panelMain;

	}

	private void enviarEmoji(int emojiId) {
		Contacto contacto = listaContactos.getSelectedValue();
		if (contacto == null) {
			JOptionPane.showMessageDialog(this, "Seleccione un contacto para enviar un emoji", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (controlador.sendEmoji(emojiId, contacto)) {
			BubbleText burbuja = new BubbleText(panelChat, emojiId, Color.GREEN, "Yo", BubbleText.SENT, 18);
			panelChat.add(burbuja);
			panelChat.revalidate();
			panelChat.repaint();
			desplazarHaciaAbajo();
			actualizarListaContactos();
		}
	}

	private void configurarEventosListaContactos() {
		listaContactos.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				Contacto contactoSeleccionado = listaContactos.getSelectedValue();
				if (contactoSeleccionado != null) {
					cargarConversacion(contactoSeleccionado);
				} else {

					panelChat.setBackground(Color.WHITE); // Restaurar color original si no hayselección
					titledBorder.setTitle(" Chat ");
					panelDer.repaint(); // Actualiza el panel para reflejar el título original

				}
			}
		});

		// Evento de clic para detectar clic en "+"
		listaContactos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Obtener la celda donde ocurrió el clic
				int index = listaContactos.locationToIndex(e.getPoint());
				if (index != -1) { // Si el clic fue en una celda válida
					Rectangle cellBounds = listaContactos.getCellBounds(index, index);

					if (cellBounds.contains(e.getPoint())) { // Verifica que el clic esté dentro de la celda
						Contacto contacto = listaContactos.getModel().getElementAt(index);
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

						// Verifica si el clic fue dentro del área del botón "+" y si el botón debería
						// ser visible
						boolean mostrarBotonPlus = false;
						if (contacto instanceof ContactoIndividual) {
							ContactoIndividual contactoInd = (ContactoIndividual) contacto;
							mostrarBotonPlus = !contactoInd.isAñadidoManualmente()
									&& !contactoInd.getMensajes().isEmpty();
						}

						// Verifica si el clic fue dentro del botón "+"
						if (mostrarBotonPlus && botonArea.contains(x, y)) {
							if (contacto instanceof ContactoIndividual) {
								ContactoIndividual contactoInd = (ContactoIndividual) contacto;
								abrirVentanaAñadirContactoExistente(contactoInd.getMovil());
							}
						} else {
							cargarConversacion(contacto);
						}
					}
				}
			}
		});

	}

	/**
	 * Actualiza el comboBox con los nombres de los contactos
	 */
	private void actualizarComboBox() {
		comboBox.removeAllItems();
		for (int i = 0; i < modelo.size(); i++) {
			comboBox.addItem(modelo.getElementAt(i).getNombre());
		}
	}

	private void manejarSeleccionComboBox() {
		String seleccionado = (String) comboBox.getSelectedItem();
		if (seleccionado != null) {
			for (int i = 0; i < modelo.getSize(); i++) {
				Contacto contacto = modelo.getElementAt(i);
				if (contacto.getNombre().equals(seleccionado)) {
					listaContactos.setSelectedIndex(i);
					cargarConversacion(contacto);
					break;
				}
			}
		}
	}

	/**
	 * Mostrar la conversación del contacto seleccionado
	 * 
	 * @param contacto
	 */

	private void cargarConversacion(Contacto contacto) {
		if (contacto == null) {
			return;
		}

		panelChat.removeAll();
		titledBorder.setTitle(" Chat con " + contacto.getNombre());
		panelDer.repaint();

		List<Mensaje> mensajes = controlador.getMensajesConContacto(contacto);
		if (mensajes == null || mensajes.isEmpty()) {
			mensajes = Collections.emptyList();
		}

		boolean esGrupo = contacto instanceof Grupo;

		mensajes.stream().map(m -> {
			boolean esEmisor = controlador.esMensajeDelUsuarioActual(m);
			String nombre;
			if (esGrupo) {
				if (esEmisor) {
					nombre = "Yo";
				} else {
					Contacto emisor = controlador.getContactoDeUsuario(m.getEmisor());
					if (emisor != null && emisor.getNombre() != null && !emisor.getNombre().isEmpty()) {
			            nombre = emisor.getNombre(); // Está en la lista de contactos
			        } else {
			            nombre = m.getEmisor().getPhone(); // No está en contactos, usamos el número
			        }
				}
			} else {
				nombre = esEmisor ? "Yo" : contacto.getNombre();
			}
			
			int tipo = esEmisor ? BubbleText.SENT : BubbleText.RECEIVED;

			if (m.getEmoticono() >= 0) {
				return new BubbleText(panelChat, m.getEmoticono(), Color.GREEN, nombre, tipo, 18);
			} else {
				return new BubbleText(panelChat, m.getTexto(), Color.GREEN, nombre, tipo);
			}
		}).forEach(b -> panelChat.add(b));

		panelChat.revalidate();
		panelChat.repaint();
		desplazarHaciaAbajo();
	}

	/**
	 * Enviar mensaje al contacto seleccionado
	 * 
	 * @param chat
	 * 
	 * @param texto
	 * 
	 * @param contacto
	 */

	private void enviarMensajes(JPanel chat, JTextField texto, Contacto contacto) {

		if (texto.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "El mensaje no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		boolean enviado = controlador.sendMessage(texto.getText(), contacto);

		if (enviado) {
			BubbleText burbuja;
			burbuja = new BubbleText(chat, texto.getText(), Color.GREEN, "Yo", BubbleText.SENT);
			chat.add(burbuja);
			texto.setText(null);

			chat.revalidate();
			chat.repaint();
			desplazarHaciaAbajo();

			actualizarListaContactos();
		}
	}

	/**
	 * Desplaza el scroll del panel de la conversación hacia abajo (ultimo mensaje)
	 */

	private void desplazarHaciaAbajo() {
		SwingUtilities.invokeLater(() -> {
			JScrollBar vertical = chatScrollPane.getVerticalScrollBar();
			vertical.setValue(vertical.getMaximum());
		});
	}

	/**
	 * Actualiza la lista de contactos con la información más reciente
	 */
	private void actualizarListaContactos() {
		Contacto contactoSeleccionado = listaContactos.getSelectedValue();
		crearModelosVista();
		actualizarComboBox();

		if (contactoSeleccionado != null) {
			for (int i = 0; i < modelo.getSize(); i++) {
				if (modelo.getElementAt(i).equals(contactoSeleccionado)) {
					listaContactos.setSelectedIndex(i);
					break;
				}
			}
		}
		listaContactos.repaint();
	}

	// Métodos para abrir ventanas

	// Ventana registrar, esta se tiene que cambiar para que en vez de abir esta se
	// abra una para camnbiar la imagen del user
	private void openRegister() {
		VentanaEditarPerfil ventanaEditarPerfil = new VentanaEditarPerfil(this);
	    ventanaEditarPerfil.setVisible(true);
	}

	// Ventana contactos
	private void openContacts() {
		VentanaContactos ventanaContactos = new VentanaContactos(this);
		ventanaContactos.setVisible(true);
		actualizarListaContactos();
	}

	// Ventana premium
	private void openPremium() {
		VentanaPremium ventanaPremium = new VentanaPremium(this);
		ventanaPremium.setVisible(true);
	}

	// Ventana buscar
	private void openBuscar() {
		BuscarMensajes ventanaBuscar = new BuscarMensajes();
		ventanaBuscar.setVisible(true);
	}

	private void abrirVentanaAñadirContactoExistente(String phone) {
		AñadirContactoExistente ventanaContacto = new AñadirContactoExistente(
				(JFrame) SwingUtilities.getWindowAncestor(listaContactos), phone);
		ventanaContacto.setModal(true); // Hacerla modal para evitar interacciones con la ventana principal
		ventanaContacto.setVisible(true);

		// Actualizar la lista de contactos después de cerrar la ventana
		actualizarListaContactos();
	}

	private void cerrarSesion() {
		controlador.logout();
		dispose(); // Cierra la ventana actual

		new VentanaLogin().setVisible(true);
	}
}