package umu.tds.vista;

import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.LineBorder;
import java.awt.Color;

import javax.swing.border.TitledBorder;

import java.util.Collections;
import java.util.List;

import umu.tds.controlador.AppChat;
import umu.tds.model.Contacto;
import umu.tds.model.Mensaje;

import tds.BubbleText;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		setLayout(new BorderLayout(0, 0));

		crearModelosVista();
		add(crearPanelNorte(), BorderLayout.NORTH);
		add(crearPanelMain(), BorderLayout.CENTER);

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
		List<Contacto> contactos = controlador.getContactosUsuarioActual();
		contactos.stream().filter(c -> !modelo.contains(c)).forEach(modelo::addElement);

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
		btnPerfil.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/usuario.png")));
		panelUsuario.add(btnPerfil);
		btnPerfil.addActionListener(e -> openRegister());

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
		gbl_panel_5.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_5.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_5.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
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
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panelDer.add(chatScrollPane, gbc_scrollPane_1);

		panelChat = new JPanel();
		panelChat.setLayout(new BoxLayout(panelChat, BoxLayout.Y_AXIS));
		chatScrollPane.setViewportView(panelChat);
		panelChat.setBackground(Color.WHITE);

		// Campo de texto para escribir mensajes
		mensajeTextField = new JTextField();
		GridBagConstraints gbc_mensajeTextField = new GridBagConstraints();
		gbc_mensajeTextField.anchor = GridBagConstraints.SOUTH;
		gbc_mensajeTextField.insets = new Insets(0, 0, 0, 5);
		gbc_mensajeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_mensajeTextField.gridx = 0;
		gbc_mensajeTextField.gridy = 1;
		panelDer.add(mensajeTextField, gbc_mensajeTextField);
		mensajeTextField.setColumns(10);

		// Botón enviar mensaje
		JButton btnSend = new JButton("");
		btnSend.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/send.png")));
		GridBagConstraints gbcB4 = new GridBagConstraints();
		gbcB4.anchor = GridBagConstraints.SOUTH;
		gbcB4.gridx = 1;
		gbcB4.gridy = 1;
		panelDer.add(btnSend, gbcB4);
		btnSend.addActionListener(e -> enviarMensajes(panelChat, mensajeTextField, listaContactos.getSelectedValue()));

		return panelMain;

	}

	private void configurarEventosListaContactos() {
		listaContactos.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				Contacto contactoSeleccionado = listaContactos.getSelectedValue();
				if (contactoSeleccionado != null) {
					cargarConversacion(contactoSeleccionado);
				} else {
					panelChat.setBackground(Color.WHITE); // Restaurar color original si no hay selección
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
							abrirVentanaAñadirContactos(); // Verificar si es openContacts, cambiar para que se le pase
															// el numero y solo se tenga que modificar el nombre
						} else {
							Contacto contacto = listaContactos.getModel().getElementAt(index);
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

		mensajes.stream().map(m -> {
			if (controlador.esMensajeDelUsuarioActual(m)) {
				return new BubbleText(panelChat, m.getTexto(), Color.GREEN, "Yo", BubbleText.SENT);
			} else {
				return new BubbleText(panelChat, m.getTexto(), Color.GREEN, m.getEmisor().getName(),
						BubbleText.RECEIVED);
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
	 * @param texto
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
		VentanaRegister ventanaRegister = new VentanaRegister(this);
		ventanaRegister.setVisible(true);
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

	// Ventana añadir contactos
	private void abrirVentanaAñadirContactos() {
		AñadirContactos ventanaContacto = new AñadirContactos(
				(JFrame) SwingUtilities.getWindowAncestor(listaContactos));
		ventanaContacto.setVisible(true);
	}
}