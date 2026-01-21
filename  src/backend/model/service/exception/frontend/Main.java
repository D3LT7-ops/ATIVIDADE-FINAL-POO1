package frontend;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Classe principal para iniciar a aplicação
 * 
 * @author  [Helberth Renan Gomes de sousa]
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new TelaInicial();
        });
    }
}