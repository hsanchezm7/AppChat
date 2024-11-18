package umu.tds.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;

import javax.swing.border.LineBorder;
import java.awt.Color;

public class VentanaMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMain frame = new VentanaMain();
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
	public VentanaMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 336);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		contentPane.add(panelNorth, BorderLayout.NORTH);
		panelNorth.setLayout(new BoxLayout(panelNorth, BoxLayout.X_AXIS));
		
		JComboBox comboBoxContacts = new JComboBox();
		comboBoxContacts.setModel(new DefaultComboBoxModel(new String[] {"Contacto1", "Contacto2", "Contacto3", "Contacto4"}));
		panelNorth.add(comboBoxContacts);
		
		JButton btnSend = new JButton("");
		btnSend.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSend.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/send.png")));
		panelNorth.add(btnSend);
		
		JButton btnSearch = new JButton("");
		btnSearch.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/magnifier.png")));
		btnSearch.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelNorth.add(btnSearch);
		
		JButton btnContacts = new JButton("Contactos");
		btnContacts.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelNorth.add(btnContacts);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panelNorth.add(horizontalGlue);
		
		JLabel lblNombreDeUsuario = new JLabel("Nombre de usuario");
		lblNombreDeUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelNorth.add(lblNombreDeUsuario);
		
		JPanel panelWest = new JPanel();
		contentPane.add(panelWest, BorderLayout.WEST);
		panelWest.setLayout(new BorderLayout(0, 0));
	}

}
