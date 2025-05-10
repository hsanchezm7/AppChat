package umu.tds.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import tds.BubbleText;
import umu.tds.controlador.AppChat;
import umu.tds.model.Contacto;
import umu.tds.model.Mensaje;

public class PanelChat extends JPanel {

	private static final long serialVersionUID = 1L;

	// Componentes UI
    private JPanel panelMensajes;
    private JTextField mensajeTextField;
    private JScrollPane chatScrollPane;
    private JButton btnSend;
    private JButton btnEmoji;
    private TitledBorder titledBorder;
    
    // Estado actual
    private Contacto contactoActual;
    private AppChat controlador;
    
    /**
     * Constructor del panel de chat
     */
    public PanelChat() {
        this.controlador = AppChat.getInstance();
        initComponents();
    }
    
    /**
     * Inicializa los componentes del panel
     */
    private void initComponents() {
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 0, 0, 0 };
        gbl.rowHeights = new int[] { 0, 0, 0 };
        gbl.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
        gbl.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
        setLayout(gbl);
        
        titledBorder = new TitledBorder(null, " Chat ", TitledBorder.LEADING, TitledBorder.TOP, null, null);
        setBorder(titledBorder);
        
        // Panel de chat con scroll
        chatScrollPane = new JScrollPane();
        chatScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        chatScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatScrollPane.setBorder(null);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 3;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;
        add(chatScrollPane, gbc_scrollPane);
        
        panelMensajes = new JPanel();
        panelMensajes.setLayout(new BoxLayout(panelMensajes, BoxLayout.Y_AXIS));
        chatScrollPane.setViewportView(panelMensajes);
        panelMensajes.setBackground(Color.WHITE);
        
        // Panel inferior para enviar mensajes
        JPanel panelEnvio = new JPanel();
        GridBagConstraints gbc_panelEnvio = new GridBagConstraints();
        gbc_panelEnvio.gridwidth = 3;
        gbc_panelEnvio.fill = GridBagConstraints.BOTH;
        gbc_panelEnvio.gridx = 0;
        gbc_panelEnvio.gridy = 1;
        add(panelEnvio, gbc_panelEnvio);
        
        GridBagLayout gbl_panelEnvio = new GridBagLayout();
        gbl_panelEnvio.columnWidths = new int[] { 40, 0, 40, 0 };
        gbl_panelEnvio.rowHeights = new int[] { 0, 0 };
        gbl_panelEnvio.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        gbl_panelEnvio.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        panelEnvio.setLayout(gbl_panelEnvio);
        
        // Botón de emoji
        btnEmoji = new JButton("");
        btnEmoji.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/feliz.png")));
        GridBagConstraints gbc_btnEmoji = new GridBagConstraints();
        gbc_btnEmoji.insets = new Insets(0, 0, 0, 5);
        gbc_btnEmoji.gridx = 0;
        gbc_btnEmoji.gridy = 0;
        panelEnvio.add(btnEmoji, gbc_btnEmoji);
        
        // Configuración del menú popup para emojis
        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setLayout(new GridLayout(6, 4));
        addPopup(btnEmoji, popupMenu);
        
        btnEmoji.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        
        // Añadir los botones de emoji al menú popup
        for (int i = 0; i <= BubbleText.MAXICONO; i++) {
            final int emoji = i;
            JButton btn = new JButton();
            btn.setIcon(BubbleText.getEmoji(i));
            popupMenu.add(btn);
            btn.addActionListener(e -> enviarMensajeEmoji(emoji));
        }
        
        // Campo de texto para escribir mensajes
        mensajeTextField = new JTextField();
        GridBagConstraints gbc_mensajeTextField = new GridBagConstraints();
        gbc_mensajeTextField.insets = new Insets(0, 0, 0, 5);
        gbc_mensajeTextField.fill = GridBagConstraints.BOTH;
        gbc_mensajeTextField.gridx = 1;
        gbc_mensajeTextField.gridy = 0;
        panelEnvio.add(mensajeTextField, gbc_mensajeTextField);
        mensajeTextField.setColumns(10);
        
        // Botón enviar mensaje
        btnSend = new JButton("");
        btnSend.setIcon(new ImageIcon(VentanaMain.class.getResource("/umu/tds/resources/send.png")));
        GridBagConstraints gbc_btnSend = new GridBagConstraints();
        gbc_btnSend.gridx = 2;
        gbc_btnSend.gridy = 0;
        panelEnvio.add(btnSend, gbc_btnSend);
        btnSend.addActionListener(e -> enviarMensajeTexto());
        
