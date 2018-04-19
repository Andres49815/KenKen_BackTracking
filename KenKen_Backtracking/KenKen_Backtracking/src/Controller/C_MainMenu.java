package Controller;

import Model.KenKen_Board;
import Model.Solver;
import Model.XML;
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
        
        if (view.isComplete()) {
            view.table_Game.setVisible(true);
            size = (int)view.spinner_Size.getValue();
            
            model = new KenKen_Board(size);
            view.model = model;
            model.print();
            view.gameTable.setTable(view.table_Game, model);
            System.out.println("Grupo");
            for (int i = 0; i < model.group.length; i++) {
                for (int j = 0; j < model.group.length; j++)
                    System.out.print(model.group[i][j] + "\t");
                System.out.println();
            }
        }
    }
    private void Solve() {
        Solver.Solve();
        //ThreadInterface();
        System.out.println("Matriz ----------------------------------------- ");
        KenKen_Board.print();
    }

    

    private void ThreadInterface() {
        Thread thread = new Thread(view);
        thread.start();
    }

    private void Save() throws FileNotFoundException {
        XML saved = new XML(KenKen_Board.board,KenKen_Board.transverseBoard,
                KenKen_Board.group,KenKen_Board.results,KenKen_Board.map,
                KenKen_Board.resultsMap,KenKen_Board.operations,KenKen_Board.actual,
                KenKen_Board.size,KenKen_Board.Powers,KenKen_Board.Modules,
                KenKen_Board.solutionFound,KenKen_Board.range);
           
        try (PrintWriter outA = new PrintWriter("kenken.xml")) {
            String xml = xstream.toXML(saved);
            outA.println(xml);
        }
    }
    private void Open() throws FileNotFoundException {
        reader = new FileReader("kenken.xml");
        XML xml = (XML) (xstream.fromXML(reader));
        KenKen_Board.board = xml.board;
        KenKen_Board.transverseBoard = xml.transverseBoard;
        KenKen_Board.group = xml.group;
        KenKen_Board.results = xml.results;
        KenKen_Board.map = xml.map;
        KenKen_Board.resultsMap = xml.resultsMap;
        KenKen_Board.operations = xml.operations;
        KenKen_Board.actual = xml.actual;
        KenKen_Board.size = xml.size;
        KenKen_Board.Powers = xml.Powers;
        KenKen_Board.Modules = xml.Modules;
        KenKen_Board.solutionFound = xml.solutionFound;
        KenKen_Board.range = xml.range;
    }
           
        
       
            
    
    
}
