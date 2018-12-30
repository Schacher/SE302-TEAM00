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

import javax.swing.JButton;

public  class DeleteFactory  extends ButtonFactory{



    @Override
    JButton CreateButton(String Type) {
        if(Type.equals("Collection"))
            return new DeleteCollection();
        
        if(Type.equals("Item"))
            return new AddItem();
        return null;
    }
    
    
}
