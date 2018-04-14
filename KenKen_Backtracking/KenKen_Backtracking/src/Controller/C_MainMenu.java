package Controller;

import View.MainMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Andres Obando Alfaro
 */
public class C_MainMenu implements ActionListener {
    private MainMenu view;
    
    public C_MainMenu(MainMenu v) {
        view = v;
        // For MVC
        view.button_Generate.addActionListener(this);
        view.table_Game.setVisible(false);
        // Display view
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    @Override public void actionPerformed(ActionEvent ae) {
    }
}
