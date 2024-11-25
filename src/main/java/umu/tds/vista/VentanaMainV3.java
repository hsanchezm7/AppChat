package umu.tds.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Component;
import javax.swing.Box;

public class VentanaMainV3 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMainV3 frame = new VentanaMainV3();
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
	public VentanaMainV3() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 484, 329);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("99px"),
				ColumnSpec.decode("32px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(2dlu;default)"),
				ColumnSpec.decode("32px"),
				ColumnSpec.decode("99px"),
				ColumnSpec.decode("233px"),
				ColumnSpec.decode("285px"),},
			new RowSpec[] {
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("36px"),}));
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Contacto1", "Contacto2", "Contacto3", "Contacto4"}));
		panel.add(comboBox, "2, 2, center, center");
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(VentanaMainV3.class.getResource("/umu/tds/resources/enviar.png")));
		panel.add(btnNewButton, "3, 2, left, center");
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(VentanaMainV3.class.getResource("/umu/tds/resources/analisis-de-busqueda.png")));
		panel.add(btnNewButton_1, "6, 2, left, center");
		
		JButton btnNewButton_2 = new JButton("Contactos");
		btnNewButton_2.setIcon(new ImageIcon(VentanaMainV3.class.getResource("/umu/tds/resources/contacto-2.png")));
		panel.add(btnNewButton_2, "7, 2, left, center");
		
		JButton btnNewButton_3 = new JButton("Premium");
		btnNewButton_3.setIcon(new ImageIcon(VentanaMainV3.class.getResource("/umu/tds/resources/calidad-premium.png")));
		panel.add(btnNewButton_3, "8, 2, left, center");
		
		JLabel lblNewLabel = new JLabel("usuario actual");
		lblNewLabel.setIcon(new ImageIcon(VentanaMainV3.class.getResource("/umu/tds/resources/usuario.png")));
		panel.add(lblNewLabel, "9, 2, left, center");
	}

}
