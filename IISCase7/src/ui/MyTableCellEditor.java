package ui;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

public class MyTableCellEditor extends AbstractCellEditor implements TableCellEditor {
            // This is the component that will handle the editing of the cell value
            JComponent component = new JTextField();
        
            // This method is called when a cell value is edited by the user.
            public Component getTableCellEditorComponent(JTable table, Object value,
                    boolean isSelected, int rowIndex, int vColIndex) {
                // 'value' is value contained in the cell located at (rowIndex, vColIndex)
        
                if (isSelected) {
                    // cell (and perhaps other cells) are selected
                }
        
                // Configure the component with the specified value
                ((JTextField)component).setText((String)value);
                ((JTextField)component).setEditable(false);
                component.setBackground(table.getSelectionBackground());
                component.setForeground(table.getSelectionForeground());
                component.setFont(table.getFont());
                component.setBorder(table.getBorder());
                // Return the configured component
                return component;
            }
        
            // This method is called when editing is completed.
            // It must return the new value to be stored in the cell.
            public Object getCellEditorValue() {
                return ((JTextField)component).getText();
            }
        }
