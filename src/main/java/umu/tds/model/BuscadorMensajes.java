package umu.tds.model;

import java.util.List;
import java.util.stream.Collectors;

public class BuscadorMensajes {
    private Usuario usuario;
    
    public BuscadorMensajes(Usuario usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Busca mensajes según criterios específicos.
     */
    public List<Mensaje> buscar(CriteriosBusqueda criterios) {
        return obtenerTodosMensajes().stream()
            .filter(criterios.toPredicate())
            .collect(Collectors.toList());
    }
    
    private List<Mensaje> obtenerTodosMensajes() {
        List<Mensaje> mensajes = new java.util.ArrayList<>();
        
        if (usuario == null) return mensajes;
        
        for (Contacto contacto : usuario.getContactos()) {
            mensajes.addAll(contacto.getMensajes());
        }
        
        return mensajes;
    }
}