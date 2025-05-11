package umu.tds.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import umu.tds.controlador.AppChat;
import umu.tds.model.Usuario;

/**
 * Ventana para editar el perfil del usuario, permitiendo cambiar la imagen de
 * perfil y mostrando información del usuario.
 */
public class VentanaEditarPerfil extends JDialog {
	private static final long serialVersionUID = 1L;
	private static final int ANCHO_VENTANA = 600; // Aumentado para acomodar ambos paneles
	private static final int ALTO_VENTANA = 400; // Aumentado para más espacio
	private static final int TAMAÑO_IMAGEN = 150;

	private AppChat controlador;
	private JLabel labelImagen;
	private File imagenSeleccionada;
	private ImageIcon imagenActual;

	/**
	 * Constructor de la ventana para editar el perfil del usuario.
	 *
	 * @param parent Ventana padre desde la que se abre esta ventana.
	 */
	public VentanaEditarPerfil(JFrame parent) {
		super(parent, "Editar Perfil", true);
		this.controlador = AppChat.getInstance();
		initComponents();
	}

	/**
	 * Inicializa los componentes de la interfaz.
	 */
	private void initComponents() {
		setSize(ANCHO_VENTANA, ALTO_VENTANA);
		setLocationRelativeTo(getOwner());
		setResizable(false);

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		mainPanel.setLayout(new BorderLayout(20, 20));
		setContentPane(mainPanel);

		// Panel principal que contendrá los dos paneles (info y imagen)
		JPanel panelContenido = new JPanel(new GridLayout(1, 2, 20, 0));

		// Panel para la información del usuario (izquierda)
		JPanel panelInfo = crearPanelInformacion();

		// Panel para la imagen (derecha)
		JPanel panelImagen = crearPanelImagen();

		// Añadir ambos paneles al panel contenedor
		panelContenido.add(panelInfo);
		panelContenido.add(panelImagen);

		// Añadir el panel contenedor al panel principal
		mainPanel.add(panelContenido, BorderLayout.CENTER);

		// Panel de botones
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

		JButton btnSeleccionar = new JButton("Seleccionar imagen");
		btnSeleccionar.addActionListener(e -> seleccionarImagen());

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(e -> guardarImagen());

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> dispose());

		panelBotones.add(btnSeleccionar);
		panelBotones.add(btnGuardar);
		panelBotones.add(btnCancelar);

