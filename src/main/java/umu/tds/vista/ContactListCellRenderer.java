package umu.tds.vista;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import umu.tds.model.ContactoIndividual;

public class ContactListCellRenderer extends JLabel implements ListCellRenderer<ContactoIndividual> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static ImageIcon longIcon = new ImageIcon("long.gif");
    final static ImageIcon shortIcon = new ImageIcon("short.gif");

    // This is the only method defined by ListCellRenderer.
    // We just reconfigure the JLabel each time we're called.
    @Override
    public Component getListCellRendererComponent(
      JList<? extends ContactoIndividual> list,           // the list
      ContactoIndividual ci,            // value to display
      int index,               // cell index
      boolean isSelected,      // is the cell selected
      boolean cellHasFocus)    // does the cell have focus
    {
        String s = ci.toString();
        setText(s);
        setIcon((s.length() > 10) ? longIcon : shortIcon);
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        return this;
    }
}
