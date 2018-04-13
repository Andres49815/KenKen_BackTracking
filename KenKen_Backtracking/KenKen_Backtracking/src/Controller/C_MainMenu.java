package Controller;

import View.MainMenu;

/**
 *
 * @author Andres Obando Alfaro
 */
public class C_MainMenu {
    private MainMenu view;
    
    public C_MainMenu(MainMenu v) {
        view = v;
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
}
