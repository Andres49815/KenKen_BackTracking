package Controller;

import Model.KenKen_Board;
import Model.Solver;
import View.MainMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Andres Obando Alfaro
 */
public class C_MainMenu implements ActionListener {
    private MainMenu view;
    private KenKen_Board model;
    
    public C_MainMenu(MainMenu v) {
        view = v;
        // For MVC
        view.button_Generate.addActionListener(this);
        view.button_Powers.addActionListener(this);
        view.table_Game.setVisible(false);
        
        // Display view
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    @Override public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Generar":
                generate();
                break;
            case "Resolver Potencias":
                Powers();
                break;
            case "Resolver Modulos":
                Moduls();
                break;
        }
    }
    private void generate() {
        int size;
        
        if (view.isComplete()) {
            //view.table_Game.setVisible(true);
            size = (int)view.spinner_Size.getValue();
            model = new KenKen_Board(size);
            //model.print();
            //view.gameTable.setTable(view.table_Game, model);
        }
    }
    private void Powers() {
        Solver.Solve();
        view.gameTable.setTable(view.table_Game, model);
        System.out.println("Matriz ----------------------------------------- ");
        KenKen_Board.print();
    }

    private void Moduls() {
        
    }
    
}
