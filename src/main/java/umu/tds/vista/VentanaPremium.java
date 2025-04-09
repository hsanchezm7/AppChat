package umu.tds.vista;

import javax.swing.*;
import javax.swing.border.*;

import umu.tds.controlador.AppChat;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class VentanaPremium extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private static final String NOMBRE_VENTANA = "AppChat Premium";
    
    private static final double PRECIO_BASE = 9.99; 
    private static final String[] METODOS_PAGO = {"Tarjeta de crédito/débito", "PayPal", "Bizum"};
    
 // Constantes para los índices o strings (mejor usar los strings directamente)
    private static final String PAGO_TARJETA = "Tarjeta de crédito/débito";
    private static final String PAGO_PAYPAL = "PayPal";
    private static final String PAGO_BIZUM = "Bizum";
    
    // Componentes del método de pago
    private JComboBox<String> comboMetodoPago;
    private JPanel panelFormularioPago;
    
    // Componentes de Tarjeta de Crédito/Débito
    private JTextField campoNumeroTarjeta;
    private JTextField campoTitularTarjeta;
    private JTextField campoFechaExpiracion;
    private JPasswordField campoCVV;
    
    // Componentes de PayPal
    private JTextField campoEmailPaypal;
    private JPasswordField campoPasswordPaypal;
    
    // Componentes de Bizum
    private JTextField campoNumeroTelefono;
    
    // Componentes de descuento
    private JComboBox<String> comboDescuento;
    private JLabel etiquetaPrecio;
    private double precioActual;
    
    // Mapa de descuentos disponibles (nombre del descuento -> porcentaje de descuento)
    private Map<String, Double> descuentosDisponibles;
    
    public VentanaPremium(JFrame owner) {
        super(owner, NOMBRE_VENTANA, true); // Bloquea la ventana padre hasta que esta se cierre
        initDiscounts();
        initComponents();
    }
    
    private void initDiscounts() {
        descuentosDisponibles = new HashMap<>();
        descuentosDisponibles.put("Sin descuento", 0.0);
        descuentosDisponibles.put("Estudiante (20%)", 0.2);
        descuentosDisponibles.put("Usuario nuevo (15%)", 0.15);
        descuentosDisponibles.put("Promoción primavera (10%)", 0.1);
        
        precioActual = PRECIO_BASE;
    }
    
    public void initComponents() {
        // Propiedades de la ventana
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        ImageIcon img = new ImageIcon("/umu/tds/resources/appchat_premium_128.png");
        setIconImage(img.getImage());
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        JPanel panelLogo = crearPanelLogo();
        getContentPane().add(panelLogo, BorderLayout.NORTH);
        
        JPanel panelCentro = crearPanelFormulario();
        getContentPane().add(panelCentro, BorderLayout.CENTER);
        
        JPanel panelBotones = crearPanelBotones();
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
        
        pack();
        setResizable(true);
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
    }
    
    public JPanel crearPanelLogo() {
        JPanel panelLogo = new JPanel();
        panelLogo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel lblLogo = new JLabel("");
        lblLogo.setIcon(new ImageIcon(VentanaPremium.class.getResource("/umu/tds/resources/appchat_premium_128.png")));
        panelLogo.add(lblLogo);
        
        return panelLogo;
    }
    
    public JPanel crearPanelFormulario() {
        JPanel panelFormulario = new JPanel();
        panelFormulario.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelFormulario.setLayout(new BorderLayout(0, 0));
        
        JPanel panelWrapperFormulario = new JPanel();
        panelWrapperFormulario.setBorder(
                new TitledBorder(null, "  Suscripción AppChat Premium  ", TitledBorder.CENTER, TitledBorder.TOP, null, null));
        panelFormulario.add(panelWrapperFormulario, BorderLayout.CENTER);
        panelWrapperFormulario.setLayout(new BorderLayout(0, 0));
        
        JPanel mainPanel = new JPanel();
        panelWrapperFormulario.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BorderLayout(0, 0));
        
        // Panel de precio y descuento
        JPanel panelPrecioDescuento = crearPanelPrecioDescuento();
        mainPanel.add(panelPrecioDescuento, BorderLayout.NORTH);
        
        // Panel de pago
        JPanel panelPago = new JPanel();
        panelPago.setLayout(new BorderLayout(0, 0));
        mainPanel.add(panelPago, BorderLayout.CENTER);
        
        // Panel selector de método de pago
        JPanel panelSelector = new JPanel();
        panelSelector.setBorder(new EmptyBorder(10, 0, 10, 0));
        panelPago.add(panelSelector, BorderLayout.NORTH);
        
        panelSelector.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 5);
        
        JLabel etiquetaMetodoPago = new JLabel("Seleccione método de pago:");
        panelSelector.add(etiquetaMetodoPago, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        comboMetodoPago = new JComboBox<>(METODOS_PAGO);
        panelSelector.add(comboMetodoPago, gbc);
        
        // Panel del formulario de pago
        panelFormularioPago = new JPanel();
        panelPago.add(panelFormularioPago, BorderLayout.CENTER);
        panelFormularioPago.setLayout(new CardLayout());
        
        // Crear paneles de formulario para cada método de pago
        JPanel panelTarjeta = crearPanelTarjeta();
        JPanel panelPaypal = crearPanelPaypal();
        JPanel panelBizum = crearPanelBizum();
        
        panelFormularioPago.add(panelTarjeta, "Tarjeta de crédito/débito");
        panelFormularioPago.add(panelPaypal, "PayPal");
        panelFormularioPago.add(panelBizum, "Bizum");
        
        // Añadir listener para cambiar el formulario según la selección
        comboMetodoPago.addActionListener(e -> {
            CardLayout cl = (CardLayout) panelFormularioPago.getLayout();
            cl.show(panelFormularioPago, (String) comboMetodoPago.getSelectedItem());
        });
        
        return panelFormulario;
    }
    
    private JPanel crearPanelPrecioDescuento() {
        JPanel panelPrecioDescuento = new JPanel();
        panelPrecioDescuento.setBorder(new EmptyBorder(0, 0, 10, 0));
        panelPrecioDescuento.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        
        // Información de precio
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel etiquetaPrecioBase = new JLabel("Precio de suscripción:");
        etiquetaPrecioBase.setFont(new Font(etiquetaPrecioBase.getFont().getName(), Font.BOLD, 14));
        panelPrecioDescuento.add(etiquetaPrecioBase, gbc);
        
        gbc.gridx = 1;
        DecimalFormat df = new DecimalFormat("0.00€");
        String precioFormateado = df.format(PRECIO_BASE);
        JLabel valorPrecioBase = new JLabel(precioFormateado);
        valorPrecioBase.setFont(new Font(valorPrecioBase.getFont().getName(), Font.BOLD, 14));
        panelPrecioDescuento.add(valorPrecioBase, gbc);
        
        // Descuentos disponibles
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 5, 5);
        JLabel etiquetaDescuento = new JLabel("Descuento disponible:");
        panelPrecioDescuento.add(etiquetaDescuento, gbc);
        
        gbc.gridx = 1;
        comboDescuento = new JComboBox<>();
        for (String descuento : descuentosDisponibles.keySet()) {
            comboDescuento.addItem(descuento);
        }
        panelPrecioDescuento.add(comboDescuento, gbc);
        
        // Precio final
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 0, 5, 5);
        JLabel etiquetaPrecioFinal = new JLabel("Precio final:");
        etiquetaPrecioFinal.setFont(new Font(etiquetaPrecioFinal.getFont().getName(), Font.BOLD, 14));
        panelPrecioDescuento.add(etiquetaPrecioFinal, gbc);
        
        gbc.gridx = 1;
        etiquetaPrecio = new JLabel(precioFormateado);
        etiquetaPrecio.setFont(new Font(etiquetaPrecio.getFont().getName(), Font.BOLD, 14));
        etiquetaPrecio.setForeground(new Color(0, 128, 0)); // Color verde para el precio
        panelPrecioDescuento.add(etiquetaPrecio, gbc);
        
        // Añadir listener para actualizar el precio cuando cambia el descuento
        comboDescuento.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                actualizarPrecio();
            }
        });
        
        return panelPrecioDescuento;
    }
    
    /**
     * Actualiza el precio mostrado según el descuento seleccionado
     */
    private void actualizarPrecio() {
        String descuentoSeleccionado = (String) comboDescuento.getSelectedItem();
        double porcentajeDescuento = descuentosDisponibles.get(descuentoSeleccionado);
        precioActual = PRECIO_BASE * (1 - porcentajeDescuento);
        
        DecimalFormat df = new DecimalFormat("0.00€");
        etiquetaPrecio.setText(df.format(precioActual));
    }
    
    private JPanel crearPanelTarjeta() {
        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 0, 0, 0 };
        gbl.rowHeights = new int[] { 0, 0, 0, 0, 0 };
        gbl.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        panelTarjeta.setLayout(gbl);
        
        // Número de tarjeta
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        JLabel etiquetaNumeroTarjeta = new JLabel("Número de tarjeta:");
        panelTarjeta.add(etiquetaNumeroTarjeta, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        campoNumeroTarjeta = new JTextField();
        panelTarjeta.add(campoNumeroTarjeta, gbc);
        campoNumeroTarjeta.setColumns(16);
        
        // Titular de la tarjeta
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        
        JLabel etiquetaTitularTarjeta = new JLabel("Titular de la tarjeta:");
        panelTarjeta.add(etiquetaTitularTarjeta, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        campoTitularTarjeta = new JTextField();
        panelTarjeta.add(campoTitularTarjeta, gbc);
        
        // Fecha de expiración
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        
        JLabel etiquetaFechaExpiracion = new JLabel("Fecha de expiración (MM/AA):");
        panelTarjeta.add(etiquetaFechaExpiracion, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        campoFechaExpiracion = new JTextField();
        panelTarjeta.add(campoFechaExpiracion, gbc);
        campoFechaExpiracion.setColumns(5);
        
        // CVV
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        
        JLabel etiquetaCVV = new JLabel("CVV:");
        panelTarjeta.add(etiquetaCVV, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        campoCVV = new JPasswordField();
        panelTarjeta.add(campoCVV, gbc);
        campoCVV.setColumns(3);
        
        return panelTarjeta;
    }
    

    private JPanel crearPanelPaypal() {
        JPanel panelPaypal = new JPanel();
        panelPaypal.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 0, 0, 0 };
        gbl.rowHeights = new int[] { 0, 0, 0 };
        gbl.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        panelPaypal.setLayout(gbl);
        
        // Email PayPal
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        JLabel etiquetaEmailPaypal = new JLabel("Correo electrónico de PayPal:");
        panelPaypal.add(etiquetaEmailPaypal, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        campoEmailPaypal = new JTextField();
        panelPaypal.add(campoEmailPaypal, gbc);
        
        // Contraseña PayPal
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        
        JLabel etiquetaPasswordPaypal = new JLabel("Contraseña de PayPal:");
        panelPaypal.add(etiquetaPasswordPaypal, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        campoPasswordPaypal = new JPasswordField();
        panelPaypal.add(campoPasswordPaypal, gbc);
        
        return panelPaypal;
    }
    
    /**
     * Crea el panel para el pago con Bizum
     * @return Panel con formulario de Bizum
     */
    private JPanel crearPanelBizum() {
        JPanel panelBizum = new JPanel();
        panelBizum.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 0, 0, 0 };
        gbl.rowHeights = new int[] { 0, 0 };
        gbl.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        panelBizum.setLayout(gbl);
        
        // Número de teléfono para Bizum
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        JLabel etiquetaNumeroTelefono = new JLabel("Número de teléfono:");
        panelBizum.add(etiquetaNumeroTelefono, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        campoNumeroTelefono = new JTextField();
        panelBizum.add(campoNumeroTelefono, gbc);
        
        return panelBizum;
    }
    
    /**
     * Crea el panel con los botones de acción
     * @return Panel con botones
     */
    public JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel();
        panelBotones.setBorder(new EmptyBorder(0, 10, 10, 10));
        panelBotones.setLayout(new BorderLayout(0, 0));
        
        JPanel panelBtnCancelar = new JPanel();
        panelBotones.add(panelBtnCancelar, BorderLayout.WEST);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBtnCancelar.add(btnCancelar);
        btnCancelar.addActionListener(e -> handleCancel());
        
        JPanel panelBtnConfirmarRegistro = new JPanel();
        panelBotones.add(panelBtnConfirmarRegistro, BorderLayout.EAST);
        
        JButton btnConfirmarRegistro = new JButton("Confirmar Pago");
        btnConfirmarRegistro.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBtnConfirmarRegistro.add(btnConfirmarRegistro);
        btnConfirmarRegistro.addActionListener(e -> handlePayment());
        
        getRootPane().setDefaultButton(btnConfirmarRegistro);
        
        return panelBotones;
    }
    

    private void handlePayment() {
        // TODO Llamar a lógica de controlador para actualizar a premium
    	if (!checkFields(this)) {
    		return;
    	}
        DecimalFormat df = new DecimalFormat("0.00€");
        String mensaje = "Procesando pago de " + df.format(precioActual) + 
                " con " + comboMetodoPago.getSelectedItem();
        
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Información de Pago",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        AppChat.getInstance().setUserPremiumStatus(true);
        System.out.println("Cambiado estado de usuario " + AppChat.getInstance().getCurrentUser().getPhone() + " a premium");
        
        dispose();
    }
    
        private void handleCancel() {
        String[] opciones = { "Continuar con el pago", "Mejor en otro momento" };
        int respuesta = JOptionPane.showOptionDialog(
            this,
            "¿Seguro que no quieres actualizar a premium?",
            "Confirmar cancelación",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null,
            opciones,
            opciones[1]
        );

        if (respuesta == 1) {
            dispose();
        }
    }
    
    /**
     * Establece los descuentos disponibles para el usuario actual
     * Este método debe ser llamado antes de mostrar el diálogo
     * @param descuentosElegibles Mapa de nombres de descuento y sus valores porcentuales
     */
    public void setAvailableDiscounts(Map<String, Double> descuentosElegibles) {
        comboDescuento.removeAllItems();
        
        // Siempre añadir la opción "Sin descuento"
        comboDescuento.addItem("Sin descuento");
        
        // Añadir descuentos elegibles
        for (Map.Entry<String, Double> entrada : descuentosElegibles.entrySet()) {
            comboDescuento.addItem(entrada.getKey());
        }
        
        // Actualizar el mapa de descuentos disponibles
        descuentosDisponibles.clear();
        descuentosDisponibles.put("Sin descuento", 0.0);
        descuentosDisponibles.putAll(descuentosElegibles);
        
        // Actualizar visualización del precio
        actualizarPrecio();
    }
    
    private boolean checkFields(Component owner) {
        String metodoSeleccionado = (String) comboMetodoPago.getSelectedItem();

        if (metodoSeleccionado == null) {
             JOptionPane.showMessageDialog(owner,
                    "Por favor, selecciona un método de pago.",
                    "Error de Validación",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        switch (metodoSeleccionado) {
            case PAGO_TARJETA:
                // Validación para Tarjeta de crédito/débito
                if (campoNumeroTarjeta.getText().trim().isEmpty()) {
                    mostrarError(owner, "El número de tarjeta no puede estar vacío.");
                    campoNumeroTarjeta.requestFocusInWindow(); // Poner foco en el campo erróneo
                    return false;
                }
                if (campoTitularTarjeta.getText().trim().isEmpty()) {
                    mostrarError(owner, "El titular de la tarjeta no puede estar vacío.");
                     campoTitularTarjeta.requestFocusInWindow();
                    return false;
                }
                if (campoFechaExpiracion.getText().trim().isEmpty()) {
                    mostrarError(owner, "La fecha de expiración no puede estar vacía.");
                    campoFechaExpiracion.requestFocusInWindow();
                    return false;
                }
                 // Para JPasswordField, se comprueba la longitud del array de chars
                if (campoCVV.getPassword().length == 0) {
                    mostrarError(owner, "El CVV no puede estar vacío.");
                    campoCVV.requestFocusInWindow();
                    return false;
                }
                // Aquí podrías añadir validaciones más específicas (formato de número, fecha, CVV)
                break; // Sale del switch si todo está bien para tarjeta

            case PAGO_PAYPAL:
                // Validación para PayPal
                if (campoEmailPaypal.getText().trim().isEmpty()) {
                    mostrarError(owner, "El email de PayPal no puede estar vacío.");
                    campoEmailPaypal.requestFocusInWindow();
                    return false;
                }
                 // Para JPasswordField, se comprueba la longitud del array de chars
                if (campoPasswordPaypal.getPassword().length == 0) {
                    mostrarError(owner, "La contraseña de PayPal no puede estar vacía.");
                    campoPasswordPaypal.requestFocusInWindow();
                    return false;
                }
                // Aquí podrías añadir validación de formato de email
                break; // Sale del switch si todo está bien para PayPal

            case PAGO_BIZUM:
                // Validación para Bizum
                if (campoNumeroTelefono.getText().trim().isEmpty()) {
                    mostrarError(owner, "El número de teléfono para Bizum no puede estar vacío.");
                    campoNumeroTelefono.requestFocusInWindow();
                    return false;
                }
                // Aquí podrías añadir validación de formato de número de teléfono (p.ej., 9 dígitos)
                break; // Sale del switch si todo está bien para Bizum

            default:
                // Caso improbable si el JComboBox está bien configurado
                 JOptionPane.showMessageDialog(owner,
                        "Método de pago desconocido seleccionado.",
                        "Error Interno",
                        JOptionPane.ERROR_MESSAGE);
                return false;
        }

        // Si ha pasado todas las validaciones para el método seleccionado
        return true;
    }

    /**
     * Método auxiliar para mostrar un mensaje de error estandarizado.
     * @param parentComponent El componente padre para el diálogo.
     * @param mensaje El mensaje de error a mostrar.
     */
    private void mostrarError(Component parentComponent, String mensaje) {
        JOptionPane.showMessageDialog(parentComponent,
                mensaje,
                "Error de Validación",
                JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Método principal para pruebas
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            VentanaPremium dialog = new VentanaPremium(null);
            
            // Ejemplo de establecer descuentos personalizados disponibles
            // En una aplicación real, esto sería determinado por la elegibilidad del usuario
            Map<String, Double> descuentosElegibles = new HashMap<>();
            descuentosElegibles.put("Estudiante (20%)", 0.2);
            descuentosElegibles.put("Promoción primavera (10%)", 0.1);
            dialog.setAvailableDiscounts(descuentosElegibles);
            
            dialog.setVisible(true);
        });
    }
}