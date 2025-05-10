package umu.tds.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import umu.tds.controlador.AppChat;
import umu.tds.model.Contacto;
import umu.tds.model.ContactoIndividual;
import umu.tds.model.Grupo;
import umu.tds.model.Mensaje;
import umu.tds.model.Usuario;

import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Ventana para buscar mensajes aplicando diferentes criterios de filtrado.
 */
public class BuscarMensajes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldTexto;
	private JTextField textFieldTelefono;
	private JTextField textFieldContacto;
	private JTable tablaMensajes;
	private DefaultTableModel modeloTabla;
	private DateTimeFormatter formatter;
	private AppChat controlador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BuscarMensajes frame = new BuscarMensajes();
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
	public BuscarMensajes() {
		controlador = AppChat.getInstance();
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		setTitle("Buscar Mensajes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		// Panel principal
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		// Panel superior con icono
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel
				.setIcon(new ImageIcon(BuscarMensajes.class.getResource("/umu/tds/resources/mensaje-de-busqueda.png")));
		panel_1.add(lblNewLabel);

		// Panel central
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		// Panel de búsqueda
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new BorderLayout(0, 0));

		// Panel campos
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.CENTER);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 0, 168, 0, 0, 0, 0 };
		gbl_panel_5.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_5.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_5.setLayout(gbl_panel_5);

		// Campo de texto
		JLabel lblTexto = new JLabel("Texto:");
		GridBagConstraints gbc_lblTexto = new GridBagConstraints();
		gbc_lblTexto.insets = new Insets(0, 0, 5, 5);
		gbc_lblTexto.anchor = GridBagConstraints.EAST;
		gbc_lblTexto.gridx = 0;
		gbc_lblTexto.gridy = 0;
		panel_5.add(lblTexto, gbc_lblTexto);

		textFieldTexto = new JTextField();
		GridBagConstraints gbc_textFieldTexto = new GridBagConstraints();
		gbc_textFieldTexto.gridwidth = 4;
		gbc_textFieldTexto.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldTexto.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTexto.gridx = 1;
		gbc_textFieldTexto.gridy = 0;
		panel_5.add(textFieldTexto, gbc_textFieldTexto);
		textFieldTexto.setColumns(10);

		// Campo de teléfono
		JLabel lblTelefono = new JLabel("Telefono:");
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();
		gbc_lblTelefono.insets = new Insets(0, 0, 0, 5);
		gbc_lblTelefono.anchor = GridBagConstraints.EAST;
		gbc_lblTelefono.gridx = 0;
		gbc_lblTelefono.gridy = 1;
		panel_5.add(lblTelefono, gbc_lblTelefono);

		textFieldTelefono = new JTextField();
		GridBagConstraints gbc_textFieldTelefono = new GridBagConstraints();
		gbc_textFieldTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTelefono.insets = new Insets(0, 0, 0, 5);
		gbc_textFieldTelefono.gridx = 1;
		gbc_textFieldTelefono.gridy = 1;
		panel_5.add(textFieldTelefono, gbc_textFieldTelefono);
		textFieldTelefono.setColumns(10);

		// Campo de contacto
		JLabel lblContacto = new JLabel("Contacto:");
		GridBagConstraints gbc_lblContacto = new GridBagConstraints();
		gbc_lblContacto.insets = new Insets(0, 0, 0, 5);
		gbc_lblContacto.anchor = GridBagConstraints.EAST;
		gbc_lblContacto.gridx = 2;
		gbc_lblContacto.gridy = 1;
		panel_5.add(lblContacto, gbc_lblContacto);

		textFieldContacto = new JTextField();
		GridBagConstraints gbc_textFieldContacto = new GridBagConstraints();
		gbc_textFieldContacto.insets = new Insets(0, 0, 0, 5);
		gbc_textFieldContacto.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldContacto.gridx = 3;
		gbc_textFieldContacto.gridy = 1;
		panel_5.add(textFieldContacto, gbc_textFieldContacto);
		textFieldContacto.setColumns(10);

		// Boton de búsqueda
		JButton btnBuscar = new JButton("Search");
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.gridx = 4;
		gbc_btnBuscar.gridy = 1;
		panel_5.add(btnBuscar, gbc_btnBuscar);

		// Panel de resultados
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Resultados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));

		// Tabla de resultados
		JScrollPane scrollPane = new JScrollPane();
		panel_4.add(scrollPane, BorderLayout.CENTER);

		String[] columnNames = { "Fecha", "Emisor", "Receptor", "Texto" };
		modeloTabla = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaMensajes = new JTable(modeloTabla);
		tablaMensajes.getColumnModel().getColumn(0).setPreferredWidth(100);
		tablaMensajes.getColumnModel().getColumn(1).setPreferredWidth(100);
		tablaMensajes.getColumnModel().getColumn(2).setPreferredWidth(100);
		tablaMensajes.getColumnModel().getColumn(3).setPreferredWidth(300);
		scrollPane.setViewportView(tablaMensajes);

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarMensajes();
			}
		});

		pack();
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
	}

	/**
	 * Realiza la búsqueda de mensajes según los criterios especificados y muestra
	 * los resultados en la tabla
	 */
	private void buscarMensajes() {
		String texto = textFieldTexto.getText().trim();
		String telefono = textFieldTelefono.getText().trim();
		String contacto = textFieldContacto.getText().trim();

		// Limpiar tabla anterior
		modeloTabla.setRowCount(0);

		try {
			Usuario currentUser = controlador.getCurrentUser();
			List<Mensaje> mensajesEncontrados = controlador.buscarMensajes(texto.isEmpty() ? null : texto,
					telefono.isEmpty() ? null : telefono, contacto.isEmpty() ? null : contacto);

			// Mostrar resultados en la tabla
			for (Mensaje mensaje : mensajesEncontrados) {
				Object[] fila = new Object[4];

				// Columna 1: Fecha y hora formateada
				fila[0] = mensaje.getFechaHora().format(formatter);

				// Columna 2: Emisor
				// Si el emisor es el usuario actual, mostrar "Yo"
				if (mensaje.getEmisor().equals(currentUser)) {
					fila[1] = "yo";
				} else {
					// Si no, mostrar el nombre del emisor
					fila[1] = mensaje.getEmisor().getName();
				}

				// Columna 3: Receptor
				if (mensaje.getReceptor() instanceof ContactoIndividual) {
					ContactoIndividual receptor = (ContactoIndividual) mensaje.getReceptor();
					Usuario usuarioReceptor = receptor.getUsuario();

					// Si el receptor es el usuario actual, mostrar "Yo"
					if (usuarioReceptor.equals(currentUser)) {
						fila[2] = "Yo";
					} else {
						// Si no, mostrar el nombre del contacto
						fila[2] = receptor.getNombre();
					}

				} else if (mensaje.getReceptor() instanceof Grupo) {
					// Si es un grupo, mostramos el nombre del grupo
					Grupo grupo = (Grupo) mensaje.getReceptor();
					fila[2] = "Grupo: " + grupo.getNombre();
				} else {
					// Para cualquier otro tipo de contacto
					fila[2] = mensaje.getReceptor().getNombre();
				}

				// Columna 4: Contenido del mensaje
				fila[3] = mensaje.getTexto();

				modeloTabla.addRow(fila);
			}

			if (mensajesEncontrados.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No se encontraron mensajes con los criterios especificados",
						"Información", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}