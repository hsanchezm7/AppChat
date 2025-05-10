package umu.tds.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;

import umu.tds.controlador.AppChat;
import umu.tds.model.Contacto;
import umu.tds.model.ContactoIndividual;
import umu.tds.model.Grupo;
import umu.tds.model.Usuario;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class VentanaContactos extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final String NOMBRE_VENTANA = "Gestionar contactos";
	
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JScrollPane scrollPaneSeleccionados;
	private DefaultListModel<String> modeloSeleccionados = new DefaultListModel<>();
	private List<String> seleccionados;


	/**
	 * Create the frame.
	 */
	public VentanaContactos(JFrame owner) {
		super(owner, NOMBRE_VENTANA, true); // Bloquea la ventana padre hasta que ésta se cierre
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 682, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "lista contactos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 276, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		///

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel_1.add(scrollPane, gbc_scrollPane);

		JPanel panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.insets = new Insets(0, 0, 5, 5);
		gbc_panel_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_6.gridx = 1;
		gbc_panel_6.gridy = 0;
		panel_1.add(panel_6, gbc_panel_6);
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[] { 0, 0 };
		gbl_panel_6.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_6.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_6.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_6.setLayout(gbl_panel_6);

		JButton button_1 = new JButton(">>");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				moverContactoAListaGrupo();
			}
		});
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 0);
		gbc_button_1.gridx = 0;
		gbc_button_1.gridy = 3;
		panel_6.add(button_1, gbc_button_1);

		JButton btnNewButton_1 = new JButton("<<");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removerContactoDeListaGrupo();
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 4;
		panel_6.add(btnNewButton_1, gbc_btnNewButton_1);

		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 2;
		gbc_panel_3.gridy = 0;
		panel_1.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Grupo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.add(panel_4, BorderLayout.CENTER);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		scrollPaneSeleccionados = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		actualizarListaContactos();

		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panel_4.add(scrollPaneSeleccionados, gbc_scrollPane_1);

		JButton btnAddContact = new JButton("Add Contact");
		btnAddContact.addActionListener(e -> openContacts());

		GridBagConstraints gbc_btnAddContact = new GridBagConstraints();
		gbc_btnAddContact.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddContact.gridx = 0;
		gbc_btnAddContact.gridy = 1;
		panel_1.add(btnAddContact, gbc_btnAddContact);

		JButton btnNewButton = new JButton("Add Group");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearGrupo();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 1;
		panel_1.add(btnNewButton, gbc_btnNewButton);

		pack();
		setResizable(true);
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
	}

	private void actualizarListaContactos() {
		List<Contacto> contactos = AppChat.getInstance().getCurrentUser().getContactos();
		
		List<String> nombresContactos = contactos.stream()
		        .filter(contacto -> contacto instanceof ContactoIndividual)
		        .map(Contacto::getNombre)
		        .collect(Collectors.toList());

		JList<String> list = new JList<>(nombresContactos.toArray(new String[0]));
		scrollPane.setViewportView(list);
	}

	private void moverContactoAListaGrupo() {
		JList<String> listaContactos = (JList<String>) scrollPane.getViewport().getView();

		seleccionados = listaContactos.getSelectedValuesList();
		
		List<Contacto> contactos = AppChat.getInstance().getCurrentUser().getContactos();

		for (String contacto : seleccionados) {
			// Validar que el contacto es individual
			Contacto contact = contactos.stream()
					.filter(c -> c instanceof ContactoIndividual && c.getNombre().equals(contacto))
					.findFirst()
					.orElse(null);
			
			if ( contact != null && !modeloSeleccionados.contains(contacto)) {
				modeloSeleccionados.addElement(contacto);
			}
		}

		JList<String> listSeleccionados = new JList<>(modeloSeleccionados);
		scrollPaneSeleccionados.setViewportView(listSeleccionados);

	}
	
	private void removerContactoDeListaGrupo() {
	    JList<String> listaSeleccionados = (JList<String>) scrollPaneSeleccionados.getViewport().getView();

	    	    List<String> seleccionadosParaRemover = listaSeleccionados.getSelectedValuesList();

	    for (String contacto : seleccionadosParaRemover) {
	        modeloSeleccionados.removeElement(contacto);
	    }

	    JList<String> nuevaListaSeleccionados = new JList<>(modeloSeleccionados);
	    scrollPaneSeleccionados.setViewportView(nuevaListaSeleccionados);
	}


	private void crearGrupo() {
		
		// Obtener el nombre del grupo del usuario
        String nombreGrupo = JOptionPane.showInputDialog(this, "Introduce el nombre del grupo:", "Nombre del Grupo", JOptionPane.PLAIN_MESSAGE);

        // Validar el nombre del grupo
        if (nombreGrupo == null || nombreGrupo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del grupo no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener la URL de la imagen del grupo (opcional)
        String imagenGrupoURL = JOptionPane.showInputDialog(this, "Introduce la URL de la imagen del grupo (opcional):", "Imagen del Grupo", JOptionPane.PLAIN_MESSAGE);

        // Obtener el administrador del grupo (usuario actual)
        //Usuario administrador = AppChat.getInstance().getCurrentUser();

        // Obtener los miembros seleccionados
        List<ContactoIndividual> miembros = obtenerMiembrosSeleccionados();

        // Validar que haya al menos un miembro en el grupo
        if (miembros.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar al menos un miembro para el grupo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Añadir el grupo
        boolean addGrupo = AppChat.getInstance().addGrupo(nombreGrupo, miembros, imagenGrupoURL);

        if (addGrupo) {
            JOptionPane.showMessageDialog(this, "Grupo añadido correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error al añadir el grupo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        actualizarListaContactos();
    }

	private List<ContactoIndividual> obtenerMiembrosSeleccionados() {
        List<Contacto> contactos = AppChat.getInstance().getCurrentUser().getContactos();
        return contactos.stream()
                .filter(contacto -> contacto instanceof ContactoIndividual && seleccionados.contains(contacto.getNombre()))
                .map(contacto -> (ContactoIndividual) contacto)
                .collect(Collectors.toList());
    }

	private void openContacts() {
		AñadirContactos ventanaContactos = new AñadirContactos(this);
		ventanaContactos.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				actualizarListaContactos();
			}
		});
		ventanaContactos.setVisible(true);
	}

}
