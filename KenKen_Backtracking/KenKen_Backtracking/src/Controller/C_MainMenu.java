package Controller;

import Model.KenKen_Board;
import Model.Solver;
import static Model.Solver.DoPossibilities;
import static Model.Solver.DoPossibilitiesQueue;
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
    private XML xmlAntes;

    public C_MainMenu(MainMenu v) {
        view = v;
        // For MVC
        view.button_Generate.addActionListener(this);
        view.button_Powers.addActionListener(this);
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
        xmlAntes = new XML(KenKen_Board.board, KenKen_Board.transverseBoard,
                KenKen_Board.group, KenKen_Board.cages, KenKen_Board.map, KenKen_Board.resultsMap,
                KenKen_Board.size, KenKen_Board.threadCount, KenKen_Board.groupsArray, KenKen_Board.Powers, KenKen_Board.Modules,
                KenKen_Board.solutionFound, KenKen_Board.time);
    }

    private void doPossibilities() {
        KenKen_Board.InitializeBoards();
        view.gameTable.actualizar(view.table_Game);
        Solver.SolvePowers();
        DoPossibilities();
//        DoPossibilitiesQueue();
//        System.out.println("Cant"+KenKen_Board.groupsArray.size());
//        for(int x = 0; x<4; x++){
//            if(x==0)
//            {
//                KenKen_Board.threadCount = x+1;
//                long startTime = System.currentTimeMillis();
//                Solver.DoPossibilitiesQueue();
//                long finishTime = System.currentTimeMillis();
//                System.out.println("Hilo: "+(x+1)+" Tiempo: " + (finishTime-startTime));            
//            }
//            else if (x==1)
//            {
//                KenKen_Board.threadCount = (KenKen_Board.groupsArray.size()/3)*1; ;
//                long startTime = System.currentTimeMillis();
//                Solver.DoPossibilitiesQueue();
//                long finishTime = System.currentTimeMillis();
//                System.out.println("Hilo: "+(33)+" Tiempo: " + (finishTime-startTime));            
//            }
//            else if (x==2)
//            {
//                KenKen_Board.threadCount = (KenKen_Board.groupsArray.size()/3)*2; ;
//                long startTime = System.currentTimeMillis();
//                Solver.DoPossibilitiesQueue();
//                long finishTime = System.currentTimeMillis();
//                System.out.println("Hilo: "+(66)+" Tiempo: " + (finishTime-startTime));            
//            }
//            else
//            {
//                KenKen_Board.threadCount = KenKen_Board.groupsArray.size();
//                long startTime = System.currentTimeMillis();
//                Solver.DoPossibilitiesQueue();
//                long finishTime = System.currentTimeMillis();
//                System.out.println("Hilo: "+(100)+" Tiempo: " + (finishTime-startTime));            
//            }
//    }
    }

    private void Solve() {

        long time = Solver.Solve();

        view.gameTable.actualizar(view.table_Game);

        JOptionPane.showMessageDialog(view, "El KenKen tardó resolviendose: " + time / 1000 + " segundos con " + Solver.cantRecursion + " recursiones y " + Solver.cantPossibilities + " posibilidades\n Se hicieron " + (Solver.cantPossibilities - Solver.cantRecursion) + " cortes");

    }

    private void Save() throws FileNotFoundException {
        try {
            reader = new FileReader("test//" + KenKen_Board.size + ".xml");
            XML xml = (XML) (xstream.fromXML(reader));
            if (xml.time > KenKen_Board.time) {
                JOptionPane.showMessageDialog(view,"Nuevo Record: "+ " Antes: "+ xml.time + " Nuevo: "+ KenKen_Board.time);
                try (PrintWriter outA = new PrintWriter("test//" + KenKen_Board.size + ".xml")) {
                    xmlAntes.time = KenKen_Board.time;
                    String archivo = xstream.toXML(xmlAntes);
                    outA.println(archivo);
                }
            }
        } catch (FileNotFoundException ex) {
            try (PrintWriter outA = new PrintWriter("test//" + KenKen_Board.size + ".xml")) {
                xmlAntes.time = KenKen_Board.time;
                String archivo = xstream.toXML(xmlAntes);
                outA.println(archivo);
            }
        }

    }

    private void Open() throws FileNotFoundException {
        view.table_Game.setVisible(true);

        JFileChooser fc = new JFileChooser("test//");
        String path = "";
        if (fc.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            path = fc.getSelectedFile().getAbsolutePath();
        }
        reader = new FileReader(path);

        XML xml = (XML) (xstream.fromXML(reader));

        model = new KenKen_Board(xml.board, xml.transverseBoard,
                xml.group, xml.cages, xml.map, xml.resultsMap,
                xml.size, xml.threadCount, xml.groupsArray, xml.Powers, xml.Modules,
                xml.solutionFound, xml.time);
        
        xmlAntes = new XML(KenKen_Board.board, KenKen_Board.transverseBoard,
                KenKen_Board.group, KenKen_Board.cages, KenKen_Board.map, KenKen_Board.resultsMap,
                KenKen_Board.size, KenKen_Board.threadCount, KenKen_Board.groupsArray, KenKen_Board.Powers, KenKen_Board.Modules,
                KenKen_Board.solutionFound, KenKen_Board.time);

        view.gameTable.setTable(view.table_Game);
    }

}
