/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.JTable;

/**
 *
 * @author Andres Obando Alfaro
 */
public class GameTable {
    public void setTable(JTable t, Model.KenKen_Board KKB) {
        t.setDefaultRenderer(Object.class, new Render);
    }
}
