/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcatalog;

/**
 *
 * @author can
 */


public class Refresh extends Button{
    
    public Refresh(){
        setBounds(675,200,150,30);
        setText("Refresh");
    }

    
    @Override
    public void Click() {
        DatabaseConnection db = new DatabaseConnection();
        
    }
    
}
