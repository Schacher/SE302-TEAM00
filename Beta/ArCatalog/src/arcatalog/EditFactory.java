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


public class EditFactory extends ButtonFactory {

    @Override
    public JButton CreateButton(String Type) {
        if (Type == "Collection") {
            return new EditCollection();
        }
        return null;
    }

}
