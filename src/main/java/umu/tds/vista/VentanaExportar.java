package umu.tds.vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import umu.tds.controlador.AppChat;
import umu.tds.model.Contacto;

public class VentanaExportar extends JDialog {
	private static final long serialVersionUID = 1L;
	private static final String NOMBRE_VENTANA = "Exportar Conversación a PDF";

	private JComboBox<Object> comboContactos; // Cambiado a Object para aceptar Contacto y String
	private JTextField campoRutaArchivo;
	private List<Contacto> contactosUsuario;
	private JButton btnSeleccionarRuta;
	private JButton btnExportar;
	private JButton btnCancelar;

	public VentanaExportar(JFrame owner) {
		super(owner, NOMBRE_VENTANA, true);

		initComponents();
	}

	private void initComponents() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(VentanaExportar.class.getResource("/umu/tds/resources/logo128x128.png")).getImage());
		setLayout(new BorderLayout(0, 0));

		contactosUsuario = AppChat.getInstance().getCurrentUser().getContactos();

		// Añadir la opción para "Todos los contactos"
		List<Object> opciones = new ArrayList<>();
		opciones.add("Todos los contactos"); // Agregar la opción de todos los contactos
		opciones.addAll(contactosUsuario); // Agregar los contactos individuales a la lista

		JPanel panelFormulario = crearPanelFormulario(opciones);
		add(panelFormulario, BorderLayout.CENTER);

		JPanel panelBotones = crearPanelBotones();
		add(panelBotones, BorderLayout.SOUTH);

		pack();
		setResizable(false);
		setLocationRelativeTo(null);
	}

	/**
	 * Crea un panel de formulario para exportar conversaciones con el mismo estilo
	 * visual que el otro panel
	 *
	 * @param opciones Lista de opciones para el selector de contactos
	 * @return Panel de formulario con diseño mejorado
	 */
	private JPanel crearPanelFormulario(List<Object> opciones) {
		// Panel principal con borde exterior
		JPanel panelFormulario = new JPanel();
		panelFormulario.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelFormulario.setLayout(new BorderLayout(0, 0));

		// Panel wrapper con título
		JPanel panelWrapperFormulario = new JPanel();
		panelWrapperFormulario.setBorder(
				new TitledBorder(null, " Exportar Conversación ", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelFormulario.add(panelWrapperFormulario, BorderLayout.CENTER);
		panelWrapperFormulario.setLayout(new BorderLayout(0, 0));

		// Panel principal que contiene los elementos del formulario
		JPanel mainPanel = new JPanel();
		panelWrapperFormulario.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainPanel.setLayout(new BorderLayout(0, 0));

		// Panel de contenido con GridBagLayout para el formulario
		JPanel panelContenido = new JPanel();
		panelContenido.setLayout(new GridBagLayout());
		mainPanel.add(panelContenido, BorderLayout.CENTER);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 5, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weightx = 1.0;

		// Selector de contacto
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel labelContacto = new JLabel("Seleccionar contacto:");
		panelContenido.add(labelContacto, gbc);

		gbc.gridy = 1;
		gbc.insets = new Insets(0, 10, 15, 10); // Mayor espacio después del combo
		comboContactos = new JComboBox<>(opciones.toArray());
		comboContactos.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof Contacto) {
					setText(((Contacto) value).getNombre());
				} else if (value instanceof String) {
					setText((String) value); // Si es "Todos los contactos", mostrar el texto
				}
				return this;
			}
		});
		panelContenido.add(comboContactos, gbc);

		// Selección de ruta de archivo
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 10, 5, 10); // Volvemos a espaciado normal
		JLabel labelRuta = new JLabel("Guardar en:");
		panelContenido.add(labelRuta, gbc);

		gbc.gridy = 3;
		gbc.insets = new Insets(0, 10, 5, 10);
		campoRutaArchivo = new JTextField();
		campoRutaArchivo.setEditable(false);
		panelContenido.add(campoRutaArchivo, gbc);

		gbc.gridy = 4;
		gbc.insets = new Insets(5, 10, 10, 10);
		btnSeleccionarRuta = new JButton("Seleccionar ubicación...");
		panelContenido.add(btnSeleccionarRuta, gbc);

		btnSeleccionarRuta.addActionListener(this::seleccionarRuta);

		return panelFormulario;
	}

	/**
	 * Crea un panel de botones con la misma estructura que el panel de referencia
	 *
	 * @return Panel con botones Cancelar y Exportar correctamente posicionados
	 */
	private JPanel crearPanelBotones() {
		// Panel principal con borde
		JPanel panelBotones = new JPanel();
		panelBotones.setBorder(new EmptyBorder(0, 10, 10, 10));
		panelBotones.setLayout(new BorderLayout(0, 0));

		// Panel para el botón Cancelar (izquierda)
		JPanel panelBtnCancelar = new JPanel();
		panelBotones.add(panelBtnCancelar, BorderLayout.WEST);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelBtnCancelar.add(btnCancelar);
		btnCancelar.addActionListener(e -> dispose());

		// Panel para el botón Exportar (derecha)
		JPanel panelBtnExportar = new JPanel();
		panelBotones.add(panelBtnExportar, BorderLayout.EAST);

		btnExportar = new JButton("Exportar");
		btnExportar.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelBtnExportar.add(btnExportar);

		btnExportar.addActionListener(e -> {
			if (comboContactos.getSelectedItem() == null || campoRutaArchivo.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Debes seleccionar un contacto y una ubicación para exportar.",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Object selectedItem = comboContactos.getSelectedItem(); // Usamos Object para aceptar tanto Contacto como
																	// String
			String ruta = campoRutaArchivo.getText();

			List<Contacto> contactosSeleccionados = new ArrayList<>();

			// Si se selecciona "Todos los contactos", se pasa la lista completa
			if ("Todos los contactos".equals(selectedItem)) {
				contactosSeleccionados = contactosUsuario;
			} else if (selectedItem instanceof Contacto) {
				contactosSeleccionados.add((Contacto) selectedItem);
			}

			AppChat.getInstance().exportarMensajesComoPdf(contactosSeleccionados, ruta);

			JOptionPane.showMessageDialog(this, "Conversación exportada con éxito.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			dispose();
		});

		getRootPane().setDefaultButton(btnExportar);

		return panelBotones;
	}

	// Método para seleccionar ruta
	private void seleccionarRuta(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Guardar PDF");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setSelectedFile(new File("chat_exportado.pdf"));

		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			campoRutaArchivo.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
	}
}
