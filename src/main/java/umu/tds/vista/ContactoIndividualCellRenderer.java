package umu.tds.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import umu.tds.model.Contacto;

public class ContactoIndividualCellRenderer extends JPanel implements ListCellRenderer<Contacto> {
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