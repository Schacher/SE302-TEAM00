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

public class AddFactory extends ButtonFactory {

    @Override
    public JButton CreateButton(String Type) {

        if ("Collection".equals(Type)) {
            return new AddCollection();
        }
        if ("Item".equals(Type)) {
            return  new AddItem();
        }
        return null;
    }

}

