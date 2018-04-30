package Controller;

import Model.KenKen_Board;
import Model.Solver;
import static Model.Solver.DoPossibilities;
import static Model.Solver.DoPossibilitiesQueue;
import View.MainMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Andres Obando Alfaro
 */
public class C_MainMenu implements ActionListener {

    XStream xstream = new XStream(new DomDriver());
    FileReader reader = null;
    private MainMenu view;
    private KenKen_Board model;

    public C_MainMenu(MainMenu v) {
        view = v;
        // For MVC
        view.button_Generate.addActionListener(this);
        view.button_Powers.addActionListener(this);
        view.button_Save.addActionListener(this);
        view.button_Open.addActionListener(this);
        view.button_Clear.addActionListener(this);
        view.table_Game.setVisible(false);

        // Display view
        view.setLocationRelativeTo(null);
        view.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Generar":
                generate();
                break;
            case "Calcular Posibilidades":
                doPossibilities();
                break;
            case "Resolver":
                Solve();
                break;
            case "Guardar":
                try {
                    Save();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(C_MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "Cargar":
                try {
                    Open();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(C_MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

    private void generate() {
        int size;
        try {
            if (view.isComplete()) {
                view.table_Game.setVisible(true);
                size = (int) view.spinner_Size.getValue();
                KenKen_Board.threadCount = (int) view.spinner_Thread.getValue();
                model = new KenKen_Board(size);
                view.gameTable.setTable(view.table_Game);
            }
        } catch (Exception e) {
            generate();
        }
    }
    
     private void doPossibilities() {
        KenKen_Board.InitializeBoards();
        DoPossibilities();
        KenKen_Board.InitializeBoards();
        view.gameTable.actualizar(view.table_Game);
        
    }

    private void Solve() {

        long time = Solver.Solve();
        
        view.gameTable.actualizar(view.table_Game);
        
        JOptionPane.showMessageDialog(view, "El KenKen tardó resolviendose: "+time/1000 +" segundos con "+ Solver.cantRecursion + " recursiones y " +Solver.cantPossibilities+" posibilidades\n Se hicieron "+ (Solver.cantPossibilities-Solver.cantRecursion)+" cortes" );

    }

    private void Save() throws FileNotFoundException {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        String path = "";
        if (fc.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            path = fc.getSelectedFile().getAbsolutePath();
        }
        try (PrintWriter outA = new PrintWriter(path + ".xml")) {
            String xml = xstream.toXML(model);
            outA.println(xml);
        }
    }

    private void Open() throws FileNotFoundException {
        view.table_Game.setVisible(true);

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        String path = "";
        if (fc.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            path = fc.getSelectedFile().getAbsolutePath();
        }

        reader = new FileReader(path + ".xml");

        KenKen_Board xml = (KenKen_Board) (xstream.fromXML(reader));

        model = new KenKen_Board(xml.board, xml.transverseBoard,
                xml.group, xml.cages, xml.map, xml.resultsMap,
                xml.size, xml.threadCount, xml.groupsArray, xml.Powers, xml.Modules,
                xml.solutionFound);

        view.gameTable.setTable(view.table_Game);
    }

   
}
