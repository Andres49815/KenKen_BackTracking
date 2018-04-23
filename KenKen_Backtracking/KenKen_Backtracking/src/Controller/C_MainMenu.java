package Controller;

import Model.KenKen_Board;
import Model.Solver;
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

import Model.XML;

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

    @Override public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Generar":
                generate();
                break;
            case "Borrar":
                KenKen_Board.CleanBoards();
                view.gameTable.actualizar(view.table_Game, model);
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
                size = (int)view.spinner_Size.getValue();

                model = new KenKen_Board(size);
                view.model = model;
                model.print();
                //view.gameTable.setTable(view.table_Game, model);
            }
        }
        catch (Exception e) {
            generate();
        }
    }
    private void Solve() {
        Solver.Solve();
        //ThreadInterface();
        view.gameTable.actualizar(view.table_Game, model);
        System.out.println("Matriz ----------------------------------------- ");
        KenKen_Board.print();
    }

    

    private void ThreadInterface() {
        Thread thread = new Thread(view);
        thread.start();
    }

    private void Save() throws FileNotFoundException {
        XML saved = new XML(KenKen_Board.board,KenKen_Board.transverseBoard,
                KenKen_Board.group,KenKen_Board.cages,KenKen_Board.results,KenKen_Board.map,
                KenKen_Board.resultsMap,KenKen_Board.operations,
                KenKen_Board.size,KenKen_Board.groupsArray,KenKen_Board.Powers,
                KenKen_Board.Modules, KenKen_Board.solutionFound);
           
        try (PrintWriter outA = new PrintWriter("kenken.xml")) {
            String xml = xstream.toXML(saved);
            outA.println(xml);
        }
    }
    private void Open() throws FileNotFoundException {
        view.table_Game.setVisible(true);
        reader = new FileReader("kenken.xml");
        
        XML xml = (XML) (xstream.fromXML(reader));
        KenKen_Board.board = xml.board;
        KenKen_Board.transverseBoard = xml.transverseBoard;
        KenKen_Board.group = xml.group;
        KenKen_Board.cages = xml.cages;
        KenKen_Board.results = xml.results;
        KenKen_Board.map = xml.map;
        KenKen_Board.resultsMap = xml.resultsMap;
        KenKen_Board.operations = xml.operations;
        KenKen_Board.size = xml.size;
        KenKen_Board.groupsArray = xml.groupsArray;
        KenKen_Board.Powers = xml.Powers;
        KenKen_Board.Modules = xml.Modules;
        KenKen_Board.solutionFound = xml.solutionFound;
        
        
        model =  new KenKen_Board(xml.board,xml.transverseBoard,
                xml.group,xml.cages,xml.results,xml.map,
                xml.resultsMap,xml.operations,
                xml.size,xml.groupsArray,xml.Powers,xml.Modules,
                xml.solutionFound);
        
        view.model = model;
        view.gameTable.setTable(view.table_Game, model);
    }
}