        // Añadir listener para enviar mensaje al presionar Enter
        mensajeTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    enviarMensajeTexto();
                }
            }
        });
    }
    
    /**
     * Carga la conversación con un contacto específico
     * @param contacto El contacto con el que se desea cargar la conversación
     */
    public void cargarConversacion(Contacto contacto) {
        if (contacto == null) {
            return;
        }
        
        this.contactoActual = contacto;
        panelMensajes.removeAll();
        titledBorder.setTitle(" Chat con " + contacto.getNombre());
        repaint();
        
        List<Mensaje> mensajes = controlador.getMensajesConContacto(contacto);
        if (mensajes == null || mensajes.isEmpty()) {
            mensajes = Collections.emptyList();
        }
        
        mensajes.stream().map(m -> {
            if (controlador.esMensajeDelUsuarioActual(m)) {
                if (m.getEmoticono() >= 0) {
                    return new BubbleText(panelMensajes, m.getEmoticono(), Color.GREEN, "Yo", BubbleText.SENT, 18);
                } else {
                    return new BubbleText(panelMensajes, m.getTexto(), Color.GREEN, "Yo", BubbleText.SENT);
                }
            } else {
                if (m.getEmoticono() >= 0) {
                    return new BubbleText(panelMensajes, m.getEmoticono(), Color.GREEN, contacto.getNombre(), BubbleText.RECEIVED, 18);
                } else {
                    return new BubbleText(panelMensajes, m.getTexto(), Color.GREEN, contacto.getNombre(), BubbleText.RECEIVED);
                }
            }
        }).forEach(b -> panelMensajes.add(b));
        
        panelMensajes.revalidate();
        panelMensajes.repaint();
        desplazarHaciaAbajo();
    }
    
    /**
     * Envía un mensaje de texto al contacto actual
     */
    private void enviarMensajeTexto() {
        if (contactoActual == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un contacto para enviar el mensaje", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (mensajeTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El mensaje no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean enviado = controlador.sendMessage(mensajeTextField.getText(), contactoActual);
        
        if (enviado) {
            BubbleText burbuja = new BubbleText(panelMensajes, mensajeTextField.getText(), Color.GREEN, "Yo", BubbleText.SENT);
            panelMensajes.add(burbuja);
            mensajeTextField.setText(null);
            
            panelMensajes.revalidate();
            panelMensajes.repaint();
            desplazarHaciaAbajo();
            
            // Notificar que se ha enviado un mensaje
            firePropertyChange("mensajeEnviado", null, contactoActual);
        }
    }
    
    /**
     * Envía un emoji al contacto actual
     * @param emojiId Identificador del emoji a enviar
     */
    private void enviarMensajeEmoji(int emojiId) {
        if (contactoActual == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un contacto para enviar el emoji", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean enviado = controlador.sendEmoji(emojiId, contactoActual);
        
        if (enviado) {
            BubbleText burbuja = new BubbleText(panelMensajes, emojiId, Color.GREEN, "Yo", BubbleText.SENT, 18);
            panelMensajes.add(burbuja);
            
            panelMensajes.revalidate();
            panelMensajes.repaint();
            desplazarHaciaAbajo();
            
            // Notificar que se ha enviado un mensaje
            firePropertyChange("mensajeEnviado", null, contactoActual);
        }
    }
    
    /**
     * Desplaza el scroll del panel de la conversación hacia abajo (último mensaje)
     */
    private void desplazarHaciaAbajo() {
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = chatScrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }
    
    /**
     * Establece el TitledBorder del panel
     * @param border Borde con título
     */
    public void setTitledBorder(TitledBorder border) {
        this.titledBorder = border;
        setBorder(border);
    }
    
    /**
     * Obtiene el TitledBorder del panel
     * @return Borde con título actual
     */
    public TitledBorder getTitledBorder() {
        return this.titledBorder;
    }
    
    /**
     * Limpia el panel de chat
     */
    public void limpiarChat() {
        panelMensajes.removeAll();
        titledBorder.setTitle(" Chat ");
        repaint();
        panelMensajes.revalidate();
    }
    
    /**
     * Método auxiliar para añadir un menú popup a un componente
     * @param component Componente al que se añade el popup
     * @param popup Menú popup a añadir
     */
    private static void addPopup(Component component, final JPopupMenu popup) {
        component.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showMenu(e);
                }
            }
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showMenu(e);
                }
            }
            private void showMenu(MouseEvent e) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }

}