		mainPanel.add(panelBotones, BorderLayout.SOUTH);
	}

	/**
	 * Crea el panel con la información del usuario (no editable).
	 *
	 * @return Panel con campos de información del usuario.
	 */
	private JPanel crearPanelInformacion() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createTitledBorder("Información del Usuario"));

		Usuario usuarioActual = controlador.getCurrentUser();

		// Formatear la fecha
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fechaNacimientoStr = usuarioActual.getFechaNacimiento().format(formatter);

		// Crear componentes para mostrar la información
		JPanel panelNombre = crearCampoInfo("Nombre completo:", usuarioActual.getName());
		JPanel panelTelefono = crearCampoInfo("Teléfono:", usuarioActual.getPhone());
		JPanel panelFechaNac = crearCampoInfo("Fecha nacimiento:", fechaNacimientoStr);

		// Para el saludo/descripción, usar un área de texto para mostrar texto más
		// largo
		JPanel panelSaludo = new JPanel(new BorderLayout(5, 5));
		panelSaludo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
		JLabel labelSaludo = new JLabel("Descripción:");
		JTextArea areaSaludo = new JTextArea(usuarioActual.getSaludo());
		areaSaludo.setEditable(false);
		areaSaludo.setLineWrap(true);
		areaSaludo.setWrapStyleWord(true);
		areaSaludo.setBackground(panel.getBackground());
		JScrollPane scrollSaludo = new JScrollPane(areaSaludo);
		scrollSaludo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		panelSaludo.add(labelSaludo, BorderLayout.NORTH);
		panelSaludo.add(scrollSaludo, BorderLayout.CENTER);

		// Añadir espacio entre componentes
		panel.add(Box.createVerticalStrut(10));
		panel.add(panelNombre);
		panel.add(Box.createVerticalStrut(10));
		panel.add(panelTelefono);
		panel.add(Box.createVerticalStrut(10));
		panel.add(panelFechaNac);
		panel.add(Box.createVerticalStrut(10));
		panel.add(panelSaludo);
		panel.add(Box.createVerticalStrut(10));

		// Añadir indicación sobre la edición
		JLabel labelNota = new JLabel("* Esta información no es editable en esta ventana");
		labelNota.setFont(new Font(labelNota.getFont().getName(), Font.ITALIC, 10));
		labelNota.setForeground(Color.GRAY);
		panel.add(labelNota);

		// Añadir espacio flexible al final para empujar todo hacia arriba
		panel.add(Box.createVerticalGlue());

		return panel;
	}

	/**
	 * Crea un panel con un campo de información etiquetado.
	 *
	 * @param etiqueta Etiqueta del campo.
	 * @param valor    Valor a mostrar.
	 * @return Panel con la etiqueta y el valor.
	 */
	private JPanel crearCampoInfo(String etiqueta, String valor) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

		JLabel label = new JLabel(etiqueta);
		label.setPreferredSize(new Dimension(120, 20));

		JTextField campo = new JTextField(valor);
		campo.setEditable(false);
		campo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		panel.add(label);
		panel.add(Box.createHorizontalStrut(10));
		panel.add(campo);

		return panel;
	}

	/**
	 * Crea el panel para la selección y visualización de la imagen de perfil.
	 *
	 * @return Panel con la imagen de perfil.
	 */
	private JPanel crearPanelImagen() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Imagen de Perfil"));

		JPanel contenedorImagen = new JPanel(new FlowLayout(FlowLayout.CENTER));
		labelImagen = new JLabel();
		labelImagen.setPreferredSize(new Dimension(TAMAÑO_IMAGEN, TAMAÑO_IMAGEN));
		labelImagen.setBorder(new LineBorder(Color.BLACK));
		labelImagen.setHorizontalAlignment(SwingConstants.CENTER);

		// Cargar la imagen actual del usuario
		cargarImagenActual();

		contenedorImagen.add(labelImagen);
		panel.add(contenedorImagen, BorderLayout.CENTER);

		return panel;
	}

	/**
	 * Carga la imagen actual del usuario si existe.
	 */
	private void cargarImagenActual() {
		// Aquí deberías obtener la imagen actual del usuario desde el controlador
		ImageIcon imagen = controlador.getImagenUsuarioActual();

		if (imagen != null) {
			imagenActual = redimensionarImagen(imagen, TAMAÑO_IMAGEN, TAMAÑO_IMAGEN);
			labelImagen.setIcon(imagenActual);
		} else {
			// Si no hay imagen, mostrar un icono por defecto
			ImageIcon iconoDefecto = new ImageIcon(
					VentanaEditarPerfil.class.getResource("/umu/tds/resources/usuario.png"));
			imagenActual = redimensionarImagen(iconoDefecto, TAMAÑO_IMAGEN, TAMAÑO_IMAGEN);
			labelImagen.setIcon(imagenActual);
		}
	}

	/**
	 * Abre un diálogo para seleccionar una imagen desde el sistema de archivos.
	 */
	private void seleccionarImagen() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Seleccionar imagen");
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de imagen", "jpg", "jpeg", "png", "gif");
		fileChooser.setFileFilter(filtro);

		int resultado = fileChooser.showOpenDialog(this);

		if (resultado == JFileChooser.APPROVE_OPTION) {
			imagenSeleccionada = fileChooser.getSelectedFile();
			try {
				BufferedImage img = ImageIO.read(imagenSeleccionada);
				if (img != null) {
					ImageIcon icono = new ImageIcon(img);
					ImageIcon iconoRedimensionado = redimensionarImagen(icono, TAMAÑO_IMAGEN, TAMAÑO_IMAGEN);
					labelImagen.setIcon(iconoRedimensionado);
				} else {
					JOptionPane.showMessageDialog(this, "El archivo seleccionado no es una imagen válida.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Guarda la imagen seleccionada como imagen de perfil del usuario.
	 */
	private void guardarImagen() {
		if (imagenSeleccionada != null) {
			boolean resultado = controlador.cambiarImagenUsuario(imagenSeleccionada.getAbsolutePath());
			if (resultado) {
				JOptionPane.showMessageDialog(this, "Imagen de perfil actualizada correctamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Error al actualizar la imagen de perfil.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (labelImagen.getIcon() == imagenActual) {
			// Si no se seleccionó ninguna imagen y se mantiene la actual
			dispose();
		} else {
			JOptionPane.showMessageDialog(this, "No se ha seleccionado ninguna imagen.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Redimensiona una imagen al tamaño especificado.
	 *
	 * @param original Imagen original a redimensionar.
	 * @param ancho    Ancho deseado.
	 * @param alto     Alto deseado.
	 * @return Imagen redimensionada.
	 */
	private ImageIcon redimensionarImagen(ImageIcon original, int ancho, int alto) {
		Image img = original.getImage();
		Image imgRedimensionada = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
		return new ImageIcon(imgRedimensionada);
	}
}